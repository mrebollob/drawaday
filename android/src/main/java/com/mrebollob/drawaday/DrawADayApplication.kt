package com.mrebollob.drawaday

import android.app.Application
import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.di.appModule
import com.mrebollob.drawaday.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DrawADayApplication : Application(), KoinComponent {
    private val logger: Kermit by inject()

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@DrawADayApplication)
            modules(appModule)
        }

        logger.d { "DrawADayApplication" }
    }
}
