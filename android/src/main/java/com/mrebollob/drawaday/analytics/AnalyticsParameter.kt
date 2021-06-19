package com.mrebollob.drawaday.analytics

import android.os.Bundle
import com.mrebollob.drawaday.shared.domain.model.DrawImage

enum class AnalyticsParameter(val key: String) {

    // Onboarding
    ONBOARDING_PAGE_NUMBER("onboarding_page_number"),

    // Image
    IMAGE_URL("image_url"),
    IMAGE_AUTHOR("image_author"),
    IMAGE_SOURCE("image_source")
}

fun DrawImage.toBundle(): Bundle = with(this) {
    val bundle = Bundle()
    bundle.putString(AnalyticsParameter.IMAGE_URL.key, image)
    bundle.putString(AnalyticsParameter.IMAGE_AUTHOR.key, author)
    bundle.putString(AnalyticsParameter.IMAGE_SOURCE.key, source)
    return bundle
}
