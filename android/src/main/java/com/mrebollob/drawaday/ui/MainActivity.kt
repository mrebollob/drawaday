package com.mrebollob.drawaday.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mrebollob.drawaday.ui.home.HomeSections
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawADayTheme {
                val mainViewModel = getViewModel<MainViewModel>()
                val isNewUser = mainViewModel.isNewUser.collectAsState()

                val navController = rememberNavController()
                val modifier = Modifier
                val scaffoldState = rememberScaffoldState()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute =
                    navBackStackEntry?.destination?.route ?: MainDestinations.HOME_ROUTE
                val sections = remember { HomeSections.values() }
                val routes = remember { sections.map { it.route } }

                Scaffold(
                    modifier = modifier,
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        if (currentRoute in routes) {
                            BottomNavigation {
                                sections.forEach { section ->
                                    BottomNavigationItem(
                                        icon = {
                                            Icon(
                                                section.icon,
                                                contentDescription = stringResource(id = section.title)
                                            )
                                        },
                                        selected = currentRoute == section.route,
                                        onClick = {
                                            navController.navigate(section.route) {
                                                popUpTo(navController.graph.startDestinationId)
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) {
                    DrawADayNavGraph(
                        navController = navController,
                        startDestination = if (true) {
                            MainDestinations.ONBOARDING_ROUTE
                        } else {
                            MainDestinations.HOME_ROUTE
                        }
                    )
                }
            }
        }
    }
}