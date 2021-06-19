package com.mrebollob.drawaday.shared.data

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.shared.data.local.DrawADayDatabaseWrapper
import com.mrebollob.drawaday.shared.data.network.DrawADayApi
import com.mrebollob.drawaday.shared.data.network.model.DrawImageApiModel
import com.mrebollob.drawaday.shared.data.network.model.toDomain
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.soywiz.klock.max
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.max

class DrawADayRepositoryImp : DrawADayRepository, KoinComponent {

    private val drawApi: DrawADayApi by inject()
    private val logger: Kermit by inject()
    private val drawDatabase: DrawADayDatabaseWrapper by inject()

    private val drawImageQueries = drawDatabase.instance?.drawADayQueries

    override suspend fun fetchDrawImages(
        index: Int,
        refresh: Boolean
    ): Flow<Result<List<DrawImage>>> = flow {
        emit(Result.Loading())

        if (refresh.not()) {
            val cachedImages = getCachedImages()?.sortedByDescending { it.index }
            if (cachedImages != null && cachedImages.isNotEmpty()) {
                logger.d { "Emitting images from cache. count: ${cachedImages.size}" }
                emit(Result.Loading(cachedImages))
            } else {
                logger.d { "Empty cache" }
            }
        }

        val freshImages = getFreshImages(index)?.sortedByDescending { it.index }
        if (freshImages != null) {
            emit(Result.Success(freshImages))
            saveImages(freshImages)
        } else {
            emit(Result.Error(Exception("Network error")))
        }
    }

    override suspend fun fetchDrawImage(id: String): Flow<Result<DrawImage>> = flow {
        emit(Result.Loading())

        val image = getCachedImage(id)
        if (image != null) {
            emit(Result.Success(image))
        } else {
            emit(Result.Error(Exception("Image not found")))
        }
    }

    private suspend fun getCachedImages(): List<DrawImage>? =
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            drawImageQueries?.selectAll(mapper = { id, image, source, author, description, index ->
                DrawImage(
                    id = id,
                    image = image,
                    source = source,
                    author = author,
                    description = description,
                    index = index.toInt()
                )
            })?.executeAsList()
        }

    private suspend fun getCachedImage(id: String): DrawImage? =
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            drawImageQueries?.selectById(
                id = id,
                mapper = { id, image, source, author, description, index ->
                    DrawImage(
                        id = id,
                        image = image,
                        source = source,
                        author = author,
                        description = description,
                        index = index.toInt()
                    )
                })?.executeAsList()?.firstOrNull()
        }

    private suspend fun getFreshImages(index: Int): List<DrawImage>? {

        val result: Map<String, DrawImageApiModel>? = try {
            drawApi.fetchDrawImages(
                startAt = max((index - 10), 0),
                endAt = index
            )
        } catch (e: IOException) {
            logger.e { "getFreshImages error" }
            null
        }

        return result?.values?.map { it.toDomain() }
    }

    private suspend fun saveImages(images: List<DrawImage>) =
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            logger.d { "${images.size} saved in cache" }
            drawImageQueries?.deleteAll()
            images.forEach {
                drawImageQueries?.insertItem(
                    id = it.id,
                    image = it.image,
                    source = it.source,
                    author = it.author,
                    description = it.description,
                    image_index = it.index.toLong()
                )
            }
        }
}
