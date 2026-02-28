package net.firzen.android.cv

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import net.firzen.android.cv.data.local.CvDao
import net.firzen.android.cv.data.local.CvDataSeeder
import net.firzen.android.cv.data.local.CvDatabase
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
 * 1. All seed data is correctly inserted
 * 2. All DAO queries return expected results
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
    private lateinit var dao: CvDao

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
        dao = database.dao

        // Seed the database with CV data
        runBlocking {
            CvDataSeeder.seedAll(dao)
        }
    }

    @After
    fun teardown() {
        database.close()
    }

    // -- Profile --------------------------------------------------------------

    @Test
    fun profileIsSeeded() = runBlocking {
        val profile = dao.getProfile()

        assertNotNull("Profile should not be null", profile)
        Timber.i("=== PROFILE ===")
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
    }

    // -- Work Experience ------------------------------------------------------

    @Test
    fun workExperiencesAreSeeded() = runBlocking {
        val experiences = dao.getAllWorkExperiences()

        Timber.i("=== WORK EXPERIENCE (${experiences.size} entries) ===")
        experiences.forEach { exp ->
            Timber.i("${exp.startDate}-${exp.endDate ?: "present"}: " +
                    "${exp.position} at ${exp.company}")
            Timber.i("  ${exp.description.take(80)}...")
        }

        assertEquals("Should have 6 work experiences", 6, experiences.size)
        assertEquals("First should be Sanctus Media", "Sanctus Media, Ltd.", experiences[0].company)
    }

    // -- Projects -------------------------------------------------------------

    @Test
    fun projectsAreSeeded() = runBlocking {
        val projects = dao.getAllProjects()

        Timber.i("=== PROJECTS (${projects.size} entries) ===")
        projects.forEach { project ->
            Timber.i("${project.name}: ${project.description.take(60)}...")
            Timber.i("  Google Play: ${project.googlePlayUrl ?: "N/A"}")
        }

        assertEquals("Should have 5 projects", 5, projects.size)
        assertEquals("First project should be WattsUp", "WattsUp", projects[0].name)
    }

    @Test
    fun projectMilestonesAreSeeded() = runBlocking {
        val allMilestones = dao.getAllMilestones()
        val projects = dao.getAllProjects()

        Timber.i("=== PROJECT MILESTONES (${allMilestones.size} total) ===")
        projects.forEach { project ->
            val milestones = dao.getMilestonesForProject(project.id)
            Timber.i("--- ${project.name} (${milestones.size} milestones) ---")
            milestones.forEach { ms ->
                Timber.i("  ${ms.year}: ${ms.title} - ${ms.description.take(50)}...")
            }
        }

        assertTrue("Should have milestones", allMilestones.isNotEmpty())
        // WattsUp should have 6 milestones
        val wattsUpMilestones = dao.getMilestonesForProject(1)
        assertEquals("WattsUp should have 6 milestones", 6, wattsUpMilestones.size)
    }

    // -- Education ------------------------------------------------------------

    @Test
    fun educationIsSeeded() = runBlocking {
        val education = dao.getAllEducation()

        Timber.i("=== EDUCATION (${education.size} entries) ===")
        education.forEach { edu ->
            Timber.i("${edu.startYear}-${edu.endYear}: ${edu.degree} at ${edu.institution}")
        }

        assertEquals("Should have 2 education entries", 2, education.size)
    }

    // -- Programming Languages ------------------------------------------------

    @Test
    fun programmingLanguagesAreSeeded() = runBlocking {
        val languages = dao.getAllProgrammingLanguages()

        Timber.i("=== PROGRAMMING LANGUAGES (${languages.size} entries) ===")
        languages.forEach { lang ->
            val bar = "●".repeat(lang.level) + "○".repeat(5 - lang.level)
            Timber.i("$bar ${lang.name} (${lang.level}/5)")
        }

        assertEquals("Should have 6 programming languages", 6, languages.size)
        assertEquals("Kotlin should be 5/5", 5, languages.first { it.name == "Kotlin" }.level)
    }

    // -- Technologies ---------------------------------------------------------

    @Test
    fun technologiesAreSeeded() = runBlocking {
        val categories = dao.getAllTechnologyCategories()
        val allTechs = dao.getAllTechnologies()

        Timber.i("=== ANDROID TECHNOLOGIES (${categories.size} categories, ${allTechs.size} total) ===")
        categories.forEach { cat ->
            val techs = dao.getTechnologiesForCategory(cat.id)
            Timber.i("${cat.categoryName}: ${techs.joinToString(", ") { it.name }}")
        }

        assertEquals("Should have 14 technology categories", 14, categories.size)
        assertTrue("Should have many technologies", allTechs.size > 40)
    }

    // -- Other Skills ---------------------------------------------------------

    @Test
    fun otherSkillsAreSeeded() = runBlocking {
        val categories = dao.getAllOtherSkillCategories()
        val allSkills = dao.getAllOtherSkills()

        Timber.i("=== OTHER SKILLS (${categories.size} categories, ${allSkills.size} total) ===")
        categories.forEach { cat ->
            val skills = dao.getOtherSkillsForCategory(cat.id)
            Timber.i("${cat.categoryName}: ${skills.joinToString(", ") { it.name }}")
        }

        assertEquals("Should have 8 other skill categories", 8, categories.size)
        assertTrue("Should have many other skills", allSkills.size > 25)
    }

    // -- Languages ------------------------------------------------------------

    @Test
    fun languagesAreSeeded() = runBlocking {
        val languages = dao.getAllLanguages()

        Timber.i("=== SPOKEN LANGUAGES (${languages.size} entries) ===")
        languages.forEach { lang ->
            Timber.i("${lang.name}: ${lang.level}${lang.note?.let { " - $it" } ?: ""}")
        }

        assertEquals("Should have 2 languages", 2, languages.size)
    }

    // -- Personality Traits ---------------------------------------------------

    @Test
    fun personalityTraitsAreSeeded() = runBlocking {
        val traits = dao.getAllPersonalityTraits()

        Timber.i("=== PERSONALITY TRAITS (${traits.size} entries) ===")
        traits.forEach { Timber.i("* ${it.trait}") }

        assertEquals("Should have 4 personality traits", 4, traits.size)
    }

    // -- Interests ------------------------------------------------------------

    @Test
    fun interestsAreSeeded() = runBlocking {
        val interests = dao.getAllInterests()

        Timber.i("=== INTERESTS (${interests.size} entries) ===")
        interests.forEach { Timber.i("* ${it.name}") }

        assertEquals("Should have 4 interests", 4, interests.size)
    }

    // -- Full Database Summary ------------------------------------------------

    @Test
    fun fullDatabaseSummary() = runBlocking {
        Timber.i("╔══════════════════════════════════════╗")
        Timber.i("║     CV DATABASE — FULL SUMMARY       ║")
        Timber.i("╠══════════════════════════════════════╣")
        Timber.i("║ Profile:                1 record     ║")
        Timber.i("║ Work Experiences:       ${dao.getAllWorkExperiences().size} records    ║")
        Timber.i("║ Projects:               ${dao.getAllProjects().size} records    ║")
        Timber.i("║ Project Milestones:    ${dao.getAllMilestones().size} records   ║")
        Timber.i("║ Education:              ${dao.getAllEducation().size} records    ║")
        Timber.i("║ Programming Languages:  ${dao.getAllProgrammingLanguages().size} records    ║")
        Timber.i("║ Tech Categories:       ${dao.getAllTechnologyCategories().size} records   ║")
        Timber.i("║ Technologies:          ${dao.getAllTechnologies().size} records   ║")
        Timber.i("║ Skill Categories:       ${dao.getAllOtherSkillCategories().size} records    ║")
        Timber.i("║ Other Skills:          ${dao.getAllOtherSkills().size} records   ║")
        Timber.i("║ Languages:              ${dao.getAllLanguages().size} records    ║")
        Timber.i("║ Personality Traits:     ${dao.getAllPersonalityTraits().size} records    ║")
        Timber.i("║ Interests:              ${dao.getAllInterests().size} records    ║")
        Timber.i("╚══════════════════════════════════════╝")

        // Basic sanity checks
        assertNotNull(dao.getProfile())
        assertTrue(dao.getAllWorkExperiences().isNotEmpty())
        assertTrue(dao.getAllProjects().isNotEmpty())
        assertTrue(dao.getAllMilestones().isNotEmpty())
        assertTrue(dao.getAllEducation().isNotEmpty())
        assertTrue(dao.getAllProgrammingLanguages().isNotEmpty())
        assertTrue(dao.getAllTechnologyCategories().isNotEmpty())
        assertTrue(dao.getAllTechnologies().isNotEmpty())
        assertTrue(dao.getAllOtherSkillCategories().isNotEmpty())
        assertTrue(dao.getAllOtherSkills().isNotEmpty())
        assertTrue(dao.getAllLanguages().isNotEmpty())
        assertTrue(dao.getAllPersonalityTraits().isNotEmpty())
        assertTrue(dao.getAllInterests().isNotEmpty())
    }
}
