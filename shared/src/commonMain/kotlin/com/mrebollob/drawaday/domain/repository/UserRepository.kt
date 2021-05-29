package com.mrebollob.drawaday.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getIsNewUser(): Flow<Boolean>
}
