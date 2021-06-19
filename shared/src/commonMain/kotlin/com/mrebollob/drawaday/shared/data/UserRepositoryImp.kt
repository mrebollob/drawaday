package com.mrebollob.drawaday.shared.data

import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.shared.data.local.UserLocalDataSource
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.max

class UserRepositoryImp(
    private val userLocalDataSource: UserLocalDataSource,
    private val logger: Kermit
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
            val days = (now - startDate).days.toInt()

            logger.d { "startDate: ${startDate.format("dd-MM-yyyy")}" }
            logger.d { "Days in the app: $days" }

            emit(max(days, 0))
        }
    }
}
