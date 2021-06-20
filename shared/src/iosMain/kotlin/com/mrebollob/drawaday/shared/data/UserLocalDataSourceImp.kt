package com.mrebollob.drawaday.shared.data

import com.mrebollob.drawaday.shared.data.local.UserLocalDataSource
import com.soywiz.klock.DateTime

class UserLocalDataSourceImp : UserLocalDataSource {

    override fun setStartDate(date: DateTime) {

    }

    override fun getStartDate(): DateTime? {
        return DateTime.now()
    }
}
