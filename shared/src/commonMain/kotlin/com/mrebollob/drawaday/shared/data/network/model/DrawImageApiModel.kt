package com.mrebollob.drawaday.shared.data.network.model

import com.mrebollob.drawaday.shared.domain.model.DrawImage
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

fun DrawImageApiModel.toDomain() = with(this) {
    DrawImage(
        id = id,
        title = title,
        drawing = drawing,
        source = source,
        publishDate = publishDate
    )
}