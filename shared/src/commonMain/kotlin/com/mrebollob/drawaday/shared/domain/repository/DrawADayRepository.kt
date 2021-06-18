package com.mrebollob.drawaday.shared.domain.repository

import com.mrebollob.drawaday.shared.domain.model.DrawImage
import kotlinx.coroutines.flow.Flow

interface DrawADayRepository {

    fun fetchDrawImages(index: Int): Flow<List<DrawImage>>

    fun fetchDrawImageByDate(date: String): Flow<DrawImage>
}
