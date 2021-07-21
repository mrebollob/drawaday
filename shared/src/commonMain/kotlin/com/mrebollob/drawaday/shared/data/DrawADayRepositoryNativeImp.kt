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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DrawADayRepositoryNativeImp : DrawADayRepositoryNative, KoinComponent {

    private val logger: Kermit by inject()
    private val repository: DrawADayRepository by inject()
    private val coroutineScope: CoroutineScope = MainScope()
    private var imagesJob: Job? = null

    override fun startObservingDrawImagesUpdates(
        index: Int,
        refresh: Boolean,
        success: (List<DrawImage>) -> Unit,
        loading: (List<DrawImage>) -> Unit,
        error: () -> Unit
    ) {
        logger.d { "startObservingDrawImagesUpdates" }
        imagesJob = coroutineScope.launch {
            repository.fetchDrawImages(index, refresh).collect {
                when (it) {
                    is Result.Success -> success(it.data)
                    is Result.Error -> error()
                    is Result.Loading -> loading(it.data ?: emptyList())
                }
            }
        }
    }

    override fun stopObservingDrawImagesUpdates() {
        logger.d { "stopObservingDrawImagesUpdates, imagesJob = $imagesJob" }
        imagesJob?.cancel()
    }
}
