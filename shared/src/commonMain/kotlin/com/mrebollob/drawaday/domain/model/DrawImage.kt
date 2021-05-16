package com.mrebollob.drawaday.domain.model

import java.time.LocalDate

data class DrawImage(
    val image: String,
    val source: String,
    val publishDate: LocalDate
)
