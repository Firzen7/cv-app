package net.firzen.android.cv.other

import net.firzen.android.cv.R
import java.util.Locale

fun getSupportedLocaleCode() : String {
    return Locale.getDefault().language.let {
        if (it == "cs") "cs" else "en"
    }
}

fun projectIconResId(projectName: String): Int = when {
    projectName.contains("WattsUp", ignoreCase = true) -> R.drawable.ic_project_wattsup
    projectName.contains("Sanctuary First", ignoreCase = true) &&
            !projectName.contains("Alexa", ignoreCase = true) -> R.drawable.ic_project_sanctuary_first
    projectName.contains("CrossReach", ignoreCase = true) -> R.drawable.ic_project_crossreach
    projectName.contains("Sanctus Tools", ignoreCase = true) -> R.drawable.ic_project_sanctus_tools
    projectName.contains("Alexa", ignoreCase = true) -> R.drawable.ic_project_sf_alexa
    else -> R.drawable.ic_project_sanctus_tools
}
