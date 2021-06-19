package com.mrebollob.drawaday.analytics

enum class AnalyticsEvent(val key: String) {

    // OnBoarding
    ONBOARDING_SKIP("onboarding_skip"),
    ONBOARDING_DONE("onboarding_done"),

    // Feed
    NAVIGATE_TO_IMAGE("navigate_to_image"),
}