package com.mrebollob.drawaday.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DrawImageApiModel(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("image")
    val image: String,
    @SerialName("source")
    val source: String,
    @SerialName("publish_date")
    val publishDate: String
)
