package com.mrebollob.drawaday.ui.drawing

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils

private val defaultSpacerSize = 16.dp

@Composable
fun DrawingContent(
    drawImage: DrawImage,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = defaultSpacerSize)
    ) {
        item {
            Spacer(Modifier.height(defaultSpacerSize))
            DrawingImage(drawImage)
        }
    }
}

@Composable
private fun DrawingImage(
    drawImage: DrawImage
) {
    val imageModifier = Modifier
        .heightIn(min = 180.dp)
        .fillMaxWidth()
        .clip(shape = MaterialTheme.shapes.medium)

    Image(
        painter = rememberCoilPainter(
            request = drawImage.drawing,
            fadeIn = true,
            previewPlaceholder = R.drawable.placeholder_1,
        ),
        contentDescription = drawImage.title,
        modifier = imageModifier,
        contentScale = ContentScale.Crop
    )
    Spacer(Modifier.height(defaultSpacerSize))
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun PreviewPost() {
    val drawImage = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        Surface {
            DrawingContent(drawImage = drawImage)
        }
    }
}
