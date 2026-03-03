package net.firzen.android.cv.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.firzen.android.cv.data.local.OnboardingPreferences
import javax.inject.Singleton

private const val PREFS_NAME = "onboarding_prefs"

/**
 * Hilt module that provides preferences-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun provideOnboardingSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideOnboardingPreferences(
        prefs: SharedPreferences
    ): OnboardingPreferences = OnboardingPreferences(prefs)
}
