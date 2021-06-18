package com.mrebollob.drawaday.ui.drawing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mrebollob.drawaday.ui.theme.CustomTeal500
import com.mrebollob.drawaday.ui.theme.DrawADayTheme

@Composable
fun DrawingGridView(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp
) {
    val rows = 20

    Box(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            for (i in 0..rows) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .width(size)
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(color = CustomTeal500)
                    )
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            for (i in 0..rows) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(size)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = CustomTeal500)
                    )
                }
            }
        }
    }
}

@Preview("Default view")
@Composable
fun DrawingGridViewPreview() {
    DrawADayTheme {
        DrawingGridView()
    }
}
