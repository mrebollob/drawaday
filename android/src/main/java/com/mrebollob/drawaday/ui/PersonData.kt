package com.mrebollob.drawaday.ui

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.mrebollob.drawaday.data.network.Assignment

class PersonProvider : CollectionPreviewParameterProvider<Assignment>(
    listOf(
        Assignment("ISS", "Chris Cassidy"),
        Assignment("ISS", "Anatoli Ivanishin")
    )
)
