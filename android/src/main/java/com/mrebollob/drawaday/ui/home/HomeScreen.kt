package com.mrebollob.drawaday.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.state.UiState
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import com.mrebollob.drawaday.utils.supportWideScreen
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(
    navigateToDrawImage: (DrawImage) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
//    val homeViewModel = getViewModel<HomeViewModel>()
//    val drawImages = homeViewModel.drawImages.collectAsState()

    HomeScreen(
        drawImages = UiState(data = TestDataUtils.getTestDrawImages(11)),
        navigateToDrawImage = navigateToDrawImage,
        onRefreshDrawImages = {},
        scaffoldState = scaffoldState
    )
}

/**
 * Responsible for displaying the Home Screen of this application.
 *
 * Stateless composable is not coupled to any specific state management.
 *
 * @param drawImages (state) the data to show on the screen
 * @param favorites (state) favorite posts
 * @param onToggleFavorite (event) toggles favorite for a post
 * @param onRefreshPosts (event) request a refresh of posts
 * @param onErrorDismiss (event) request the current error be dismissed
 * @param navigateToArticle (event) request navigation to Article screen
 * @param openDrawer (event) request opening the app drawer
 * @param scaffoldState (state) state for the [Scaffold] component on this screen
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    drawImages: UiState<List<DrawImage>>,
    navigateToDrawImage: (DrawImage) -> Unit,
    onRefreshDrawImages: () -> Unit,
    scaffoldState: ScaffoldState
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text("Draw a day") })
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
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
                    navigateToDrawImage = navigateToDrawImage,
                    modifier = modifier.supportWideScreen()
                )
            }
        )
    }
}

/**
 * Display an initial empty state or swipe to refresh content.
 *
 * @param empty (state) when true, display [emptyContent]
 * @param emptyContent (slot) the content to display for the empty state
 * @param loading (state) when true, display a loading spinner over [content]
 * @param onRefresh (event) event to request refresh
 * @param content (slot) the main content to show
 */
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

/**
 * Responsible for displaying any error conditions around [DrawImageList].
 *
 * @param drawImages (state) list of drawImages and error state to display
 * @param onRefresh (event) request to refresh data
 * @param navigateToDrawImage (event) request navigation to drawImage screen
 * @param modifier modifier for root element
 */
@Composable
private fun HomeScreenErrorAndContent(
    drawImages: UiState<List<DrawImage>>,
    onRefresh: () -> Unit,
    navigateToDrawImage: (DrawImage) -> Unit,
    modifier: Modifier = Modifier
) {
    if (drawImages.data != null) {
        DrawImageList(
            drawImages = drawImages.data,
            navigateToDrawImage = navigateToDrawImage,
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

/**
 * Display a list of drawImages.
 *
 * When a drawImage is clicked on, [navigateToDrawImage] will be called to navigate to the detail screen
 * for that drawImage.
 *
 * @param drawImages (state) the list to display
 * @param navigateToDrawImage (event) request navigation to DrawImage screen
 */
@Composable
private fun DrawImageList(
    drawImages: List<DrawImage>,
    navigateToDrawImage: (drawImage: DrawImage) -> Unit,
    modifier: Modifier = Modifier
) {
    val todayDrawImage = drawImages[0]
//    val postsSimple = drawImages.subList(1, 2)
//    val postsPopular = drawImages.subList(2, 7)
//    val postsHistory = drawImages.subList(7, 10)

    LazyColumn(
        modifier = modifier,
        contentPadding = LocalWindowInsets.current.systemBars.toPaddingValues(top = false)
    ) {
        item { DrawImageTopSection(todayDrawImage, navigateToDrawImage) }
//        item { PostListSimpleSection(postsSimple, navigateToArticle, favorites, onToggleFavorite) }
//        item { PostListPopularSection(postsPopular, navigateToArticle) }
//        item { PostListHistorySection(postsHistory, navigateToArticle) }
    }
}

/**
 * Full screen circular progress indicator
 */
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

/**
 * Top section of [DrawImageList]
 *
 * @param drawImage (state) highlighted post to display
 * @param navigateToDrawImage (event) request navigation to DrawImage screen
 */
@Composable
private fun DrawImageTopSection(drawImage: DrawImage, navigateToDrawImage: (DrawImage) -> Unit) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = "Your drawing for today",
        style = MaterialTheme.typography.subtitle1
    )
    DrawImageCardTop(
        drawImage = drawImage,
        modifier = Modifier.clickable(onClick = { navigateToDrawImage(drawImage) })
    )
    DrawImageListDivider()
}

/**
 * Full-width divider with padding for [DrawImageList]
 */
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
fun PreviewHomeScreen() {

    val drawImages = TestDataUtils.getTestDrawImages(11)

    DrawADayTheme {
        HomeScreen(
            drawImages = UiState(data = drawImages),
            navigateToDrawImage = { /*TODO*/ },
            onRefreshDrawImages = { /*TODO*/ },
            scaffoldState = rememberScaffoldState()
        )
    }
}
