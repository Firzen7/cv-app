package net.firzen.android.cv.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Thin wrapper around [SharedPreferences] that persists whether the user
 * has already seen the onboarding screen.
 *
 * The flag is stored in a dedicated `"onboarding_prefs"` file so it stays
 * isolated from any other app preferences.
 *
 * Clearing app data (or reinstalling) removes the file, causing the
 * onboarding screen to reappear.
 */
@Singleton
class OnboardingPreferences @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun isOnboardingCompleted(): Boolean =
        prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)

    fun setOnboardingCompleted() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply()
    }

    private companion object {
        const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}
