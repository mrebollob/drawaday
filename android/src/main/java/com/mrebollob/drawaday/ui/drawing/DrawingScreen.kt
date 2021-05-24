package com.mrebollob.drawaday.ui.drawing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.mrebollob.drawaday.domain.model.DrawImage
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

    DrawingScreen(
        drawImage = drawImage,
        onBack = onBack,
        isBlackAndWhite = isBlackAndWhite,
        gridSize = gridSize.dp,
        onToggleBlackAndWhite = {
            isBlackAndWhite = isBlackAndWhite.not()
        },
        onToggleGridSize = {
            gridSize = when (gridSize) {
                0 -> 100
                100 -> 50
                else -> 0
            }
        }
    )
}

@Composable
fun DrawingScreen(
    drawImage: DrawImage,
    onBack: () -> Unit,
    isBlackAndWhite: Boolean,
    gridSize: Dp,
    onToggleBlackAndWhite: () -> Unit,
    onToggleGridSize: () -> Unit
) {
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                title = {
                    Text(
                        text = "Published in: ${drawImage.publishDate}",
                        style = MaterialTheme.typography.subtitle2,
                        color = LocalContentColor.current
                    )
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
                onToggleBlackAndWhite = onToggleBlackAndWhite,
                onToggleGridSize = onToggleGridSize
            )
        }
    ) { innerPadding ->
        DrawingContent(
            drawImage = drawImage,
            modifier = Modifier
                // innerPadding takes into account the top and bottom bar
                .padding(innerPadding)
                // offset content in landscape mode to account for the navigation bar
                .navigationBarsPadding(bottom = false)
                // center content in landscape mode
                .supportWideScreen(),
            isBlackAndWhite = isBlackAndWhite,
            gridSize = gridSize
        )
    }
}

@Composable
private fun BottomBar(
    isBlackAndWhite: Boolean,
    gridSize: Dp,
    onToggleBlackAndWhite: () -> Unit,
    onToggleGridSize: () -> Unit
) {
    Surface(elevation = 8.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .navigationBarsPadding()
                .height(56.dp)
                .fillMaxWidth()
        ) {
            BlackAndWhiteButton(
                isBlackAndWhite = isBlackAndWhite,
                onClick = onToggleBlackAndWhite
            )
            GridViewButton(
                isGridEnabled = gridSize != 0.dp,
                onClick = onToggleGridSize
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
            onToggleBlackAndWhite = { /*TODO*/ },
            onToggleGridSize = { /*TODO*/ }
        )
    }
}
