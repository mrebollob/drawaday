package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.ImageCard
import com.mrebollob.drawaday.components.VerticalGrid
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import kotlinx.coroutines.launch

@Composable
fun DrawingHistory(
    drawings: List<DrawImage>,
    onDrawingClick: (DrawImage) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.home_screen_history_category),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(horizontal = 16.dp)
                .wrapContentHeight()
        )
        VerticalGrid(
            modifier
                .padding(horizontal = 8.dp)
        ) {
            if (drawings.isNotEmpty()) {
                ImagesHistory(drawings, onDrawingClick)
            } else {
                ImagesHistoryPlaceholder(snackbarHostState)
            }
        }
        Spacer(Modifier.height(4.dp))
    }
    Spacer(Modifier.height(32.dp))
}

@Composable
private fun ImagesHistory(
    drawings: List<DrawImage>,
    onDrawingClick: (DrawImage) -> Unit
) {
    drawings.forEach { drawing ->
        ImageCard(
            painter = rememberCoilPainter(
                request = drawing.getScaledImage(200),
                previewPlaceholder = R.drawable.placeholder,
            ),
            title = null,
            contentDescription = drawing.description,
            onClick = {
                onDrawingClick(drawing)
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun ImagesHistoryPlaceholder(
    snackbarHostState: SnackbarHostState
) {
    val snackbarErrorText = stringResource(id = R.string.home_screen_feed_history)
    val scope = rememberCoroutineScope()

    (0..2).forEach { _ ->
        ImageCard(
            painter = painterResource(id = R.drawable.ic_image_placeholder),
            title = null,
            contentDescription = stringResource(id = R.string.home_screen_feed_image_placeholder),
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = snackbarErrorText,
//                        actionLabel = snackbarActionLabel
                    )
                }
            },
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(8.dp),
            imageModifier = Modifier.padding(64.dp),
        )
    }
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun DrawingHistoryItemPreview() {
    val drawings = TestDataUtils.getTestDrawImages(8)
    val snackbarHostState = remember { SnackbarHostState() }

    DrawADayTheme {
        Surface {
            DrawingHistory(
                drawings = drawings,
                onDrawingClick = { /*TODO*/ },
                snackbarHostState = snackbarHostState
            )
        }
    }
}
