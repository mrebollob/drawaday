package com.mrebollob.drawaday.ui.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.ui.theme.ColorTheme
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.supportWideScreen

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onBoardingState: OnBoardingState,
    onSkipPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
) {

    val contentState = remember(onBoardingState.currentIndex) {
        onBoardingState.contentStates[onBoardingState.currentIndex]
    }

    ColorTheme(
        colors = contentState.content.colors
    ) {
        Surface(
            modifier = modifier.supportWideScreen()
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .wrapContentSize(Alignment.TopCenter)
            ) {
                Image(
                    painter = painterResource(contentState.content.image),
                    contentDescription = stringResource(id = contentState.content.title),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = stringResource(id = R.string.on_boarding_screen_title_1),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight()
                )
                Text(
                    text = stringResource(id = R.string.on_boarding_screen_body_1),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight()
                )
                NavigationBottomBar(
                    contentState = contentState,
                    onSkipPressed = onSkipPressed,
                    onNextPressed = { onBoardingState.currentIndex++ },
                    onDonePressed = onDonePressed,
                )
            }
        }
    }
}

@Composable
private fun NavigationBottomBar(
    contentState: OnBoardingContentState,
    onSkipPressed: () -> Unit,
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
            if (contentState.showPrevious && contentState.showDone.not()) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onSkipPressed,
                ) {
                    Text(
                        text = stringResource(id = R.string.on_boarding_screen_skip),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (contentState.showDone) {
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

    val onBoardingState = OnBoardingState(
        contentStates = listOf(
            OnBoardingContentState(
                content = OnBoardingContent.getOnBoardingContent()[0],
                index = 1,
                totalCount = 3,
                showPrevious = true,
                showDone = false
            )
        )
    )

    DrawADayTheme {
        OnBoardingScreen(
            onBoardingState = onBoardingState,
            onSkipPressed = { /*TODO*/ },
            onNextPressed = { /*TODO*/ },
            onDonePressed = { /*TODO*/ },
        )
    }
}
