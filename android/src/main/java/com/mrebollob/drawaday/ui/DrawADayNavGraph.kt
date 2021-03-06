package com.mrebollob.drawaday.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mrebollob.drawaday.ui.MainDestinations.DRAWING_ID_KEY
import com.mrebollob.drawaday.ui.drawing.DrawingScreen
import com.mrebollob.drawaday.ui.home.HomeSections
import com.mrebollob.drawaday.ui.home.addHomeGraph
import com.mrebollob.drawaday.ui.onboarding.OnBoardingContent
import com.mrebollob.drawaday.ui.onboarding.OnBoardingScreen
import com.mrebollob.drawaday.ui.profile.ProfileScreen
import org.koin.androidx.compose.getViewModel

object MainDestinations {
    const val ONBOARDING_ROUTE = "onboarding"
    const val HOME_ROUTE = "home"
    const val PROFILE_ROUTE = "profile"
    const val DRAWING_ROUTE = "drawing"
    const val DRAWING_ID_KEY = "drawingId"
}

@Composable
fun DrawADayNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    val mainViewModel = getViewModel<MainViewModel>()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = MainDestinations.HOME_ROUTE,
            startDestination = HomeSections.FEED.route
        ) {
            addHomeGraph(
                onProfileClick = { from: NavBackStackEntry ->
                    if (from.lifecycleIsResumed()) {
                        navController.navigate(MainDestinations.PROFILE_ROUTE)
                    }
                },
                onDrawingClick = { drawingId: String, from: NavBackStackEntry ->
                    if (from.lifecycleIsResumed()) {
                        navController.navigate("${MainDestinations.DRAWING_ROUTE}/$drawingId")
                    }
                },
                modifier = modifier
            )
        }
        composable(MainDestinations.ONBOARDING_ROUTE) {
            OnBoardingScreen(
                onBoardingContent = OnBoardingContent.getOnBoardingContent(),
                onDonePressed = {
                    mainViewModel.setStartDate()
                    navController.navigate(MainDestinations.HOME_ROUTE) {
                        popUpTo(MainDestinations.ONBOARDING_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(MainDestinations.PROFILE_ROUTE) {
            ProfileScreen(
                onBack = { navController.navigateUp() }
            )
        }
        composable(
            "${MainDestinations.DRAWING_ROUTE}/{$DRAWING_ID_KEY}",
            arguments = listOf(navArgument(DRAWING_ID_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val drawingId = arguments.getString(DRAWING_ID_KEY)
            DrawingScreen(
                drawingId = drawingId!!,
                onBack = { navController.navigateUp() }
            )
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
