package com.mrebollob.drawaday.shared.domain.repository

import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface DrawADayRepository {

    suspend fun fetchDrawImages(index: Int): Flow<Result<List<DrawImage>>>
}
