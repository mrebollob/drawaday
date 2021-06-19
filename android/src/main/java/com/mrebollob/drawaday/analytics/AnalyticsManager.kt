package com.mrebollob.drawaday.analytics

import android.os.Bundle
import co.touchlab.kermit.Kermit
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsManager constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val logger: Kermit
) {

    fun trackEvent(event: AnalyticsEvent, bundle: Bundle? = null) {
        logger.d { "Track event: ${event.key}" }
        firebaseAnalytics.logEvent(event.key, bundle)
    }

    fun setUserData() {
        firebaseAnalytics.setUserProperty("device", android.os.Build.DEVICE)
        firebaseAnalytics.setUserProperty("manufacturer", android.os.Build.MANUFACTURER)
        firebaseAnalytics.setUserProperty("model", android.os.Build.MODEL)
        firebaseAnalytics.setUserProperty("product", android.os.Build.PRODUCT)
    }
}