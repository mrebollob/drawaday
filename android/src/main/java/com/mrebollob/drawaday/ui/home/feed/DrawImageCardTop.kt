package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

@Composable
fun DrawImageCardTop(drawImage: DrawImage, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
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
        Spacer(Modifier.height(16.dp))

        Text(
            text = drawImage.title,
            style = typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = drawImage.title,
            style = typography.subtitle2,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = drawImage.publishDate.toString(),
                style = typography.subtitle2
            )
        }
    }
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun DrawImageCardTopPreview() {
    val drawImage = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        Surface {
            DrawImageCardTop(drawImage)
        }
    }
}
