package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.state.UiState
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import com.mrebollob.drawaday.utils.supportWideScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun FeedScreen(
    onProfileClick: () -> Unit,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val feedViewModel = getViewModel<FeedViewModel>()
    val drawImages = feedViewModel.drawImages.collectAsState()

    FeedScreen(
        drawImages = UiState(data = TestDataUtils.getTestDrawImages(11)),
        onProfileClick = onProfileClick,
        onDrawingClick = onDrawingClick,
        onRefreshDrawImages = {},
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FeedScreen(
    drawImages: UiState<List<DrawImage>>,
    onProfileClick: () -> Unit,
    onDrawingClick: (String) -> Unit,
    onRefreshDrawImages: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) {
        LoadingContent(
            empty = drawImages.initialLoad,
            emptyContent = { FullScreenLoading() },
            loading = drawImages.loading,
            onRefresh = onRefreshDrawImages,
            content = {
                HomeScreenErrorAndContent(
                    drawImages = drawImages,
                    onProfileClick = onProfileClick,
                    onRefresh = {
                        onRefreshDrawImages()
                    },
                    onDrawingClick = onDrawingClick,
                    modifier = modifier
                        .supportWideScreen()
                        .background(MaterialTheme.colors.primary)
                )
            }
        )
    }
}

@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

@Composable
private fun HomeScreenErrorAndContent(
    drawImages: UiState<List<DrawImage>>,
    onProfileClick: () -> Unit,
    onRefresh: () -> Unit,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (drawImages.data != null) {
        DrawImageList(
            drawImages = drawImages.data,
            onProfileClick = onProfileClick,
            onDrawingClick = onDrawingClick,
            modifier = modifier
        )
    } else if (!drawImages.hasError) {
        TextButton(onClick = onRefresh, modifier.fillMaxSize()) {
            Text("Tap to load content", textAlign = TextAlign.Center)
        }
    } else {
        Box(modifier.fillMaxSize()) { /* empty screen */ }
    }
}

@Composable
private fun DrawImageList(
    drawImages: List<DrawImage>,
    onProfileClick: () -> Unit,
    onDrawingClick: (drawingId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val todayDrawImage = drawImages[0]
    val drawingsHistory = drawImages.subList(1, drawImages.size)

    LazyColumn(
        modifier = modifier,
        contentPadding = LocalWindowInsets.current.systemBars.toPaddingValues(top = false)
    ) {
        item { UserGreetingsRow(onProfileClick) }
        item { DrawImageCardTop(todayDrawImage, onDrawingClick) }
        item { DrawingHistory(drawingsHistory, onDrawingClick) }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Preview("Home screen")
@Preview("Home screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Home screen (big font)", fontScale = 1.5f)
@Preview("Home screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun HomeScreenPreview() {
    val drawImages = TestDataUtils.getTestDrawImages(11)
    DrawADayTheme {
        FeedScreen(
            drawImages = UiState(data = drawImages),
            onProfileClick = { /*TODO*/ },
            onDrawingClick = { /*TODO*/ },
            onRefreshDrawImages = { /*TODO*/ }
        )
    }
}
