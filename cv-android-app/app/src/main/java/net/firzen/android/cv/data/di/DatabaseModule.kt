package net.firzen.android.cv.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.firzen.android.cv.data.local.CvDao
import net.firzen.android.cv.data.local.CvDatabase
import net.firzen.android.cv.data.local.CvDataSeeder
import timber.log.Timber
import javax.inject.Singleton

/**
 * Hilt DI module that provides database-related dependencies.
 *
 * @InstallIn(SingletonComponent::class) means these dependencies live for the entire
 * lifetime of the application (created once, reused everywhere).
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides the Room database instance as a singleton.
     *
     * The [RoomDatabase.Callback] seeds the database with CV data when it is first created.
     * This means the data is inserted only once (on first install), not on every app launch.
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): CvDatabase {
        return Room.databaseBuilder(
            appContext,
            CvDatabase::class.java,
            "cv_database"
        )
            // During development: if the schema changes, destroy and recreate the DB
            // instead of crashing. In production, you'd write proper migrations instead.
            .fallbackToDestructiveMigration(true)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Timber.i("Database created — seeding CV data...")

                    // We need to seed data after the database is created.
                    // Since Room DAO operations are suspend functions, we launch a coroutine.
                    CoroutineScope(Dispatchers.IO).launch {
                        val database = Room.databaseBuilder(
                            appContext,
                            CvDatabase::class.java,
                            "cv_database"
                        ).build()

                        CvDataSeeder.seedAll(database.dao)
                        Timber.i("Database seeding complete!")
                    }
                }
            })
            .build()
    }

    /**
     * Provides the DAO from the database.
     * Any class that needs the DAO can simply declare it as a constructor parameter
     * and Hilt will inject it automatically.
     */
    @Provides
    fun provideDao(database: CvDatabase): CvDao {
        return database.dao
    }
}
