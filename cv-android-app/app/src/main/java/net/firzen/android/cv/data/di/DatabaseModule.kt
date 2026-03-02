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
import net.firzen.android.cv.data.local.CvDatabase
import net.firzen.android.cv.data.local.CvDataSeeder
import net.firzen.android.cv.data.local.dao.*
import timber.log.Timber
import javax.inject.Singleton

private const val DB_NAME = "cv_database"

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
            DB_NAME
        )
            .fallbackToDestructiveMigration(true)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Timber.i("Database created - seeding CV data...")

                    // We need to seed data after the database is created.
                    // Since Room DAO operations are suspend functions, we launch a coroutine.
                    CoroutineScope(Dispatchers.IO).launch {
                        val database = Room.databaseBuilder(
                            appContext,
                            CvDatabase::class.java,
                            DB_NAME
                        ).build()

                        CvDataSeeder.seedAll(database)
                        Timber.i("Database seeding complete!")
                    }
                }
            })
            .build()
    }

    // -- Individual DAO providers ---------------------------------------------
    // Each DAO is provided from the database so Hilt can inject them into
    // Repository and tests.

    @Provides
    fun provideProfileDao(db: CvDatabase): ProfileDao = db.profileDao

    @Provides
    fun provideWorkExperienceDao(db: CvDatabase): WorkExperienceDao = db.workExperienceDao

    @Provides
    fun provideProjectDao(db: CvDatabase): ProjectDao = db.projectDao

    @Provides
    fun provideProjectMilestoneDao(db: CvDatabase): ProjectMilestoneDao = db.projectMilestoneDao

    @Provides
    fun provideEducationDao(db: CvDatabase): EducationDao = db.educationDao

    @Provides
    fun provideProgrammingLanguageDao(db: CvDatabase): ProgrammingLanguageDao = db.programmingLanguageDao

    @Provides
    fun provideTechnologyDao(db: CvDatabase): TechnologyDao = db.technologyDao

    @Provides
    fun provideOtherSkillDao(db: CvDatabase): OtherSkillDao = db.otherSkillDao

    @Provides
    fun provideLanguageDao(db: CvDatabase): LanguageDao = db.languageDao

    @Provides
    fun providePersonalityTraitDao(db: CvDatabase): PersonalityTraitDao = db.personalityTraitDao

    @Provides
    fun provideInterestDao(db: CvDatabase): InterestDao = db.interestDao
}
