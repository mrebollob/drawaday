package com.mrebollob.drawaday.utils

import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.model.User

object TestDataUtils {

    fun getTestDrawImages(count: Int): List<DrawImage> = (0..count).map {
        getTestDrawImage("#$it")
    }

    fun getTestDrawImage(seed: String): DrawImage = DrawImage(
        id = "id_$seed",
        title = "Item $seed",
        drawing = "https://images.unsplash.com/photo-1605666807892-8c11d020bede",
        source = "Source: $seed",
        publishDate = "2021-5=12"
    )

    fun getTestUser(seed: String) = User("$seed user")
}
