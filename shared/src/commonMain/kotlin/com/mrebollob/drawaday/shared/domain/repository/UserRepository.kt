package com.mrebollob.drawaday.shared.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getIsNewUser(): Flow<Boolean>
}
