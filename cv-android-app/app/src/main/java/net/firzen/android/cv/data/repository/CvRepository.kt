package net.firzen.android.cv.data.repository

import net.firzen.android.cv.data.local.dao.*
import net.firzen.android.cv.data.local.entities.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single entry point for all database operations in the app.
 *
 * ViewModels and other app logic should always call Repository methods
 * instead of accessing DAOs directly. This provides a clean abstraction layer
 * between the data source and the rest of the app.
 */
@Singleton
class CvRepository @Inject constructor(
    private val profileDao: ProfileDao,
    private val workExperienceDao: WorkExperienceDao,
    private val projectDao: ProjectDao,
    private val projectMilestoneDao: ProjectMilestoneDao,
    private val educationDao: EducationDao,
    private val programmingLanguageDao: ProgrammingLanguageDao,
    private val technologyDao: TechnologyDao,
    private val otherSkillDao: OtherSkillDao,
    private val languageDao: LanguageDao,
    private val personalityTraitDao: PersonalityTraitDao,
    private val interestDao: InterestDao
) {

    // -- Profile --------------------------------------------------------------

    suspend fun getProfile(): ProfileEntity? = profileDao.get()

    // -- Work Experience ------------------------------------------------------

    suspend fun getAllWorkExperiences(): List<WorkExperienceEntity> =
        workExperienceDao.getAll()

    // -- Projects -------------------------------------------------------------

    suspend fun getAllProjects(): List<ProjectEntity> = projectDao.getAll()

    suspend fun getMilestonesForProject(projectId: Int): List<ProjectMilestoneEntity> =
        projectMilestoneDao.getForProject(projectId)

    suspend fun getAllMilestones(): List<ProjectMilestoneEntity> =
        projectMilestoneDao.getAll()

    // -- Education ------------------------------------------------------------

    suspend fun getAllEducation(): List<EducationEntity> = educationDao.getAll()

    // -- Programming Languages ------------------------------------------------

    suspend fun getAllProgrammingLanguages(): List<ProgrammingLanguageEntity> =
        programmingLanguageDao.getAll()

    // -- Technologies ---------------------------------------------------------

    suspend fun getAllTechnologyCategories(): List<TechnologyCategoryEntity> =
        technologyDao.getAllCategories()

    suspend fun getTechnologiesForCategory(categoryId: Int): List<TechnologyEntity> =
        technologyDao.getForCategory(categoryId)

    suspend fun getAllTechnologies(): List<TechnologyEntity> = technologyDao.getAll()

    // -- Other Skills ---------------------------------------------------------

    suspend fun getAllOtherSkillCategories(): List<OtherSkillCategoryEntity> =
        otherSkillDao.getAllCategories()

    suspend fun getOtherSkillsForCategory(categoryId: Int): List<OtherSkillEntity> =
        otherSkillDao.getForCategory(categoryId)

    suspend fun getAllOtherSkills(): List<OtherSkillEntity> = otherSkillDao.getAll()

    // -- Languages ------------------------------------------------------------

    suspend fun getAllLanguages(): List<LanguageEntity> = languageDao.getAll()

    // -- Personality Traits ---------------------------------------------------

    suspend fun getAllPersonalityTraits(): List<PersonalityTraitEntity> =
        personalityTraitDao.getAll()

    // -- Interests ------------------------------------------------------------

    suspend fun getAllInterests(): List<InterestEntity> = interestDao.getAll()
}
