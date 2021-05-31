package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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

@Composable
fun FeedScreen(
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
//    val homeViewModel = getViewModel<HomeViewModel>()
//    val drawImages = homeViewModel.drawImages.collectAsState()

    FeedScreen(
        drawImages = UiState(data = TestDataUtils.getTestDrawImages(11)),
        onDrawingClick = onDrawingClick,
        onRefreshDrawImages = {},
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FeedScreen(
    drawImages: UiState<List<DrawImage>>,
    onDrawingClick: (String) -> Unit,
    onRefreshDrawImages: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("Draw a day") })
        }
    ) {
        LoadingContent(
            empty = drawImages.initialLoad,
            emptyContent = { FullScreenLoading() },
            loading = drawImages.loading,
            onRefresh = onRefreshDrawImages,
            content = {
                HomeScreenErrorAndContent(
                    drawImages = drawImages,
                    onRefresh = {
                        onRefreshDrawImages()
                    },
                    onDrawingClick = onDrawingClick,
                    modifier = modifier.supportWideScreen()
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
    onRefresh: () -> Unit,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (drawImages.data != null) {
        DrawImageList(
            drawImages = drawImages.data,
            onDrawingClick = onDrawingClick,
            modifier = modifier
        )
    } else if (!drawImages.hasError) {
        // if there are no posts, and no error, let the user refresh manually
        TextButton(onClick = onRefresh, modifier.fillMaxSize()) {
            Text("Tap to load content", textAlign = TextAlign.Center)
        }
    } else {
        // there's currently an error showing, don't show any content
        Box(modifier.fillMaxSize()) { /* empty screen */ }
    }
}

@Composable
private fun DrawImageList(
    drawImages: List<DrawImage>,
    onDrawingClick: (drawingId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val todayDrawImage = drawImages[0]
    val drawingsHistory = drawImages.subList(1, drawImages.size)

    LazyColumn(
        modifier = modifier,
        contentPadding = LocalWindowInsets.current.systemBars.toPaddingValues(top = false)
    ) {
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
            onDrawingClick = { /*TODO*/ },
            onRefreshDrawImages = { /*TODO*/ }
        )
    }
}
