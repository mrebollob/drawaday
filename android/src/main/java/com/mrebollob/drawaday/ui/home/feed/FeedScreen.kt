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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.analytics.AnalyticsEvent
import com.mrebollob.drawaday.analytics.AnalyticsManager
import com.mrebollob.drawaday.analytics.toBundle
import com.mrebollob.drawaday.components.ErrorSnackbar
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.state.UiState
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import com.mrebollob.drawaday.utils.supportWideScreen
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun FeedScreen(
    onProfileClick: () -> Unit,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val analyticsManager = get<AnalyticsManager>()
    val viewModel = getViewModel<FeedViewModel>()
    val drawImages = viewModel.drawImages.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    FeedScreen(
        drawImages = drawImages.value,
        onProfileClick = onProfileClick,
        onDrawingClick = { image ->
            analyticsManager.trackEvent(AnalyticsEvent.NAVIGATE_TO_IMAGE, image.toBundle())
            onDrawingClick(image.id)
        },
        onRefreshDrawImages = { viewModel.loadImages(true) },
        onResetStartDate = { viewModel.resetData() },
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FeedScreen(
    drawImages: UiState<List<DrawImage>>,
    onProfileClick: () -> Unit,
    onDrawingClick: (DrawImage) -> Unit,
    onRefreshDrawImages: () -> Unit,
    onResetStartDate: () -> Unit,
    snackbarHostState: SnackbarHostState,
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
                    onRefreshClick = {
                        onRefreshDrawImages()
                    },
                    onResetContentClick = {
                        onResetStartDate()
                    },
                    onDrawingClick = onDrawingClick,
                    snackbarHostState = snackbarHostState,
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface)
                        .supportWideScreen()
                )
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
            modifier = Modifier.align(Alignment.BottomCenter)
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
    onRefreshClick: () -> Unit,
    onResetContentClick: () -> Unit,
    onDrawingClick: (DrawImage) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    if (drawImages.data != null && drawImages.data.isNotEmpty()) {
        DrawImageList(
            drawImages = drawImages.data,
            onProfileClick = onProfileClick,
            onDrawingClick = onDrawingClick,
            snackbarHostState = snackbarHostState,
            modifier = modifier
        )
    } else if (drawImages.hasError) {
        TextButton(
            onClick = onRefreshClick,
            modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.home_screen_feed_refresh_content),
                textAlign = TextAlign.Center
            )
        }
    } else {
        TextButton(
            onClick = onResetContentClick,
            modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.home_screen_feed_reset_content),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun DrawImageList(
    drawImages: List<DrawImage>,
    onProfileClick: () -> Unit,
    onDrawingClick: (DrawImage) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    if (drawImages.isNotEmpty()) {
        val todayDrawImage = drawImages[0]
        val drawingsHistory = drawImages.subList(1, drawImages.size)

        LazyColumn(
            modifier = modifier,
            contentPadding = LocalWindowInsets.current.systemBars.toPaddingValues(top = false)
        ) {
            item { UserGreetingsRow(onProfileClick) }
            item { DrawImageCardTop(todayDrawImage, onDrawingClick) }
            item { DrawingHistory(drawingsHistory, onDrawingClick, snackbarHostState) }
        }
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
    val snackbarHostState = remember { SnackbarHostState() }

    DrawADayTheme {
        FeedScreen(
            drawImages = UiState(data = drawImages),
            onProfileClick = {},
            onDrawingClick = {},
            onRefreshDrawImages = {},
            onResetStartDate = {},
            snackbarHostState = snackbarHostState
        )
    }
}
