package com.mrebollob.drawaday.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.ui.theme.Green
import com.mrebollob.drawaday.ui.theme.Orange1
import com.mrebollob.drawaday.ui.theme.Orange2

data class OnBoardingContent(
    @StringRes val title: Int,
    @StringRes val body: Int,
    @DrawableRes val image: Int,
    val colors: Colors
) {

    companion object {
        fun getOnBoardingContent(): List<OnBoardingContent> = listOf(
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_1,
                body = R.string.on_boarding_screen_body_1,
                image = R.drawable.placeholder,
                colors = lightColors(
                    primary = Orange1,
                    primaryVariant = Orange1,
                    secondary = Orange1,
                    surface = Orange1
                )
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_2,
                body = R.string.on_boarding_screen_body_2,
                image = R.drawable.placeholder,
                colors = lightColors(
                    primary = Orange2,
                    primaryVariant = Orange2,
                    secondary = Orange2,
                    surface = Orange2
                )
            ),
            OnBoardingContent(
                title = R.string.on_boarding_screen_title_3,
                body = R.string.on_boarding_screen_body_3,
                image = R.drawable.placeholder,
                colors = lightColors(
                    primary = Green,
                    primaryVariant = Green,
                    secondary = Green,
                    surface = Green
                )
            )
        )
    }
}

