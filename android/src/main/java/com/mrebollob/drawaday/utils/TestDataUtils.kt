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
        drawing = "https://firebasestorage.googleapis.com/v0/b/drawaday-6b6a5.appspot.com/o/images%2Fmockup-graphics-Kl3467edwsE-unsplash.jpg?alt=media&token=cc895837-bcde-4352-a308-07020e4fbf02",
        source = "Source: $seed",
        publishDate = LocalDate.of(2021, 5, 12),
    )
}
