package com.mrebollob.drawaday.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.ui.home.feed.FeedScreen

fun NavGraphBuilder.addHomeGraph(
    onDrawingClick: (String, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.FEED.route) { from ->
        FeedScreen(onDrawingClick = { id -> onDrawingClick(id, from) }, modifier)
    }
    composable(HomeSections.LEARN.route) { from ->
        FeedScreen(onDrawingClick = { id -> onDrawingClick(id, from) }, modifier)
    }
}

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    FEED(R.string.home_screen_feed_section, Icons.Outlined.Home, "home/feed"),
    LEARN(R.string.home_screen_learn_section, Icons.Outlined.Search, "home/learn")
}