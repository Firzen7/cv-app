package net.firzen.android.cv.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Custom dark color scheme with cerulean blue accent
private val DarkColorScheme = darkColorScheme(
    primary = CeruleanBlue,
    onPrimary = Color.White,
    secondary = CeruleanBlueLight,
    onSecondary = Color.White,
    tertiary = CeruleanBlueDark,
    background = DarkBackground,
    onBackground = TextOnDark,
    surface = DarkSurface,
    onSurface = TextOnDark,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextOnDarkSecondary
)

// Custom light color scheme with cerulean blue accent
private val LightColorScheme = lightColorScheme(
    primary = CeruleanBlue,
    onPrimary = Color.White,
    secondary = CeruleanBlue,
    onSecondary = Color.White,
    tertiary = CeruleanBlueLight,
    background = LightBackground,
    onBackground = TextOnLight,
    surface = LightSurface,
    onSurface = TextOnLight,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = TextOnLightSecondary
)

@Composable
fun CvAndroidAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    // Using our custom cerulean blue color scheme instead of dynamic colors,
    // so the app always looks consistent regardless of the device wallpaper
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}