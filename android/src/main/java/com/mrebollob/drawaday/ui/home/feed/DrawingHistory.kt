package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.ImageCard
import com.mrebollob.drawaday.components.VerticalGrid
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils

@Composable
fun DrawingHistory(
    drawings: List<DrawImage>,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (drawings.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.home_screen_history_category),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .heightIn(min = 56.dp)
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight()
            )
            VerticalGrid(
                modifier
                    .padding(horizontal = 8.dp)
            ) {
                drawings.forEach { drawing ->
                    ImageCard(
                        painter = rememberCoilPainter(
                            request = drawing.getScaledDrawing(200),
                            previewPlaceholder = R.drawable.placeholder,
                        ),
                        title = drawing.title,
                        contentDescription = drawing.title,
                        onClick = {
                            onDrawingClick(drawing.id)
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
        }
        Spacer(Modifier.height(32.dp))
    }
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun DrawingHistoryItemPreview() {
    val drawings = TestDataUtils.getTestDrawImages(8)
    DrawADayTheme {
        Surface {
            DrawingHistory(
                drawings = drawings,
                onDrawingClick = { /*TODO*/ }
            )
        }
    }
}
