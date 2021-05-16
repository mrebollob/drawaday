package com.mrebollob.drawaday.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NSLogLogger
import com.mrebollob.drawaday.db.DrawADayDatabase
import com.mrebollob.drawaday.data.local.DrawADayDatabaseWrapper
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(DrawADayDatabase.Schema, "drawaday.db")
        DrawADayDatabaseWrapper(DrawADayDatabase(driver))
    }
    single<Logger> { NSLogLogger() }
}
