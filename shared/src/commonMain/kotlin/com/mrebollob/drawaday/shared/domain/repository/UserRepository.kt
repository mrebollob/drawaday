package com.mrebollob.drawaday.shared.domain.repository

import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getIsNewUser(): Flow<Boolean>

    suspend fun setStartDate(date: DateTime)

    fun getDaysInTheApp(): Flow<Int>
}
