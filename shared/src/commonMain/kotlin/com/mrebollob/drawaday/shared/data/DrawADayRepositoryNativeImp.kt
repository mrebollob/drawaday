package com.mrebollob.drawaday.shared.data

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepositoryNative
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DrawADayRepositoryNativeImp(
    private val logger: Kermit,
    private val repository: DrawADayRepository
) : DrawADayRepositoryNative {

    private val coroutineScope: CoroutineScope = MainScope()
    private var imagesJob: Job? = null

    override fun startObservingDrawImagesUpdates(
        index: Int,
        refresh: Boolean,
        success: (Result<List<DrawImage>>) -> Unit
    ) {
        logger.d { "startObservingDrawImagesUpdates" }
        imagesJob = coroutineScope.launch {
            repository.fetchDrawImages(index, refresh).collect {
                success(it)
            }
        }
    }

    override fun stopObservingDrawImagesUpdates() {
        logger.d { "stopObservingDrawImagesUpdates, imagesJob = $imagesJob" }
        imagesJob?.cancel()
    }
}
