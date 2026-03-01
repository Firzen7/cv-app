package net.firzen.android.cv.data.repository

import net.firzen.android.cv.data.local.dao.*
import net.firzen.android.cv.data.local.entities.*
import net.firzen.android.cv.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single entry point for all database operations in the app.
 *
 * ViewModels and other app logic should always call Repository methods
 * instead of accessing DAOs directly. This provides a clean abstraction layer
 * between the data source and the rest of the app.
 *
 * Repository never returns entity classes -- it always maps them into
 * domain model classes before returning.
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

    suspend fun getProfile(): Profile? = profileDao.get()?.toDomain()

    // -- Work Experience ------------------------------------------------------

    suspend fun getAllWorkExperiences(): List<WorkExperience> =
        workExperienceDao.getAll().map { it.toDomain() }

    // -- Projects -------------------------------------------------------------

    suspend fun getAllProjects(): List<Project> =
        projectDao.getAll().map { entity ->
            val milestones = projectMilestoneDao.getForProject(entity.id)
            entity.toDomain(milestones)
        }

    suspend fun getProjectById(projectId: Int): Project? {
        val entity = projectDao.getById(projectId) ?: return null
        val milestones = projectMilestoneDao.getForProject(entity.id)
        return entity.toDomain(milestones)
    }

    // -- Education ------------------------------------------------------------

    suspend fun getAllEducation(): List<Education> =
        educationDao.getAll().map { it.toDomain() }

    // -- Programming Languages ------------------------------------------------

    suspend fun getAllProgrammingLanguages(): List<ProgrammingLanguage> =
        programmingLanguageDao.getAll().map { it.toDomain() }

    // -- Technologies ---------------------------------------------------------

    suspend fun getAllTechnologyCategories(): List<TechnologyCategory> =
        technologyDao.getAllCategories().map { category ->
            val techs = technologyDao.getForCategory(category.id)
            category.toDomain(techs)
        }

    // -- Other Skills ---------------------------------------------------------

    suspend fun getAllOtherSkillCategories(): List<OtherSkillCategory> =
        otherSkillDao.getAllCategories().map { category ->
            val skills = otherSkillDao.getForCategory(category.id)
            category.toDomain(skills)
        }

    // -- Languages ------------------------------------------------------------

    suspend fun getAllLanguages(): List<Language> =
        languageDao.getAll().map { it.toDomain() }

    // -- Personality Traits ---------------------------------------------------

    suspend fun getAllPersonalityTraits(): List<PersonalityTrait> =
        personalityTraitDao.getAll().map { it.toDomain() }

    // -- Interests ------------------------------------------------------------

    suspend fun getAllInterests(): List<Interest> =
        interestDao.getAll().map { it.toDomain() }

    // -- Entity to domain mapping extensions -----------------------------------

    private fun ProfileEntity.toDomain() = Profile(
        id = id, name = name, title = title, dateOfBirth = dateOfBirth,
        address = address, phone = phone, email = email,
        linkedInUsername = linkedInUsername, githubUsernames = githubUsernames,
        stackOverflowId = stackOverflowId
    )

    private fun WorkExperienceEntity.toDomain() = WorkExperience(
        id = id, company = company, position = position,
        startDate = startDate, endDate = endDate,
        description = description, ordinal = ordinal
    )

    private fun ProjectEntity.toDomain(milestones: List<ProjectMilestoneEntity>) = Project(
        id = id, name = name, description = description,
        googlePlayUrl = googlePlayUrl, ordinal = ordinal,
        milestones = milestones.map { it.toDomain() }
    )

    private fun ProjectMilestoneEntity.toDomain() = ProjectMilestone(
        id = id, projectId = projectId, year = year,
        title = title, description = description, ordinal = ordinal
    )

    private fun EducationEntity.toDomain() = Education(
        id = id, institution = institution, degree = degree,
        startYear = startYear, endYear = endYear
    )

    private fun ProgrammingLanguageEntity.toDomain() = ProgrammingLanguage(
        id = id, name = name, level = level
    )

    private fun TechnologyCategoryEntity.toDomain(techs: List<TechnologyEntity>) = TechnologyCategory(
        id = id, name = categoryName,
        technologies = techs.map { it.name }
    )

    private fun OtherSkillCategoryEntity.toDomain(skills: List<OtherSkillEntity>) = OtherSkillCategory(
        id = id, name = categoryName,
        skills = skills.map { it.name }
    )

    private fun LanguageEntity.toDomain() = Language(
        id = id, name = name, level = level, note = note
    )

    private fun PersonalityTraitEntity.toDomain() = PersonalityTrait(
        id = id, trait = trait
    )

    private fun InterestEntity.toDomain() = Interest(
        id = id, name = name
    )
}
