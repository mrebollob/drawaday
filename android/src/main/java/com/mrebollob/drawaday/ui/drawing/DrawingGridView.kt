package com.mrebollob.drawaday.ui.drawing

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrebollob.drawaday.ui.theme.DrawADayTheme

@Composable
private fun DrawingGridView() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Preview("Default view")
@Composable
fun DrawingGridViewPreview() {
    DrawADayTheme {
        DrawingGridView(
        )
    }
}
