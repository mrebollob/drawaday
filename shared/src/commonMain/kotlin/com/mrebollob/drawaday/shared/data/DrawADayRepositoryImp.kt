package com.mrebollob.drawaday.shared.data

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.shared.data.local.DrawADayDatabaseWrapper
import com.mrebollob.drawaday.shared.data.network.DrawADayApi
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DrawADayRepositoryImp : DrawADayRepository, KoinComponent {
    private val drawApi: DrawADayApi by inject()
    private val logger: Kermit by inject()

    private val coroutineScope: CoroutineScope = MainScope()
    private val drawDatabase: DrawADayDatabaseWrapper by inject()
    private val drawImageQueries = drawDatabase.instance?.drawADayQueries

    init {
        coroutineScope.launch {
            fetchAndStoreDrawImages()
        }
    }

    override fun fetchDrawImages(index: Int): Flow<List<DrawImage>> {
        return drawImageQueries?.selectAll(mapper = { id, title, drawing, source, publishDate ->
            DrawImage(
                id = id,
                title = title,
                drawing = drawing,
                source = source,
                publishDate = publishDate
            )
        })?.asFlow()?.mapToList() ?: flowOf(emptyList())
    }

    override fun fetchDrawImageByDate(date: String): Flow<DrawImage> {
        TODO("Not yet implemented")
    }

    private suspend fun fetchAndStoreDrawImages() {
        logger.d { "fetchAndStoreDrawImages" }
        val result = drawApi.fetchDrawImages()
        drawImageQueries?.deleteAll()
        result.values.forEach {
            drawImageQueries?.insertItem(
                id = it.id,
                title = it.title,
                drawing = it.drawing,
                source = it.source,
                publish_date = it.publishDate
            )
        }
    }
}
