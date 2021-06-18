package com.mrebollob.drawaday.shared.data

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.shared.data.local.DrawADayDatabaseWrapper
import com.mrebollob.drawaday.shared.data.network.DrawADayApi
import com.mrebollob.drawaday.shared.data.network.model.toDomain
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DrawADayRepositoryImp : DrawADayRepository, KoinComponent {

    private val drawApi: DrawADayApi by inject()
    private val logger: Kermit by inject()
    private val drawDatabase: DrawADayDatabaseWrapper by inject()

    private val drawImageQueries = drawDatabase.instance?.drawADayQueries

    override suspend fun fetchDrawImages(index: Int): Flow<Result<List<DrawImage>>> = flow {
        emit(Result.Loading())
        val cachedImages = getCachedImages()
        if (cachedImages != null) {
            logger.d { "Emitting images from cache. count: ${cachedImages.size}" }
            emit(Result.Loading(cachedImages))
        }

        val freshImages = getFreshImages(index)
        emit(Result.Success(freshImages))
        saveImages(freshImages)
    }

    private suspend fun getCachedImages(): List<DrawImage>? =
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            drawImageQueries?.selectAll(mapper = { id, title, drawing, source, publishDate ->
                DrawImage(
                    id = id,
                    title = title,
                    drawing = drawing,
                    source = source,
                    publishDate = publishDate
                )
            })?.executeAsList()
        }

    private suspend fun getFreshImages(index: Int): List<DrawImage> {
        val result = drawApi.fetchDrawImages()
        return result.values.map { it.toDomain() }
    }

    private suspend fun saveImages(images: List<DrawImage>) =
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            drawImageQueries?.deleteAll()
            images.forEach {
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
