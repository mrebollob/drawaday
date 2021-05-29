package com.mrebollob.drawaday.data

import com.mrebollob.drawaday.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class UserRepositoryImp : UserRepository, KoinComponent {

    override fun getIsNewUser(): Flow<Boolean> {
        return flow {
            emit(true)
        }
    }
}