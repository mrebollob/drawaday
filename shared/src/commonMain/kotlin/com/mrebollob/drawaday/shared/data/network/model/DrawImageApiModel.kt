package com.mrebollob.drawaday.shared.data.network.model

import com.mrebollob.drawaday.shared.domain.model.DrawImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrawImageApiModel(
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: String,
    @SerialName("source")
    val source: String,
    @SerialName("author")
    val author: String,
    @SerialName("description")
    val description: String,
    @SerialName("index")
    val index: Int
)

fun DrawImageApiModel.toDomain() = with(this) {
    DrawImage(
        id = id,
        image = image,
        source = source,
        author = author,
        description = description,
        index = index
    )
}