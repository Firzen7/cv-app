package net.firzen.android.cv

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import net.firzen.android.cv.data.local.CvDataSeeder
import net.firzen.android.cv.data.local.CvDatabase
import net.firzen.android.cv.data.local.dao.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

/**
 * Instrumented tests for the CV Room database.
 *
 * These tests use an in-memory database (data is not persisted to disk) to verify that:
 * 1. All seed data is correctly inserted (both EN and CS)
 * 2. All DAO queries return expected results when filtered by language
 * 3. Data counts match the CV content
 *
 * Each test also prints the data to Logcat via Timber so you can manually
 * inspect the contents in Android Studio's Logcat window.
 *
 * Run with: ./gradlew :app:connectedDebugAndroidTest
 * or right-click this file in Android Studio -> Run
 */
@RunWith(AndroidJUnit4::class)
class CvDatabaseTest {

    private lateinit var database: CvDatabase
    private lateinit var profileDao: ProfileDao
    private lateinit var workExperienceDao: WorkExperienceDao
    private lateinit var projectDao: ProjectDao
    private lateinit var projectMilestoneDao: ProjectMilestoneDao
    private lateinit var educationDao: EducationDao
    private lateinit var programmingLanguageDao: ProgrammingLanguageDao
    private lateinit var technologyDao: TechnologyDao
    private lateinit var otherSkillDao: OtherSkillDao
    private lateinit var languageDao: LanguageDao
    private lateinit var personalityTraitDao: PersonalityTraitDao
    private lateinit var interestDao: InterestDao

    @Before
    fun setup() {
        // Plant Timber tree for test output
        if (Timber.treeCount == 0) {
            Timber.plant(Timber.DebugTree())
        }

        // Create an in-memory database for testing - it's destroyed when the test finishes
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, CvDatabase::class.java)
            .allowMainThreadQueries() // OK for tests, never do this in production
            .build()

        // Initialize individual DAOs
        profileDao = database.profileDao
        workExperienceDao = database.workExperienceDao
        projectDao = database.projectDao
        projectMilestoneDao = database.projectMilestoneDao
        educationDao = database.educationDao
        programmingLanguageDao = database.programmingLanguageDao
        technologyDao = database.technologyDao
        otherSkillDao = database.otherSkillDao
        languageDao = database.languageDao
        personalityTraitDao = database.personalityTraitDao
        interestDao = database.interestDao

