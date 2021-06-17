package com.mrebollob.drawaday.ui.drawing

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.InsetAwareTopAppBar
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import com.mrebollob.drawaday.utils.supportWideScreen

@Composable
fun DrawingScreen(
    drawingId: String,
    onBack: () -> Unit
) {
    val drawImage = TestDataUtils.getTestDrawImage(drawingId)
    var isBlackAndWhite by rememberSaveable { mutableStateOf(false) }
    var gridSize by rememberSaveable { mutableStateOf(0) }
    val scale = remember { mutableStateOf(1f) }
    val rotation = remember { mutableStateOf(0f) }

    DrawingScreen(
        drawImage = drawImage,
        onBack = onBack,
        isBlackAndWhite = isBlackAndWhite,
        gridSize = gridSize.dp,
        scale = scale.value,
        rotation = rotation.value,
        onToggleBlackAndWhite = {
            isBlackAndWhite = isBlackAndWhite.not()
        },
        onToggleGridSize = {
            gridSize = when (gridSize) {
                0 -> 100
                100 -> 50
                else -> 0
            }
        },
        onToggleScale = {
            scale.value = when (scale.value) {
                1f -> 1.5f
                1.5f -> 2f
                2f -> 2.5f
                else -> 1f
            }
        },
        onToggleRotation = {
            rotation.value = rotation.value + 90f
        }
    )
}

@Composable
private fun DrawingScreen(
    drawImage: DrawImage,
    onBack: () -> Unit,
    isBlackAndWhite: Boolean,
    gridSize: Dp,
    scale: Float,
    rotation: Float,
    onToggleBlackAndWhite: () -> Unit,
    onToggleGridSize: () -> Unit,
    onToggleScale: () -> Unit,
    onToggleRotation: () -> Unit
) {
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                elevation = 0.dp,
                title = {
//                    Text(
//                        text = drawImage.title,
//                        style = MaterialTheme.typography.subtitle2,
//                        color = LocalContentColor.current
//                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_up)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                isBlackAndWhite = isBlackAndWhite,
                gridSize = gridSize,
                scale = scale,
                onToggleBlackAndWhite = onToggleBlackAndWhite,
                onToggleGridSize = onToggleGridSize,
                onToggleScale = onToggleScale,
                onToggleRotation = onToggleRotation
            )
        }
    ) { innerPadding ->
        DrawingContent(
            drawImage = drawImage,
            modifier = Modifier
                .padding(innerPadding)
                .navigationBarsPadding(bottom = false)
                .supportWideScreen()
                .background(MaterialTheme.colors.surface),
            isBlackAndWhite = isBlackAndWhite,
            gridSize = gridSize,
            scale = scale,
            rotation = rotation
        )
    }
}

@Composable
private fun BottomBar(
    isBlackAndWhite: Boolean,
    gridSize: Dp,
    scale: Float,
    onToggleBlackAndWhite: () -> Unit,
    onToggleGridSize: () -> Unit,
    onToggleScale: () -> Unit,
    onToggleRotation: () -> Unit
) {
    Surface(elevation = 8.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .navigationBarsPadding()
                .height(56.dp)
                .fillMaxWidth()
        ) {
            BottomBarButton(
                isEnabled = isBlackAndWhite,
                onClick = onToggleBlackAndWhite,
                enableIcon = Icons.Filled.Palette,
                disableIcon = Icons.Filled.FilterBAndW,
                enableText = R.string.drawing_screen_color_mode,
                disableText = R.string.drawing_screen_black_and_white_mode,
                tint = MaterialTheme.colors.primary
            )
            BottomBarButton(
                isEnabled = gridSize == 50.dp,
                onClick = onToggleGridSize,
                enableIcon = Icons.Filled.GridOff,
                disableIcon = Icons.Filled.GridOn,
                enableText = R.string.drawing_screen_hide_grid,
                disableText = R.string.drawing_screen_show_grid,
                tint = MaterialTheme.colors.primary
            )
            BottomBarButton(
                isEnabled = scale == 2.5f,
                onClick = onToggleScale,
                enableIcon = Icons.Filled.ZoomOut,
                disableIcon = Icons.Filled.ZoomIn,
                enableText = R.string.drawing_screen_zoom_out,
                disableText = R.string.drawing_screen_zoom_in,
                tint = MaterialTheme.colors.primary
            )
            BottomBarButton(
                isEnabled = false,
                onClick = onToggleRotation,
                enableIcon = Icons.Filled.RotateRight,
                disableIcon = Icons.Filled.RotateRight,
                enableText = R.string.drawing_screen_rotate,
                disableText = R.string.drawing_screen_rotate,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Preview("Article screen")
@Preview("Article screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Article screen (big font)", fontScale = 1.5f)
@Preview("Article screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun ArticlePreview() {
    val drawImage = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        DrawingScreen(
            drawImage = drawImage,
            onBack = { /*TODO*/ },
            isBlackAndWhite = true,
            gridSize = 100.dp,
            scale = 1f,
            rotation = 0f,
            onToggleBlackAndWhite = { /*TODO*/ },
            onToggleGridSize = { /*TODO*/ },
            onToggleScale = { /*TODO*/ },
            onToggleRotation = { /*TODO*/ }
        )
    }
}
