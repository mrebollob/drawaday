package com.mrebollob.drawaday.ui.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.ui.theme.ColorTheme
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.supportWideScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onBoardingContent: List<OnBoardingContent>,
    onDonePressed: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = onBoardingContent.size)

    ColorTheme(color = onBoardingContent[pagerState.currentPage].color) {
        Surface(
            modifier = modifier.supportWideScreen()
        ) {
            Column(Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    OnBoardingContentView(

                        onBoardingContent = onBoardingContent[page]
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                )
                NavigationBottomBar(
                    showDone = onBoardingContent.size - 1 == pagerState.currentPage,
                    onNextPressed = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    },
                    onDonePressed = onDonePressed,
                )
            }
        }
    }
}

@Composable
private fun OnBoardingContentView(
    modifier: Modifier = Modifier,
    onBoardingContent: OnBoardingContent
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource(onBoardingContent.image),
            contentDescription = stringResource(id = onBoardingContent.title),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.on_boarding_screen_title_1),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.on_boarding_screen_body_1),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight()
        )
    }
}

@Composable
private fun NavigationBottomBar(
    showDone: Boolean,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if (showDone) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onDonePressed,
                ) {
                    Text(
                        text = stringResource(id = R.string.on_boarding_screen_done),
                        color = Color.White
                    )
                }
            } else {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onDonePressed,
                ) {
                    Text(
                        text = stringResource(id = R.string.on_boarding_screen_skip),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onNextPressed,
                ) {
                    Text(
                        text = stringResource(id = R.string.on_boarding_screen_next),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview("OnBoarding screen")
@Preview("OnBoarding screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OnBoardingScreenPreview() {
    DrawADayTheme {
        OnBoardingScreen(
            onBoardingContent = OnBoardingContent.getOnBoardingContent(),
            onDonePressed = { /*TODO*/ },
        )
    }
}
