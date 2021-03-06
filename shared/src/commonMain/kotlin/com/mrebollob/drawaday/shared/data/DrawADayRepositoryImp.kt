package com.mrebollob.drawaday.shared.data

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.shared.data.local.DrawADayDatabaseWrapper
import com.mrebollob.drawaday.shared.data.network.DrawADayApi
import com.mrebollob.drawaday.shared.data.network.model.DrawImageApiModel
import com.mrebollob.drawaday.shared.data.network.model.toDomain
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.max

class DrawADayRepositoryImp : DrawADayRepository, KoinComponent {

    private val drawApi: DrawADayApi by inject()
    private val logger: Kermit by inject()
    private val drawDatabase: DrawADayDatabaseWrapper by inject()

    private val drawImageQueries = drawDatabase.instance?.drawADayQueries

    override fun fetchDrawImages(
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
    }.flowOn(Dispatchers.Default)

    override fun fetchDrawImage(id: String): Flow<Result<DrawImage>> = flow {
        emit(Result.Loading())

        val image = getCachedImage(id)
        if (image != null) {
            emit(Result.Success(image))
        } else {
            emit(Result.Error(Exception("Image not found")))
        }
    }.flowOn(Dispatchers.Default)

    private fun getCachedImages(): List<DrawImage>? =
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

    private fun getCachedImage(id: String): DrawImage? = drawImageQueries?.selectById(
        id = id,
        mapper = { imageId, image, source, author, description, index ->
            DrawImage(
                id = imageId,
                image = image,
                source = source,
                author = author,
                description = description,
                index = index.toInt()
            )
        })?.executeAsList()?.firstOrNull()


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

    private fun saveImages(images: List<DrawImage>) {
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
