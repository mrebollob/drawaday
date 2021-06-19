package com.mrebollob.drawaday

import android.app.Application
import android.os.StrictMode
import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.analytics.AnalyticsManager
import com.mrebollob.drawaday.shared.di.appModule
import com.mrebollob.drawaday.shared.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DrawADayApplication : Application(), KoinComponent {
    private val logger: Kermit by inject()
    private val analyticsManager: AnalyticsManager by inject()

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            enableStrictMode()
        }
        super.onCreate()

        initKoin(enableNetworkLogs = true) {
            androidLogger()
            androidContext(this@DrawADayApplication)
            modules(appModule)
        }

        initAnalytics()

        logger.d { "DrawADayApplication" }
    }

    private fun initAnalytics() {
        analyticsManager.setUserData()
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }
}
