package com.santimattius.template.ui


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.santimattius.template.ui.navigation.NavItem
import com.santimattius.template.ui.navigation.navigatePoppingUpToStartDestination
import kotlinx.coroutines.CoroutineScope

@ExperimentalAnimationApi
@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState = remember(scaffoldState, navController, coroutineScope) {
    AppState(scaffoldState, navController, coroutineScope)
}

class AppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    companion object {
        val BOTTOM_NAV_ITEMS = listOf(NavItem.HOME, NavItem.TV)
    }

    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

    val showUpNavigation: Boolean
        @Composable get() = !NavItem.values().map { it.navCommand.route }.contains(currentRoute)

    val showBottomNavigation: Boolean
        @Composable get() = BOTTOM_NAV_ITEMS.any { currentRoute.contains(it.navCommand.feature.route) }

    fun onUpClick() {
        navController.popBackStack()
    }

    fun onNavItemClick(navItem: NavItem) {
        navController.navigatePoppingUpToStartDestination(navItem.navCommand.route)
    }
}