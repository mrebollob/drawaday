package com.mrebollob.drawaday.shared.domain.repository

import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface DrawADayRepository {

    suspend fun fetchDrawImages(index: Int, refresh: Boolean): Flow<Result<List<DrawImage>>>

    suspend fun fetchDrawImage(id: String): Flow<Result<DrawImage>>
}
