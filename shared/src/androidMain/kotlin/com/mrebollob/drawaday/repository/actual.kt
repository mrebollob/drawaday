package com.mrebollob.drawaday.repository

import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger
import com.mrebollob.drawaday.db.DrawADayDatabase
import com.mrebollob.drawaday.di.DrawADayDatabaseWrapper
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
