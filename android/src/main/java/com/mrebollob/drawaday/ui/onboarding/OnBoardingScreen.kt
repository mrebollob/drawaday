package com.mrebollob.drawaday.ui.onboarding

import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.mrebollob.drawaday.analytics.AnalyticsEvent
import com.mrebollob.drawaday.analytics.AnalyticsManager
import com.mrebollob.drawaday.analytics.AnalyticsParameter
import com.mrebollob.drawaday.ui.theme.ColorTheme
import com.mrebollob.drawaday.ui.theme.CustomOrange1
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.supportWideScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onBoardingContent: List<OnBoardingContent>,
    onDonePressed: () -> Unit
) {
    val analyticsManager = get<AnalyticsManager>()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    ColorTheme(color = onBoardingContent[pagerState.currentPage].screenColor) {
        Surface(
            modifier = modifier.supportWideScreen()
        ) {
            Column(Modifier.fillMaxSize()) {
                HorizontalPager(
                    count = onBoardingContent.size,
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
                    onSkipClick = {
                        val bundle = Bundle()
                        bundle.putInt(
                            AnalyticsParameter.ONBOARDING_PAGE_NUMBER.key,
                            pagerState.currentPage
                        )
                        analyticsManager.trackEvent(AnalyticsEvent.ONBOARDING_SKIP, bundle)
                        onDonePressed()
                    },
                    onDoneClick = {
                        analyticsManager.trackEvent(AnalyticsEvent.ONBOARDING_DONE)
                        onDonePressed()
                    },
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
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            painter = painterResource(onBoardingContent.image),
            contentDescription = stringResource(id = onBoardingContent.title),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = onBoardingContent.title),
            color = Color.White,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = onBoardingContent.body),
            color = Color.White,
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
    onSkipClick: () -> Unit,
    onDoneClick: () -> Unit
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
                        .height(48.dp)
                        .clip(RoundedCornerShape(50)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = CustomOrange1),
                    onClick = onDoneClick,
                ) {
                    Text(
                        text = stringResource(id = R.string.on_boarding_screen_done),
                        color = Color.White,
                    )
                }
            } else {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onSkipClick,
                ) {
                    Text(
                        text = stringResource(id = R.string.on_boarding_screen_skip),
                        color = Color.White,
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
                        color = Color.White,
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
