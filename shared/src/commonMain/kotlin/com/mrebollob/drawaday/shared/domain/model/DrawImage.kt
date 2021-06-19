package com.mrebollob.drawaday.shared.domain.model

data class DrawImage(
    val id: String,
    val image: String,
    val source: String,
    val author: String,
    val description: String,
    val index: Int
) {
    fun getScaledImage(widthInPx: Int) = "$image?fit=crop&w=$widthInPx&q=80"
}
