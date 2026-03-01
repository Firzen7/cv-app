package net.firzen.android.cv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.firzen.android.cv.presentation.screens.ExperienceScreen
import net.firzen.android.cv.presentation.screens.ProfileScreen
import net.firzen.android.cv.presentation.models.ExperienceViewModel
import net.firzen.android.cv.presentation.models.ProfileViewModel
import net.firzen.android.cv.presentation.models.SkillsViewModel
import net.firzen.android.cv.presentation.screens.ProjectsScreen
import net.firzen.android.cv.presentation.screens.SkillsScreen

/**
 * Navigation host that defines all navigation destinations and handles screen transitions.
 *
 * [NavHost] is the container where screen content is displayed. When the user taps a tab
 * in the bottom navigation bar, the [navController] tells NavHost to switch to the
 * corresponding composable defined here.
 *
 * @param navController shared controller that coordinates navigation between BottomNavBar and NavHost
 * @param modifier receives the inner padding from Scaffold to avoid overlapping with the bottom bar
 */
@Composable
fun CvNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        // Profile is the first screen shown when the app launches
        startDestination = Screen.Profile.route,
        modifier = modifier
    ) {
        // Each `composable()` block registers a route and the screen to display for it.
        // The route strings must match those defined in the Screen sealed class.
        composable(Screen.Profile.route) {
            ProfileScreen(hiltViewModel<ProfileViewModel>())
        }
        composable(Screen.Experience.route) {
            ExperienceScreen(hiltViewModel<ExperienceViewModel>())
        }
        composable(Screen.Skills.route) {
            SkillsScreen(hiltViewModel<SkillsViewModel>())
        }
        composable(Screen.Projects.route) {
            ProjectsScreen()
        }
    }
}

