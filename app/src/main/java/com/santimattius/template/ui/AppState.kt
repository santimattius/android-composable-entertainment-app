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
import com.santimattius.template.ui.navigation.Feature
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
        val BOTTOM_NAV_ITEMS = listOf(NavItem.MOVIE, NavItem.TV)
        val HOME_ROUTES = listOf(NavItem.MOVIE.navCommand.route, NavItem.TV.navCommand.route)
    }

    val isFullScreen: Boolean
        @Composable get() = !currentRoute.notContainsRoute(Feature.SPLASH)

    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

    val showUpNavigation: Boolean
        @Composable get() = !HOME_ROUTES.contains(currentRoute)

    val showBottomNavigation: Boolean
        @Composable get() = currentRoute.notContainsRoute(Feature.SPLASH)

    val showTopAppBar: Boolean
        @Composable get() = currentRoute.notContainsRoute(Feature.SPLASH)

    fun onUpClick() {
        navController.popBackStack()
    }

    private fun String.notContainsRoute(feature: Feature): Boolean {
        return if (isBlank()) {
            false
        } else {
            !contains(feature.route)
        }
    }

    fun onNavItemClick(navItem: NavItem) {
        navController.navigatePoppingUpToStartDestination(navItem.navCommand.route)
    }
}