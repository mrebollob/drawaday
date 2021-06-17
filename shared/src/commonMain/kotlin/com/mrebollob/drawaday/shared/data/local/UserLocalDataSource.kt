package com.mrebollob.drawaday.shared.data.local

import com.soywiz.klock.DateTime

interface UserLocalDataSource {

    fun setStartDate(date: DateTime)

    fun getStartDate(): DateTime?
}