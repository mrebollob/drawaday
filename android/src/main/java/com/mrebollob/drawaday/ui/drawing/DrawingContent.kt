package com.mrebollob.drawaday.ui.drawing

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterBAndW
import androidx.compose.material.icons.outlined.FilterBAndW
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
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
    modifier: Modifier = Modifier,
    isBlackAndWhite: Boolean,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = defaultSpacerSize)
    ) {
        item {
            Spacer(Modifier.height(defaultSpacerSize))
            DrawingImage(
                drawImage = drawImage,
                isBlackAndWhite = isBlackAndWhite
            )
        }
    }
}

@Composable
private fun DrawingImage(
    drawImage: DrawImage,
    isBlackAndWhite: Boolean,
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
        contentScale = ContentScale.Crop,
        colorFilter = if (isBlackAndWhite) {
            ColorFilter.colorMatrix(ColorMatrix().apply {
                setToSaturation(0f)
            })
        } else {
            null
        }
    )
    Spacer(Modifier.height(defaultSpacerSize))
}

@Composable
fun BlackAndWhiteButton(
    isBlackAndWhite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clickLabel = stringResource(
        if (isBlackAndWhite) R.string.drawing_screen_color_mode else R.string.drawing_screen_black_and_white_mode
    )
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        IconToggleButton(
            checked = isBlackAndWhite,
            onCheckedChange = { onClick() },
            modifier = modifier.semantics {
                this.onClick(label = clickLabel, action = null)
            }
        ) {
            Icon(
                imageVector = if (isBlackAndWhite) Icons.Filled.FilterBAndW else Icons.Outlined.FilterBAndW,
                contentDescription = stringResource(id = R.string.drawing_screen_black_and_white_mode)
            )
        }
    }
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun DrawingContentPreview() {
    val drawImage = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        Surface {
            DrawingContent(
                drawImage = drawImage,
                isBlackAndWhite = false
            )
        }
    }
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun BlackAndWhiteButtonPreview() {
    DrawADayTheme {
        Surface {
            BlackAndWhiteButton(
                isBlackAndWhite = true,
                onClick = { /*TODO*/ }
            )
        }
    }
}
