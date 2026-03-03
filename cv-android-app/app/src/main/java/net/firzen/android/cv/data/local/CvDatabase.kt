package net.firzen.android.cv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import net.firzen.android.cv.data.local.dao.*
import net.firzen.android.cv.data.local.entities.*

/**
 * Room database definition for the CV app.
 *
 * Lists all entity classes that make up the database tables.
 * Room generates the actual SQLite implementation at compile time via annotation processing (kapt).
 *
 * Version should be incremented when the schema changes (adding/removing/modifying tables or columns).
 * During development, [fallbackToDestructiveMigration] is used in the DI module so the database
 * is simply recreated if the schema changes, rather than requiring migration code.
 */
@Database(
    entities = [
        ProfileEntity::class,
        WorkExperienceEntity::class,
        ProjectEntity::class,
        ProjectMilestoneEntity::class,
        EducationEntity::class,
        ProgrammingLanguageEntity::class,
        TechnologyCategoryEntity::class,
        TechnologyEntity::class,
        OtherSkillCategoryEntity::class,
        OtherSkillEntity::class,
        LanguageEntity::class,
        PersonalityTraitEntity::class,
        InterestEntity::class
    ],
    version = 3,
    exportSchema = false
)

abstract class CvDatabase : RoomDatabase() {
    /** Room generates the implementation of each DAO at compile time. */
    abstract val profileDao: ProfileDao
    abstract val workExperienceDao: WorkExperienceDao
    abstract val projectDao: ProjectDao
    abstract val projectMilestoneDao: ProjectMilestoneDao
    abstract val educationDao: EducationDao
    abstract val programmingLanguageDao: ProgrammingLanguageDao
    abstract val technologyDao: TechnologyDao
    abstract val otherSkillDao: OtherSkillDao
    abstract val languageDao: LanguageDao
    abstract val personalityTraitDao: PersonalityTraitDao
    abstract val interestDao: InterestDao
}
