package com.mrebollob.drawaday.shared.domain.repository

import com.mrebollob.drawaday.shared.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(): Flow<User?>

    suspend fun saveUser(user: User)

    fun getIsNewUser(): Flow<Boolean>

    suspend fun setIsNewUser(isNewUser: Boolean)
}
