package net.firzen.android.cv.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.firzen.android.cv.presentation.experience.ExperienceScreen
import net.firzen.android.cv.presentation.profile.ProfileScreen
import net.firzen.android.cv.presentation.projects.ProjectsScreen
import net.firzen.android.cv.presentation.skills.SkillsScreen

@Composable
fun CvNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Profile.route,
        modifier = modifier
    ) {
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.Experience.route) {
            ExperienceScreen()
        }
        composable(Screen.Skills.route) {
            SkillsScreen()
        }
        composable(Screen.Projects.route) {
            ProjectsScreen()
        }
    }
}

