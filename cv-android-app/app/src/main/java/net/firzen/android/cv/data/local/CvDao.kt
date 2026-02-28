package net.firzen.android.cv.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.firzen.android.cv.data.local.entities.*

/**
 * Data Access Object for the CV database.
 *
 * All methods are `suspend` functions, meaning they run inside Kotlin Coroutines
 * on a background thread (IO dispatcher). Room enforces this — calling a DAO method
 * on the main thread would throw an exception.
 */
@Dao
interface CvDao {

    // -- Profile --------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    // -- Work Experience ------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkExperiences(experiences: List<WorkExperienceEntity>)

    /** Returns all work experiences ordered by ordinal (most recent first). */
    @Query("SELECT * FROM work_experience ORDER BY ordinal ASC")
    suspend fun getAllWorkExperiences(): List<WorkExperienceEntity>

    // -- Projects -------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectEntity>)

    @Query("SELECT * FROM projects ORDER BY ordinal ASC")
    suspend fun getAllProjects(): List<ProjectEntity>

    // -- Project Milestones --------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjectMilestones(milestones: List<ProjectMilestoneEntity>)

    /** Returns milestones for a specific project, ordered chronologically. */
    @Query("SELECT * FROM project_milestones WHERE project_id = :projectId ORDER BY ordinal ASC")
    suspend fun getMilestonesForProject(projectId: Int): List<ProjectMilestoneEntity>

    @Query("SELECT * FROM project_milestones ORDER BY project_id, ordinal ASC")
    suspend fun getAllMilestones(): List<ProjectMilestoneEntity>

    // -- Education ------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEducation(education: List<EducationEntity>)

    @Query("SELECT * FROM education ORDER BY start_year DESC")
    suspend fun getAllEducation(): List<EducationEntity>

    // -- Programming Languages ------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgrammingLanguages(languages: List<ProgrammingLanguageEntity>)

    /** Returns programming languages ordered by skill level (highest first). */
    @Query("SELECT * FROM programming_languages ORDER BY level DESC")
    suspend fun getAllProgrammingLanguages(): List<ProgrammingLanguageEntity>

    // -- Technology Categories & Technologies ---------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnologyCategories(categories: List<TechnologyCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnologies(technologies: List<TechnologyEntity>)

    @Query("SELECT * FROM technology_categories ORDER BY id ASC")
    suspend fun getAllTechnologyCategories(): List<TechnologyCategoryEntity>

    @Query("SELECT * FROM technologies WHERE category_id = :categoryId ORDER BY id ASC")
    suspend fun getTechnologiesForCategory(categoryId: Int): List<TechnologyEntity>

    @Query("SELECT * FROM technologies ORDER BY category_id, id ASC")
    suspend fun getAllTechnologies(): List<TechnologyEntity>

    // -- Other Skill Categories & Skills --------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOtherSkillCategories(categories: List<OtherSkillCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOtherSkills(skills: List<OtherSkillEntity>)

    @Query("SELECT * FROM other_skill_categories ORDER BY id ASC")
    suspend fun getAllOtherSkillCategories(): List<OtherSkillCategoryEntity>

    @Query("SELECT * FROM other_skills WHERE category_id = :categoryId ORDER BY id ASC")
    suspend fun getOtherSkillsForCategory(categoryId: Int): List<OtherSkillEntity>

    @Query("SELECT * FROM other_skills ORDER BY category_id, id ASC")
    suspend fun getAllOtherSkills(): List<OtherSkillEntity>

    // -- Languages ------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguages(languages: List<LanguageEntity>)

    @Query("SELECT * FROM languages ORDER BY id ASC")
    suspend fun getAllLanguages(): List<LanguageEntity>

    // -- Personality Traits ---------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalityTraits(traits: List<PersonalityTraitEntity>)

    @Query("SELECT * FROM personality_traits ORDER BY id ASC")
    suspend fun getAllPersonalityTraits(): List<PersonalityTraitEntity>

    // -- Interests ------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterests(interests: List<InterestEntity>)

    @Query("SELECT * FROM interests ORDER BY id ASC")
    suspend fun getAllInterests(): List<InterestEntity>
}
