package com.mrebollob.drawaday.repository

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.di.DrawADayDatabaseWrapper
import com.mrebollob.drawaday.model.personBios
import com.mrebollob.drawaday.model.personImages
import com.mrebollob.drawaday.remote.Assignment
import com.mrebollob.drawaday.remote.DrawADayApi
import com.mrebollob.drawaday.remote.IssPosition
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class DrawADayRepository : KoinComponent {
    private val drawApi: DrawADayApi by inject()
    private val logger: Kermit by inject()

    private val coroutineScope: CoroutineScope = MainScope()
    private val drawDatabase: DrawADayDatabaseWrapper by inject()
    private val peopleInSpaceQueries = drawDatabase.instance?.drawADayQueries

    var peopleJob: Job? = null

    init {
        coroutineScope.launch {
            fetchAndStorePeople()
        }
    }

    fun fetchPeopleAsFlow(): Flow<List<Assignment>> {
        // the main reason we need to do this check is that sqldelight isn't currently
        // setup for javascript client
        return peopleInSpaceQueries?.selectAll(mapper = { name, craft ->
            Assignment(name = name, craft = craft)
        })?.asFlow()?.mapToList() ?: flowOf(emptyList<Assignment>())
    }

    private suspend fun fetchAndStorePeople() {
        logger.d { "fetchAndStorePeople" }
        val result = drawApi.fetchPeople()

        // this is very basic implementation for now that removes all existing rows
        // in db and then inserts results from api request
        peopleInSpaceQueries?.deleteAll()
        result.people.forEach {
            peopleInSpaceQueries?.insertItem(it.name, it.craft)
        }
    }

    // Used by web client atm
    suspend fun fetchPeople() = drawApi.fetchPeople().people

    fun getPersonBio(personName: String) = personBios[personName] ?: ""
    fun getPersonImage(personName: String) = personImages[personName] ?: ""


    // called from Kotlin/Native clients
    fun startObservingPeopleUpdates(success: (List<Assignment>) -> Unit) {
        logger.d { "startObservingPeopleUpdates" }
        peopleJob = coroutineScope.launch {
            fetchPeopleAsFlow().collect {
                success(it)
            }
        }
    }

    fun stopObservingPeopleUpdates() {
        logger.d { "stopObservingPeopleUpdates, peopleJob = $peopleJob" }
        peopleJob?.cancel()
    }


    fun pollISSPosition(): Flow<IssPosition> = flow {
        while (true) {
            val position = drawApi.fetchISSPosition().iss_position
            emit(position)
            logger.d("DrawADayRepository") { position.toString() }
            delay(POLL_INTERVAL)
        }
    }


    val iosScope: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = SupervisorJob() + Dispatchers.Main
    }

    fun iosPollISSPosition() = KotlinNativeFlowWrapper<IssPosition>(pollISSPosition())


    companion object {
        private const val POLL_INTERVAL = 10000L
    }
}


class KotlinNativeFlowWrapper<T>(private val flow: Flow<T>) {
    fun subscribe(
        scope: CoroutineScope,
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit
    ) = flow
        .onEach { onEach(it) }
        .catch { onThrow(it) }
        .onCompletion { onComplete() }
        .launchIn(scope)
}
