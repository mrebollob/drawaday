package com.mrebollob.drawaday.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DrawImageApiModel(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("drawing")
    val drawing: String,
    @SerialName("source")
    val source: String,
    @SerialName("publish_date")
    val publishDate: String
)