        // Seed the database with CV data (both EN and CS)
        runBlocking {
            CvDataSeeder.seedAll(database)
        }
    }

    @After
    fun teardown() {
        database.close()
    }

    // -- Profile --------------------------------------------------------------

    @Test
    fun profileIsSeeded() = runBlocking {
        val profile = profileDao.get("en").first()

        assertNotNull("Profile should not be null", profile)
        Timber.i("=== PROFILE (EN) ===")
        Timber.i("Name: ${profile!!.name}")
        Timber.i("Title: ${profile.title}")
        Timber.i("Born: ${profile.dateOfBirth}")
        Timber.i("Address: ${profile.address}")
        Timber.i("Phone: ${profile.phone}")
        Timber.i("Email: ${profile.email}")
        Timber.i("LinkedIn: ${profile.linkedInUsername}")
        Timber.i("GitHub: ${profile.githubUsernames}")
        Timber.i("StackOverflow: ${profile.stackOverflowId}")

        assertEquals("Bc. Ondřej Bockschneider", profile.name)
        assertEquals("Android Developer", profile.title)

        // Verify Czech profile also exists
        val profileCs = profileDao.get("cs").first()
        assertNotNull("Czech profile should not be null", profileCs)
    }

    // -- Work Experience ------------------------------------------------------

    @Test
    fun workExperiencesAreSeeded() = runBlocking {
        val experiences = workExperienceDao.getAll("en").first()

        Timber.i("=== WORK EXPERIENCE EN (${experiences.size} entries) ===")
        experiences.forEach { exp ->
            Timber.i("${exp.startDate}-${exp.endDate ?: "present"}: " +
                    "${exp.position} at ${exp.company}")
            Timber.i("  ${exp.description.take(80)}...")
        }

        assertEquals("Should have 6 EN work experiences", 6, experiences.size)
        assertEquals("First should be Sanctus Media", "Sanctus Media, Ltd.", experiences[0].company)

        // Verify Czech data
        val experiencesCs = workExperienceDao.getAll("cs").first()
        assertEquals("Should have 6 CS work experiences", 6, experiencesCs.size)
    }

    // -- Projects -------------------------------------------------------------

    @Test
    fun projectsAreSeeded() = runBlocking {
        val projects = projectDao.getAll("en").first()

        Timber.i("=== PROJECTS EN (${projects.size} entries) ===")
        projects.forEach { project ->
            Timber.i("${project.name}: ${project.description.take(60)}...")
            Timber.i("  Google Play: ${project.googlePlayUrl ?: "N/A"}")
        }

        assertEquals("Should have 5 EN projects", 5, projects.size)
        assertEquals("First project should be WattsUp", "WattsUp", projects[0].name)

        // Verify Czech data
        val projectsCs = projectDao.getAll("cs").first()
        assertEquals("Should have 5 CS projects", 5, projectsCs.size)
    }

    @Test
    fun projectMilestonesAreSeeded() = runBlocking {
        val allMilestones = projectMilestoneDao.getAll("en").first()
        val projects = projectDao.getAll("en").first()

        Timber.i("=== PROJECT MILESTONES EN (${allMilestones.size} total) ===")
        projects.forEach { project ->
            val milestones = projectMilestoneDao.getForProject(project.id, "en").first()
            Timber.i("--- ${project.name} (${milestones.size} milestones) ---")
            milestones.forEach { ms ->
                Timber.i("  ${ms.year}: ${ms.title} - ${ms.description.take(50)}...")
            }
        }

        assertTrue("Should have milestones", allMilestones.isNotEmpty())
        // WattsUp (id=1) should have 6 milestones
        val wattsUpMilestones = projectMilestoneDao.getForProject(1, "en").first()
        assertEquals("WattsUp should have 6 milestones", 6, wattsUpMilestones.size)
    }

    // -- Education ------------------------------------------------------------

    @Test
    fun educationIsSeeded() = runBlocking {
        val education = educationDao.getAll("en").first()

        Timber.i("=== EDUCATION EN (${education.size} entries) ===")
        education.forEach { edu ->
            Timber.i("${edu.startYear}-${edu.endYear}: ${edu.degree} at ${edu.institution}")
        }

        assertEquals("Should have 2 EN education entries", 2, education.size)

        val educationCs = educationDao.getAll("cs").first()
        assertEquals("Should have 2 CS education entries", 2, educationCs.size)
    }

    // -- Programming Languages ------------------------------------------------

    @Test
    fun programmingLanguagesAreSeeded() = runBlocking {
        val languages = programmingLanguageDao.getAll("en").first()

        Timber.i("=== PROGRAMMING LANGUAGES EN (${languages.size} entries) ===")
        languages.forEach { lang ->
            val bar = "●".repeat(lang.level) + "○".repeat(5 - lang.level)
            Timber.i("$bar ${lang.name} (${lang.level}/5)")
        }

        assertEquals("Should have 6 EN programming languages", 6, languages.size)
        assertEquals("Kotlin should be 5/5", 5, languages.first { it.name == "Kotlin" }.level)
    }

    // -- Technologies ---------------------------------------------------------

    @Test
    fun technologiesAreSeeded() = runBlocking {
        val categories = technologyDao.getAllCategories("en").first()
        val allTechs = technologyDao.getAll("en").first()

        Timber.i("=== ANDROID TECHNOLOGIES EN (${categories.size} categories, ${allTechs.size} total) ===")
        categories.forEach { cat ->
            val techs = technologyDao.getForCategory(cat.id, "en").first()
            Timber.i("${cat.categoryName}: ${techs.joinToString(", ") { it.name }}")
        }

        assertEquals("Should have 14 EN technology categories", 14, categories.size)
        assertTrue("Should have many EN technologies", allTechs.size > 40)
    }

    // -- Other Skills ---------------------------------------------------------

    @Test
    fun otherSkillsAreSeeded() = runBlocking {
        val categories = otherSkillDao.getAllCategories("en").first()
        val allSkills = otherSkillDao.getAll("en").first()

        Timber.i("=== OTHER SKILLS EN (${categories.size} categories, ${allSkills.size} total) ===")
        categories.forEach { cat ->
            val skills = otherSkillDao.getForCategory(cat.id, "en").first()
            Timber.i("${cat.categoryName}: ${skills.joinToString(", ") { it.name }}")
        }

        assertEquals("Should have 8 EN other skill categories", 8, categories.size)
        assertTrue("Should have many EN other skills", allSkills.size > 25)
    }

    // -- Languages ------------------------------------------------------------

    @Test
    fun languagesAreSeeded() = runBlocking {
        val languages = languageDao.getAll("en").first()

        Timber.i("=== SPOKEN LANGUAGES EN (${languages.size} entries) ===")
        languages.forEach { lang ->
            Timber.i("${lang.name}: ${lang.level}${lang.note?.let { " - $it" } ?: ""}")
        }

        assertEquals("Should have 2 EN languages", 2, languages.size)

        val languagesCs = languageDao.getAll("cs").first()
        assertEquals("Should have 2 CS languages", 2, languagesCs.size)
    }

    // -- Personality Traits ---------------------------------------------------

    @Test
    fun personalityTraitsAreSeeded() = runBlocking {
        val traits = personalityTraitDao.getAll("en").first()

        Timber.i("=== PERSONALITY TRAITS EN (${traits.size} entries) ===")
        traits.forEach { Timber.i("* ${it.trait}") }

        assertEquals("Should have 4 EN personality traits", 4, traits.size)

        val traitsCs = personalityTraitDao.getAll("cs").first()
        assertEquals("Should have 4 CS personality traits", 4, traitsCs.size)
    }

    // -- Interests ------------------------------------------------------------

    @Test
    fun interestsAreSeeded() = runBlocking {
        val interests = interestDao.getAll("en").first()

        Timber.i("=== INTERESTS EN (${interests.size} entries) ===")
        interests.forEach { Timber.i("* ${it.name}") }

        assertEquals("Should have 4 EN interests", 4, interests.size)

        val interestsCs = interestDao.getAll("cs").first()
        assertEquals("Should have 4 CS interests", 4, interestsCs.size)
    }

    // -- Full Database Summary ------------------------------------------------

    @Test
    fun fullDatabaseSummary() = runBlocking {
        Timber.i("╔══════════════════════════════════════╗")
        Timber.i("║  CV DATABASE — FULL SUMMARY (EN)     ║")
        Timber.i("╠══════════════════════════════════════╣")
        Timber.i("║ Profile:                1 record     ║")
        Timber.i("║ Work Experiences:       ${workExperienceDao.getAll("en").first().size} records    ║")
        Timber.i("║ Projects:               ${projectDao.getAll("en").first().size} records    ║")
        Timber.i("║ Project Milestones:    ${projectMilestoneDao.getAll("en").first().size} records   ║")
        Timber.i("║ Education:              ${educationDao.getAll("en").first().size} records    ║")
        Timber.i("║ Programming Languages:  ${programmingLanguageDao.getAll("en").first().size} records    ║")
        Timber.i("║ Tech Categories:       ${technologyDao.getAllCategories("en").first().size} records   ║")
        Timber.i("║ Technologies:          ${technologyDao.getAll("en").first().size} records   ║")
        Timber.i("║ Skill Categories:       ${otherSkillDao.getAllCategories("en").first().size} records    ║")
        Timber.i("║ Other Skills:          ${otherSkillDao.getAll("en").first().size} records   ║")
        Timber.i("║ Languages:              ${languageDao.getAll("en").first().size} records    ║")
        Timber.i("║ Personality Traits:     ${personalityTraitDao.getAll("en").first().size} records    ║")
        Timber.i("║ Interests:              ${interestDao.getAll("en").first().size} records    ║")
        Timber.i("╚══════════════════════════════════════╝")

        // Basic sanity checks — EN
        assertNotNull(profileDao.get("en").first())
        assertTrue(workExperienceDao.getAll("en").first().isNotEmpty())
        assertTrue(projectDao.getAll("en").first().isNotEmpty())
        assertTrue(projectMilestoneDao.getAll("en").first().isNotEmpty())
        assertTrue(educationDao.getAll("en").first().isNotEmpty())
        assertTrue(programmingLanguageDao.getAll("en").first().isNotEmpty())
        assertTrue(technologyDao.getAllCategories("en").first().isNotEmpty())
        assertTrue(technologyDao.getAll("en").first().isNotEmpty())
        assertTrue(otherSkillDao.getAllCategories("en").first().isNotEmpty())
        assertTrue(otherSkillDao.getAll("en").first().isNotEmpty())
        assertTrue(languageDao.getAll("en").first().isNotEmpty())
        assertTrue(personalityTraitDao.getAll("en").first().isNotEmpty())
        assertTrue(interestDao.getAll("en").first().isNotEmpty())

        // Basic sanity checks — CS
        assertNotNull(profileDao.get("cs").first())
        assertTrue(workExperienceDao.getAll("cs").first().isNotEmpty())
        assertTrue(projectDao.getAll("cs").first().isNotEmpty())
    }
}
