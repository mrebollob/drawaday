package com.mrebollob.drawaday.shared.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NSLogLogger
import com.mrebollob.drawaday.db.DrawADayDatabase
import com.mrebollob.drawaday.shared.data.UserLocalDataSourceImp
import com.mrebollob.drawaday.shared.data.local.DrawADayDatabaseWrapper
import com.mrebollob.drawaday.shared.data.local.UserLocalDataSource
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(DrawADayDatabase.Schema, "drawaday.db")
        DrawADayDatabaseWrapper(DrawADayDatabase(driver))
    }
    single<UserLocalDataSource> { UserLocalDataSourceImp() }
    single<Logger> { NSLogLogger() }
}
