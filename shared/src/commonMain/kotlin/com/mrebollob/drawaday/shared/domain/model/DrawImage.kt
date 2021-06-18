package com.mrebollob.drawaday.shared.domain.model

data class DrawImage(
    val id: String,
    val title: String,
    val drawing: String,
    val source: String,
    val publishDate: String
) {
    fun getScaledDrawing(widthInPx: Int) = drawing // "$drawing?fit=crop&w=$widthInPx&q=80"
}
