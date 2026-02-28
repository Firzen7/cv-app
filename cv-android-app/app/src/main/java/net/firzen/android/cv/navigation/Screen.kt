package net.firzen.android.cv.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Profile : Screen("profile", "Profile", Icons.Default.Person)
    data object Experience : Screen("experience", "Experience", Icons.Default.Work)
    data object Skills : Screen("skills", "Skills", Icons.Default.Code)
    data object Projects : Screen("projects", "Projects", Icons.Default.Rocket)

    companion object {
        val bottomNavItems = listOf(Profile, Experience, Skills, Projects)
    }
}
