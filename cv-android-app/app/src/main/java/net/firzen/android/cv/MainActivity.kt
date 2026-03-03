package net.firzen.android.cv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import net.firzen.android.cv.data.local.OnboardingPreferences
import net.firzen.android.cv.navigation.BottomNavBar
import net.firzen.android.cv.navigation.CvNavHost
import net.firzen.android.cv.navigation.Screen
import net.firzen.android.cv.presentation.screens.OnboardingScreen
import net.firzen.android.cv.ui.theme.CvAndroidAppTheme
import javax.inject.Inject

/**
 * The single Activity in this app (single-activity architecture).
 *
 * @AndroidEntryPoint allows Hilt to inject dependencies into this Activity.
 * All Compose UI is set up in [MainScreen] — the Activity itself only handles
 * the lifecycle setup (edge-to-edge display, theme, content).
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var onboardingPreferences: OnboardingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extends the app's drawing area behind system bars (status bar, navigation bar)
        // for a modern edge-to-edge look. The Scaffold's inner padding then adjusts
        // content to not overlap with system UI.
        enableEdgeToEdge()

        setContent {
            // CvAndroidAppTheme wraps the entire UI in Material 3 theming,
            // providing colors, typography, and shapes to all child composables.
            CvAndroidAppTheme {
                // Persisted flag — reads from SharedPreferences so the onboarding
                // screen is shown only once after install (or data wipe).
                var showOnboarding by remember {
                    mutableStateOf(!onboardingPreferences.isOnboardingCompleted())
                }

                if (showOnboarding) {
                    OnboardingScreen(onDismiss = {
                        onboardingPreferences.setOnboardingCompleted()
                        showOnboarding = false
                    })
                } else {
                    MainScreen()
                }
            }
        }
    }
}

/**
 * Root composable that sets up the app's main structure:
 * - A [Scaffold] providing the overall layout frame
 * - A [BottomNavBar] for tab-based navigation between screens
 * - A [CvNavHost] that swaps screen content based on the selected tab
 *
 * The [rememberNavController] creates and remembers a NavController across
 * recompositions — it is the central piece that coordinates navigation between screens.
 */
@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Only show the bottom bar on the four main tab screens
    val showBottomBar = currentRoute in Screen.bottomNavItems.map { it.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // The bottom bar is placed here in the Scaffold so it stays visible across tab screens
        bottomBar = { if (showBottomBar) BottomNavBar(navController) }
    ) { innerPadding ->
        // innerPadding accounts for the space taken by the bottom bar and system bars,
        // ensuring screen content doesn't render underneath them
        CvNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}