package com.mrebollob.drawaday.shared.data

import com.mrebollob.drawaday.shared.domain.repository.UserRepository
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