package com.mrebollob.drawaday.ui.drawing

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.InsetAwareTopAppBar
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.state.UiState
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import com.mrebollob.drawaday.utils.supportWideScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun DrawingScreen(
    drawingId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = getViewModel<DrawingViewModel> { parametersOf(drawingId) }
    val image = viewModel.drawImage.collectAsState()

    var isBlackAndWhite by rememberSaveable { mutableStateOf(false) }
    var gridSize by rememberSaveable { mutableStateOf(0) }
    val scale = remember { mutableStateOf(1f) }
    val rotation = remember { mutableStateOf(0f) }

    DrawingScreen(
        drawImage = image.value,
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
        },
        onCreditsClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(image.value.data?.source))
            context.startActivity(browserIntent)
        }
    )
}

@Composable
private fun DrawingScreen(
    drawImage: UiState<DrawImage>,
    onBack: () -> Unit,
    isBlackAndWhite: Boolean,
    gridSize: Dp,
    scale: Float,
    rotation: Float,
    onToggleBlackAndWhite: () -> Unit,
    onToggleGridSize: () -> Unit,
    onToggleScale: () -> Unit,
    onToggleRotation: () -> Unit,
    onCreditsClick: () -> Unit
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
                onToggleRotation = onToggleRotation,
                onCreditsClick = onCreditsClick
            )
        }
    ) { innerPadding ->
        if (drawImage.data != null) {
            DrawingContent(
                drawImage = drawImage.data,
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(innerPadding)
                    .navigationBarsPadding(bottom = false)
                    .supportWideScreen(),
                isBlackAndWhite = isBlackAndWhite,
                gridSize = gridSize,
                scale = scale,
                rotation = rotation
            )
        }
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
    onToggleRotation: () -> Unit,
    onCreditsClick: () -> Unit
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
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = { onCreditsClick() },
            ) {
                Text(
                    text = stringResource(id = R.string.drawing_screen_credits),
                    color = MaterialTheme.colors.primary,
                )
            }
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
            drawImage = UiState(data = drawImage),
            onBack = { /*TODO*/ },
            isBlackAndWhite = true,
            gridSize = 100.dp,
            scale = 1f,
            rotation = 0f,
            onToggleBlackAndWhite = { /*TODO*/ },
            onToggleGridSize = { /*TODO*/ },
            onToggleScale = { /*TODO*/ },
            onToggleRotation = { /*TODO*/ },
            onCreditsClick = { /*TODO*/ }
        )
    }
}
