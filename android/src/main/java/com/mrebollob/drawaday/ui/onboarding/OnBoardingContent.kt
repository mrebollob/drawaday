package com.mrebollob.drawaday.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.ui.theme.Green
import com.mrebollob.drawaday.ui.theme.Orange1
import com.mrebollob.drawaday.ui.theme.Orange2

data class OnBoardingContent(
    @StringRes val title: Int,
    @StringRes val body: Int,
    @DrawableRes val image: Int,
    val color: Color
) {

    companion object {
        fun getOnBoardingContent(): List<OnBoardingContent> = listOf(
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_1,
                body = R.string.on_boarding_screen_body_1,
                image = R.drawable.image_tutorial_hello,
                color = Orange1
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_2,
                body = R.string.on_boarding_screen_body_2,
                image = R.drawable.image_tutorial_hello,
                color = Orange2
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_3,
                body = R.string.on_boarding_screen_body_3,
                image = R.drawable.image_tutorial_hello,
                color = Green
            )
        )
    }
}

