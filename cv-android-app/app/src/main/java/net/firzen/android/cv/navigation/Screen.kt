package net.firzen.android.cv.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector
import net.firzen.android.cv.R

/**
 * Defines all screens accessible via bottom navigation.
 *
 * This is a sealed class, meaning all possible Screen subtypes are defined here
 * and known at compile time. This is useful for exhaustive `when` expressions
 * and ensures we can't accidentally create an unregistered screen.
 *
 * Each screen has:
 *  - [route]: unique string identifier used by the Navigation component to identify the destination
 *  - [titleResId]: string resource ID for the tab label (supports i18n)
 *  - [icon]: Material icon displayed in the bottom navigation bar
 */
sealed class Screen(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector
) {
    // `data object` is Kotlin's way of creating a singleton with a meaningful toString()
    // and equals()/hashCode() -- perfect for representing fixed navigation destinations
    data object Profile : Screen("profile", R.string.tab_profile, Icons.Default.Person)
    data object Experience : Screen("experience", R.string.tab_experience, Icons.Default.Work)
    data object Skills : Screen("skills", R.string.tab_skills, Icons.Default.Code)
    data object Projects : Screen("projects", R.string.tab_projects, Icons.Default.Rocket)

    companion object {
        /** All screens that should appear as tabs in the bottom navigation bar. */
        val bottomNavItems = listOf(Profile, Experience, Skills, Projects)

        /** Route pattern for project detail screen (not in bottom nav). */
        const val PROJECT_DETAIL_ROUTE = "project_detail/{projectId}"

        /** Route for the onboarding screen (not in bottom nav). */
        const val ONBOARDING_ROUTE = "onboarding"

        /** Creates a navigation route for a specific project. */
        fun projectDetailRoute(projectId: Int) = "project_detail/$projectId"
    }
}
