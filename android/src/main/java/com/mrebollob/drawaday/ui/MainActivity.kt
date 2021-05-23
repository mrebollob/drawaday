package com.mrebollob.drawaday.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mrebollob.drawaday.ui.theme.DrawADayTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawADayTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute =
                    navBackStackEntry?.destination?.route ?: MainDestinations.HOME_ROUTE

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation {
                            bottomNavigationItems.forEach { bottomNavigationItem ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            bottomNavigationItem.icon,
                                            contentDescription = bottomNavigationItem.iconContentDescription
                                        )
                                    },
                                    selected = currentRoute == bottomNavigationItem.route,
                                    onClick = {
                                        navController.navigate(bottomNavigationItem.route) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) {
                    DrawADayNavGraph(
                        navController = navController,
                    )
                }
            }
        }
    }
}

data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        MainDestinations.HOME_ROUTE,
        Icons.Default.Person,
        "Home"
    ),
    BottomNavigationItem(
        MainDestinations.INTERESTS_ROUTE,
        Icons.Filled.LocationOn,
        "Learn"
    )
)
