package net.firzen.android.cv.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Material 3 bottom navigation bar with tabs for each screen.
 *
 * Observes the [navController]'s current back stack entry to determine which tab
 * is currently active and highlight it accordingly.
 *
 * Navigation behavior:
 * - popUpTo(startDestination): prevents building up a large back stack when switching tabs
 * - launchSingleTop: avoids creating duplicate instances of the same destination
 * - restoreState: remembers scroll position and other state when returning to a tab
 */
@Composable
fun BottomNavBar(navController: NavController) {
    // Observe the current navigation state as a Compose State.
    // `by` delegation means navBackStackEntry automatically updates when navigation changes,
    // triggering recomposition of this composable.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        Screen.bottomNavItems.forEach { screen ->
            NavigationBarItem(
                // Highlights this tab when its route matches the current destination
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination to avoid building up a large
                        // back stack of screens. Without this, pressing Back would go through
                        // every tab the user visited instead of exiting the app.
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination on the stack
                        // (e.g., tapping the same tab twice)
                        launchSingleTop = true
                        // Restore previously saved state when re-selecting a tab
                        // (e.g., scroll position in a list)
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) }
            )
        }
    }
}
