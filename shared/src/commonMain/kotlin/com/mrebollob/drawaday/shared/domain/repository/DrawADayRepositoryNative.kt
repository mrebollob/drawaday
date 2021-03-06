package com.mrebollob.drawaday.shared.domain.repository

import com.mrebollob.drawaday.shared.data.DrawADayRepositoryNativeImp
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result

interface DrawADayRepositoryNative {

    fun startObservingDrawImagesUpdates(
        index: Int,
        refresh: Boolean,
        success: (List<DrawImage>) -> Unit,
        loading: (List<DrawImage>) -> Unit,
        error: () -> Unit
    )

    fun stopObservingDrawImagesUpdates()

    companion object {
        fun getInstance(): DrawADayRepositoryNative {
            return DrawADayRepositoryNativeImp()
        }
    }
}
