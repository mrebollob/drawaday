package com.mrebollob.drawaday.shared.data

import com.mrebollob.drawaday.shared.data.local.UserLocalDataSource
import com.mrebollob.drawaday.shared.domain.model.User
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImp(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun getUser(): Flow<User> {
        return flow {
            emit(
                User(
                    name = userLocalDataSource.getUserName()
                )
            )
        }
    }

    override suspend fun saveUser(user: User) {
        userLocalDataSource.saveUserName(userName = user.name)
    }

    override fun getIsNewUser(): Flow<Boolean> {
        return flow {
            emit(userLocalDataSource.getIsNewUser())
        }
    }

    override suspend fun setIsNewUser(isNewUser: Boolean) {
        userLocalDataSource.setIsNewUser(isNewUser)
    }
}
