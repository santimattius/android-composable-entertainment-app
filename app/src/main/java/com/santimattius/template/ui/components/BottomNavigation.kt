package com.santimattius.template.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.santimattius.template.ui.navigation.NavItem

@Composable
fun AppBottomNavigation(
    bottomNavOptions: List<NavItem>,
    currentRoute: String,
    onNavItemClick: (NavItem) -> Unit
) {
    EntertainmentBottomNavigation {
        bottomNavOptions.forEach { item ->
            val title = stringResource(item.title)
            BottomNavigationItem(
                selected = currentRoute.contains(item.navCommand.feature.route),
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = title
                    )
                },
                label = { Text(text = title) },
                onClick = { onNavItemClick(item) }
            )
        }
    }
}

@Composable
fun EntertainmentBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = BottomNavigationDefaults.Elevation,
    content: @Composable RowScope.() -> Unit,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        content = content
    )
}