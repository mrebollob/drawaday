package com.mrebollob.drawaday.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.MainDestinations.DRAWING_ID_KEY
import com.mrebollob.drawaday.ui.drawing.DrawingScreen
import com.mrebollob.drawaday.ui.home.feed.FeedScreen

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val INTERESTS_ROUTE = "interests"
    const val DRAWING_ROUTE = "drawing"
    const val DRAWING_ID_KEY = "drawingId"
}

@Composable
fun DrawADayNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.HOME_ROUTE) {
            FeedScreen(
                navigateToDrawImage = actions.navigateToDrawImage
            )
        }
        composable("${MainDestinations.DRAWING_ROUTE}/{$DRAWING_ID_KEY}") { backStackEntry ->
            DrawingScreen(
                drawingId = backStackEntry.arguments?.getString(DRAWING_ID_KEY),
                onBack = actions.upPress
            )
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val navigateToDrawImage: (DrawImage) -> Unit = { drawImage: DrawImage ->
        navController.navigate("${MainDestinations.DRAWING_ROUTE}/${drawImage.id}")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
