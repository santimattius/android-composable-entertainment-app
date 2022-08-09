package com.santimattius.template.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.santimattius.template.R

enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector,
    @StringRes val title: Int,
) {
    HOME(NavCommand.ContentType(Feature.MOVIES), Icons.Filled.Movie, R.string.movie),
    TV(NavCommand.ContentType(Feature.TV_SHOWS), Icons.Filled.LiveTv, R.string.tv),
}

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArg> = emptyList(),
) {
    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentTypeDetail(feature: Feature) :
        NavCommand(feature, "detail", listOf(NavArg.ItemId)) {
        fun createRoute(itemId: Int) = "${feature.route}/$subRoute/$itemId"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(feature.route)
            .plus(subRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}

enum class NavArg(val key: String, val navType: NavType<*>) {
    ItemId("id", NavType.IntType)
}