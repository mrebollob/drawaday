package com.mrebollob.drawaday.shared.data

import com.mrebollob.drawaday.shared.data.local.UserLocalDataSource
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImp(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun getIsNewUser(): Flow<Boolean> {
        return flow {
            val startDate = userLocalDataSource.getStartDate()
            emit(startDate == null)
        }
    }

    override suspend fun setStartDate(date: DateTime) {
        userLocalDataSource.setStartDate(date)
    }

    override fun getDaysInTheApp(): Flow<Int> {
        return flow {
            val now = DateTime.now()
            val startDate = userLocalDataSource.getStartDate() ?: now
            emit((startDate - now).days.toInt())
        }
    }
}
