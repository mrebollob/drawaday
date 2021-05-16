package com.mrebollob.drawaday.data.network.model

import com.mrebollob.drawaday.domain.model.DrawImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Serializable
class DrawImageApiModel(
    @SerialName("image")
    val image: String,
    @SerialName("source")
    val source: String,
    @SerialName("publish_date")
    val publishDate: String
) {
    fun toDomain() = DrawImage(
        image = image,
        source = source,
        publishDate = try {
            LocalDate.parse(publishDate)
        } catch (exception: DateTimeParseException) {
            LocalDate.now()
        }
    )
}
