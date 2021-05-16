package com.mrebollob.drawaday.di

import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger
import com.mrebollob.drawaday.db.DrawADayDatabase
import com.mrebollob.drawaday.data.local.DrawADayDatabaseWrapper
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver =
            AndroidSqliteDriver(DrawADayDatabase.Schema, get(), "drawaday.db")

        DrawADayDatabaseWrapper(DrawADayDatabase(driver))
    }
    single<Logger> { LogcatLogger() }
}
