package com.mrebollob.drawaday.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.ui.theme.CustomGreen1
import com.mrebollob.drawaday.ui.theme.CustomOrange1
import com.mrebollob.drawaday.ui.theme.CustomYellow1

data class OnBoardingContent(
    @StringRes val title: Int,
    @StringRes val body: Int,
    @DrawableRes val image: Int,
    val screenColor: Color
) {

    companion object {
        fun getOnBoardingContent(): List<OnBoardingContent> = listOf(
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_1,
                body = R.string.on_boarding_screen_body_1,
                image = R.drawable.image_tutorial_hello,
                screenColor = CustomOrange1
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_2,
                body = R.string.on_boarding_screen_body_2,
                image = R.drawable.image_tutorial_check_list,
                screenColor = CustomYellow1
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_3,
                body = R.string.on_boarding_screen_body_3,
                image = R.drawable.image_tutorial_drawing_tools,
                screenColor = CustomOrange1
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_4,
                body = R.string.on_boarding_screen_body_4,
                image = R.drawable.image_tutorial_chronometer,
                screenColor = CustomYellow1
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_5,
                body = R.string.on_boarding_screen_body_5,
                image = R.drawable.image_tutorial_lets_go,
                screenColor = CustomGreen1
            )
        )
    }
}

