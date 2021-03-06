package com.mrebollob.drawaday.ui.home.feed

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.ImageCard
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils

@Composable
fun DrawImageCardTop(
    drawing: DrawImage,
    onDrawingClick: (DrawImage) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.home_screen_today_category),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(vertical = 8.dp)
                .wrapContentHeight()
        )

        ImageCard(
            painter = rememberCoilPainter(
                request = drawing.getScaledImage(800),
                previewPlaceholder = R.drawable.placeholder,
            ),
            title = null,
            contentDescription = drawing.description,
            onClick = {
                onDrawingClick(drawing)
            }
        )
    }
}

@Preview("Default view")
@Composable
fun DrawImageCardTopPreview() {
    val drawing = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        Surface {
            DrawImageCardTop(
                drawing = drawing,
                onDrawingClick = { /* TODO */ }
            )
        }
    }
}
