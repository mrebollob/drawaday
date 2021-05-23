package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.state.UiState
import com.mrebollob.drawaday.ui.home.DrawImageCardHistory
import com.mrebollob.drawaday.ui.home.DrawImageCardTop
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
fun FeedScreen(
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
    val postsHistory = drawImages.subList(1, drawImages.size)

    LazyColumn(
        modifier = modifier,
        contentPadding = LocalWindowInsets.current.systemBars.toPaddingValues(top = false)
    ) {
        item { DrawImageTopSection(todayDrawImage, onDrawingClick) }
        item { DrawImageListHistorySection(postsHistory, onDrawingClick) }
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

@Composable
private fun DrawImageTopSection(
    drawImage: DrawImage,
    onDrawingClick: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = "Your drawing for today",
        style = MaterialTheme.typography.subtitle1
    )
    DrawImageCardTop(
        drawImage = drawImage,
        modifier = Modifier.clickable(onClick = { onDrawingClick(drawImage.id) })
    )
    DrawImageListDivider()
}

@Composable
private fun DrawImageListHistorySection(
    drawImages: List<DrawImage>,
    onDrawingClick: (String) -> Unit
) {
    Column {
        drawImages.forEach { drawImage ->
            DrawImageCardHistory(drawImage, onDrawingClick)
            DrawImageListDivider()
        }
    }
}

@Composable
private fun DrawImageListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
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
