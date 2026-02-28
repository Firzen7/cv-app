package net.firzen.android.cv.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Defines all screens accessible via bottom navigation.
 *
 * This is a sealed class, meaning all possible Screen subtypes are defined here
 * and known at compile time. This is useful for exhaustive `when` expressions
 * and ensures we can't accidentally create an unregistered screen.
 *
 * Each screen has:
 *  - [route]: unique string identifier used by the Navigation component to identify the destination
 *  - [title]: human-readable label shown in the bottom navigation bar
 *  - [icon]: Material icon displayed in the bottom navigation bar
 */
sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    // `data object` is Kotlin's way of creating a singleton with a meaningful toString()
    // and equals()/hashCode() — perfect for representing fixed navigation destinations
    data object Profile : Screen("profile", "Profile", Icons.Default.Person)
    data object Experience : Screen("experience", "Experience", Icons.Default.Work)
    data object Skills : Screen("skills", "Skills", Icons.Default.Code)
    data object Projects : Screen("projects", "Projects", Icons.Default.Rocket)

    companion object {
        /** All screens that should appear as tabs in the bottom navigation bar. */
        val bottomNavItems = listOf(Profile, Experience, Skills, Projects)
    }
}
