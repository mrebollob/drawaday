package com.mrebollob.drawaday.ui.drawing

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.InsetAwareTopAppBar
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import com.mrebollob.drawaday.utils.supportWideScreen
import kotlinx.coroutines.launch

@Composable
fun DrawingScreen(
    drawingId: String,
    onBack: () -> Unit
) {
    val drawImage = TestDataUtils.getTestDrawImage("$drawingId")
    var isBlackAndWhite by rememberSaveable { mutableStateOf(false) }

    DrawingScreen(
        drawImage = drawImage,
        onBack = onBack,
        isBlackAndWhite = isBlackAndWhite,
        onToggleBlackAndWhite = {
            isBlackAndWhite = isBlackAndWhite.not()
        }
    )
}

@Composable
fun DrawingScreen(
    drawImage: DrawImage,
    onBack: () -> Unit,
    isBlackAndWhite: Boolean,
    onToggleBlackAndWhite: () -> Unit
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
                onToggleBlackAndWhite = onToggleBlackAndWhite
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
            isBlackAndWhite = isBlackAndWhite
        )
    }
}

@Composable
private fun BottomBar(
    isBlackAndWhite: Boolean,
    onToggleBlackAndWhite: () -> Unit
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
            onToggleBlackAndWhite = { /*TODO*/ }
        )
    }
}
