package com.mrebollob.drawaday.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils

@Composable
fun DrawImageCardHistory(
    drawImage: DrawImage,
    onDrawingClick: (String) -> Unit
) {
    Row(
        Modifier
            .clickable(onClick = { onDrawingClick(drawImage.id) })
            .padding(16.dp)
    ) {
        PostImage(
            drawImage = drawImage,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(Modifier.weight(1f)) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "BASED ON YOUR HISTORY",
                    style = MaterialTheme.typography.overline
                )
            }
            PostTitle(drawImage = drawImage)
            AuthorAndReadTime(
                drawImage = drawImage,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun AuthorAndReadTime(
    drawImage: DrawImage,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            val textStyle = MaterialTheme.typography.body2
            Text(
                text = drawImage.title,
                style = textStyle
            )
            Text(
                text = " - ${drawImage.title}",
                style = textStyle
            )
        }
    }
}

@Composable
fun PostImage(
    drawImage: DrawImage,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberCoilPainter(
            request = drawImage.drawing,
            fadeIn = true,
            previewPlaceholder = com.mrebollob.drawaday.R.drawable.placeholder_1,
        ),
        contentDescription = drawImage.title,
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun PostTitle(drawImage: DrawImage) {
    Text(drawImage.title, style = MaterialTheme.typography.subtitle1)
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun DrawImageCardHistoryPreview() {
    val drawImage = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        Surface {
            DrawImageCardHistory(drawImage) {}
        }
    }
}
