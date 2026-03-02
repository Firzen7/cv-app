package net.firzen.android.cv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.firzen.android.cv.data.local.CvDatabase
import timber.log.Timber
import javax.inject.Inject

/**
 * Application-level entry point for the CV app.
 *
 * The @HiltAndroidApp annotation triggers Hilt's code generation at compile time,
 * creating the base classes needed for dependency injection throughout the app.
 * This is a requirement for using Hilt — every Hilt app must have exactly one
 * Application class annotated with @HiltAndroidApp.
 */
@HiltAndroidApp
class CvApp : Application() {

    /**
     * Eagerly inject the database so Room's onCreate callback (which seeds
     * all CV data) fires at app startup — not when the first DAO is accessed.
     * This guarantees data is ready before any screen tries to display it.
     */
    @Inject lateinit var database: CvDatabase

    override fun onCreate() {
        super.onCreate()

        // Plant Timber's DebugTree so that all Timber.i(), Timber.e(), etc. calls
        // output to Logcat with automatic tag generation (class name + line number).
        // In a production app, you'd use a different Tree that sends logs to Crashlytics/Sentry.
        Timber.plant(Timber.DebugTree())
        Timber.i("*** CV App started ***")

        // Force Room to open the database now (triggers onCreate callback + seeding)
        database.openHelper.writableDatabase
        Timber.i("Database initialised eagerly")
    }
}
