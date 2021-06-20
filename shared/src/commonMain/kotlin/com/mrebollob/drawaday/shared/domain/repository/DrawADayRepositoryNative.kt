package com.mrebollob.drawaday.shared.domain.repository

import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result
import kotlinx.coroutines.CoroutineScope

interface DrawADayRepositoryNative {

    fun getNativeScope(): CoroutineScope

    fun startObservingDrawImagesUpdates(
        index: Int,
        refresh: Boolean,
        success: (Result<List<DrawImage>>) -> Unit
    )

    fun stopObservingDrawImagesUpdates()
}
