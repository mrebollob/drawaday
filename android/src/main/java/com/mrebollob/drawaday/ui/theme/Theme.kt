package com.mrebollob.drawaday.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightThemeColors: Colors = lightColors(
    primary = CustomTeal500,
    primaryVariant = CustomTeal700,
    onPrimary = Color.White,
    secondary = CustomYellow500,
    secondaryVariant = CustomYellow700,
    onSecondary = Color.White,
    surface = Surface,
    error = Red800
)

private val DarkThemeColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)

val Colors.snackbarAction: Color
    @Composable
    get() = if (isLight) CustomYellow500 else CustomYellow700

@Composable
fun DrawADayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = DrawADayTypography,
        shapes = DrawADayShapes,
        content = content
    )
}

@Composable
fun ColorTheme(
    color: Color,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color)
    }

    val colors = lightColors(
        primary = color,
        primaryVariant = color,
        secondary = color,
        surface = color
    )

    MaterialTheme(
        colors = colors,
        typography = DrawADayTypography,
        shapes = DrawADayShapes,
        content = content
    )
}
