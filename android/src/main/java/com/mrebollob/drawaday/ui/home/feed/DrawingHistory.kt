package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.VerticalGrid
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import kotlin.math.max

@Composable
fun DrawingHistory(
    drawings: List<DrawImage>,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.home_screen_history_category),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .wrapContentHeight()
        )
        VerticalGrid(Modifier.padding(horizontal = 16.dp)) {
            drawings.forEach { drawing ->
                DrawingHistoryItem(
                    drawing = drawing,
                    onDrawingClick = onDrawingClick,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun DrawingHistoryItem(
    drawing: DrawImage,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = modifier
            .clickable { onDrawingClick(drawing.id) },
        content = {
            Column {
                Image(
                    painter = rememberCoilPainter(
                        request = drawing.drawing,
                        fadeIn = true,
                        previewPlaceholder = R.drawable.placeholder_1,
                    ),
                    contentDescription = drawing.title,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .aspectRatio(1.45f)
                        .fillMaxWidth()
                )
                Text(
                    text = drawing.title,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(start = 8.dp)
                )
            }
        }
    )
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun DrawingHistoryItemPreview() {
    val drawImage = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        Surface {
            DrawingHistoryItem(
                drawing = drawImage,
                onDrawingClick = { /*TODO*/ }
            )
        }
    }
}
