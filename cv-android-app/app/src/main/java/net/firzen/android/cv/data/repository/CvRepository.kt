package net.firzen.android.cv.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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
 *
 * All read methods return [Flow] so that the UI layer is automatically notified
 * when the underlying data changes (e.g. after initial seeding on first launch).
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

    fun getProfile(): Flow<Profile?> =
        profileDao.get().map { it?.toDomain() }

    // -- Work Experience ------------------------------------------------------

    fun getAllWorkExperiences(): Flow<List<WorkExperience>> =
        workExperienceDao.getAll().map { list -> list.map { it.toDomain() } }

    // -- Projects -------------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllProjects(): Flow<List<Project>> =
        projectDao.getAll().flatMapLatest { entities ->
            if (entities.isEmpty()) {
                flowOf(emptyList())
            } else {
                // Combine milestone flows for every project
                val milestoneFlows = entities.map { entity ->
                    projectMilestoneDao.getForProject(entity.id)
                        .map { milestones -> entity.toDomain(milestones) }
                }
                combine(milestoneFlows) { it.toList() }
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getProjectById(projectId: Int): Flow<Project?> =
        projectDao.getById(projectId).flatMapLatest { entity ->
            if (entity == null) {
                flowOf(null)
            } else {
                projectMilestoneDao.getForProject(entity.id)
                    .map { milestones -> entity.toDomain(milestones) }
            }
        }

    // -- Education ------------------------------------------------------------

    fun getAllEducation(): Flow<List<Education>> =
        educationDao.getAll().map { list -> list.map { it.toDomain() } }

    // -- Programming Languages ------------------------------------------------

    fun getAllProgrammingLanguages(): Flow<List<ProgrammingLanguage>> =
        programmingLanguageDao.getAll().map { list -> list.map { it.toDomain() } }

    // -- Technologies ---------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllTechnologyCategories(): Flow<List<TechnologyCategory>> =
        technologyDao.getAllCategories().flatMapLatest { categories ->
            if (categories.isEmpty()) {
                flowOf(emptyList())
            } else {
                val techFlows = categories.map { category ->
                    technologyDao.getForCategory(category.id)
                        .map { techs -> category.toDomain(techs) }
                }
                combine(techFlows) { it.toList() }
            }
        }

    // -- Other Skills ---------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllOtherSkillCategories(): Flow<List<OtherSkillCategory>> =
        otherSkillDao.getAllCategories().flatMapLatest { categories ->
            if (categories.isEmpty()) {
                flowOf(emptyList())
            } else {
                val skillFlows = categories.map { category ->
                    otherSkillDao.getForCategory(category.id)
                        .map { skills -> category.toDomain(skills) }
                }
                combine(skillFlows) { it.toList() }
            }
        }

    // -- Languages ------------------------------------------------------------

    fun getAllLanguages(): Flow<List<Language>> =
        languageDao.getAll().map { list -> list.map { it.toDomain() } }

    // -- Personality Traits ---------------------------------------------------

    fun getAllPersonalityTraits(): Flow<List<PersonalityTrait>> =
        personalityTraitDao.getAll().map { list -> list.map { it.toDomain() } }

    // -- Interests ------------------------------------------------------------

    fun getAllInterests(): Flow<List<Interest>> =
        interestDao.getAll().map { list -> list.map { it.toDomain() } }

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
