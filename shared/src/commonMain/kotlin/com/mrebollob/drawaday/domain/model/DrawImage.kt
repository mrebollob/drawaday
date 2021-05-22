package com.mrebollob.drawaday.domain.model

import java.time.LocalDate

data class DrawImage(
    val id: String,
    val title: String,
    val drawing: String,
    val source: String,
    val publishDate: LocalDate
)