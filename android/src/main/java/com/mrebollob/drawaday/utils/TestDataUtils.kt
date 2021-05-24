package com.mrebollob.drawaday.utils

import com.mrebollob.drawaday.domain.model.DrawImage
import java.time.LocalDate

object TestDataUtils {

    fun getTestDrawImages(count: Int): List<DrawImage> = (0..count).map {
        getTestDrawImage("#$it")
    }

    fun getTestDrawImage(seed: String): DrawImage = DrawImage(
        id = "id_$seed",
        title = "Item $seed",
        drawing = "https://images.unsplash.com/photo-1605666807892-8c11d020bede",
        source = "Source: $seed",
        publishDate = LocalDate.of(2021, 5, 12),
    )
}
