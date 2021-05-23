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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mrebollob.drawaday.ui.home.HomeSections
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
                val tabs = remember { HomeSections.values() }

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation {
                            tabs.forEach { tab ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            tab.icon,
                                            contentDescription = stringResource(id = tab.title)
                                        )
                                    },
                                    selected = currentRoute == tab.route,
                                    onClick = {
                                        navController.navigate(tab.route) {
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