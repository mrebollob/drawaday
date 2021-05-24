package com.mrebollob.drawaday.ui.drawing

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterBAndW
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils

@Composable
fun DrawingContent(
    drawImage: DrawImage,
    isBlackAndWhite: Boolean,
    gridSize: Dp,
    scale: Float,
    rotation: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DrawingImage(
            drawImage = drawImage,
            isBlackAndWhite = isBlackAndWhite,
            scale = scale,
            rotation = rotation
        )
        if (gridSize != 0.dp) {
            DrawingGridView(
                size = gridSize
            )
        }
    }
}

@Composable
private fun DrawingImage(
    drawImage: DrawImage,
    isBlackAndWhite: Boolean,
    scale: Float,
    rotation: Float,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberCoilPainter(
            request = drawImage.drawing,
            fadeIn = true,
            previewPlaceholder = R.drawable.placeholder,
        ),
        contentDescription = drawImage.title,
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(
                // adding some zoom limits (min 50%, max 200%)
                scaleX = maxOf(.5f, minOf(3f, scale)),
                scaleY = maxOf(.5f, minOf(3f, scale)),
                rotationZ = rotation
            ),
        contentScale = ContentScale.Fit,
        colorFilter = if (isBlackAndWhite) {
            ColorFilter.colorMatrix(ColorMatrix().apply {
                setToSaturation(0f)
            })
        } else {
            null
        }
    )
}

@Composable
fun BottomBarButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    enableIcon: ImageVector,
    disableIcon: ImageVector,
    @StringRes enableText: Int,
    @StringRes disableText: Int,
    modifier: Modifier = Modifier
) {
    val clickLabel = stringResource(
        if (isEnabled) enableText else disableText
    )
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        IconToggleButton(
            checked = isEnabled,
            onCheckedChange = { onClick() },
            modifier = modifier.semantics {
                this.onClick(label = clickLabel, action = null)
            }
        ) {
            Icon(
                imageVector = if (isEnabled) enableIcon else disableIcon,
                contentDescription = clickLabel
            )
        }
    }
}

@Preview("DrawingContent")
@Composable
fun DrawingContentPreview() {
    val drawImage = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        DrawingContent(
            drawImage = drawImage,
            isBlackAndWhite = false,
            gridSize = 100.dp,
            scale = 1f,
            rotation = 1f
        )
    }
}

@Preview("BottomBarButton")
@Composable
fun BottomBarButtonPreview() {
    DrawADayTheme {
        Surface {
            BottomBarButton(
                isEnabled = true,
                onClick = { /*TODO*/ },
                enableIcon = Icons.Filled.FilterBAndW,
                disableIcon = Icons.Filled.FilterBAndW,
                enableText = R.string.drawing_screen_color_mode,
                disableText = R.string.drawing_screen_black_and_white_mode,
            )
        }
    }
}
