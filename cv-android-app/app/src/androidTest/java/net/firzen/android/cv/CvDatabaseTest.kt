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

/**
 * Instrumented tests for the CV Room database.
 *
 * These tests use an in-memory database (data is not persisted to disk) to verify that:
 * 1. All seed data is correctly inserted
 * 2. All DAO queries return expected results
 * 3. Data counts match the CV content
 *
 * Each test also prints the data to Logcat (tag: "CvDbTest") so you can manually
 * inspect the contents in Android Studio's Logcat window.
 *
 * Run with: ./gradlew :app:connectedDebugAndroidTest
 * or right-click this file in Android Studio → Run
 */
@RunWith(AndroidJUnit4::class)
class CvDatabaseTest {

    companion object {
        private const val TAG = "CvDbTest"
    }

    private lateinit var database: CvDatabase
    private lateinit var dao: CvDao

    @Before
    fun setup() {
        // Create an in-memory database for testing — it's destroyed when the test finishes
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

    // ── Profile ──────────────────────────────────────────────────────────────

    @Test
    fun profileIsSeeded() = runBlocking {
        val profile = dao.getProfile()

        assertNotNull("Profile should not be null", profile)
        Log.i(TAG, "=== PROFILE ===")
        Log.i(TAG, "Name: ${profile!!.name}")
        Log.i(TAG, "Title: ${profile.title}")
        Log.i(TAG, "Born: ${profile.dateOfBirth}")
        Log.i(TAG, "Address: ${profile.address}")
        Log.i(TAG, "Phone: ${profile.phone}")
        Log.i(TAG, "Email: ${profile.email}")
        Log.i(TAG, "LinkedIn: ${profile.linkedInUsername}")
        Log.i(TAG, "GitHub: ${profile.githubUsernames}")
        Log.i(TAG, "StackOverflow: ${profile.stackOverflowId}")

        assertEquals("Bc. Ondřej Bockschneider", profile.name)
        assertEquals("Android Developer", profile.title)
    }

    // ── Work Experience ──────────────────────────────────────────────────────

    @Test
    fun workExperiencesAreSeeded() = runBlocking {
        val experiences = dao.getAllWorkExperiences()

        Log.i(TAG, "=== WORK EXPERIENCE (${experiences.size} entries) ===")
        experiences.forEach { exp ->
            Log.i(TAG, "${exp.startDate}–${exp.endDate ?: "present"}: " +
                    "${exp.position} at ${exp.company}")
            Log.i(TAG, "  ${exp.description.take(80)}...")
        }

        assertEquals("Should have 6 work experiences", 6, experiences.size)
        assertEquals("First should be Sanctus Media", "Sanctus Media, Ltd.", experiences[0].company)
    }

    // ── Projects ─────────────────────────────────────────────────────────────

    @Test
    fun projectsAreSeeded() = runBlocking {
        val projects = dao.getAllProjects()

        Log.i(TAG, "=== PROJECTS (${projects.size} entries) ===")
        projects.forEach { project ->
            Log.i(TAG, "${project.name}: ${project.description.take(60)}...")
            Log.i(TAG, "  Google Play: ${project.googlePlayUrl ?: "N/A"}")
        }

        assertEquals("Should have 5 projects", 5, projects.size)
        assertEquals("First project should be WattsUp", "WattsUp", projects[0].name)
    }

    @Test
    fun projectMilestonesAreSeeded() = runBlocking {
        val allMilestones = dao.getAllMilestones()
        val projects = dao.getAllProjects()

        Log.i(TAG, "=== PROJECT MILESTONES (${allMilestones.size} total) ===")
        projects.forEach { project ->
            val milestones = dao.getMilestonesForProject(project.id)
            Log.i(TAG, "--- ${project.name} (${milestones.size} milestones) ---")
            milestones.forEach { ms ->
                Log.i(TAG, "  ${ms.year}: ${ms.title} — ${ms.description.take(50)}...")
            }
        }

        assertTrue("Should have milestones", allMilestones.isNotEmpty())
        // WattsUp should have 6 milestones
        val wattsUpMilestones = dao.getMilestonesForProject(1)
        assertEquals("WattsUp should have 6 milestones", 6, wattsUpMilestones.size)
    }

    // ── Education ────────────────────────────────────────────────────────────

    @Test
    fun educationIsSeeded() = runBlocking {
        val education = dao.getAllEducation()

        Log.i(TAG, "=== EDUCATION (${education.size} entries) ===")
        education.forEach { edu ->
            Log.i(TAG, "${edu.startYear}–${edu.endYear}: ${edu.degree} at ${edu.institution}")
        }

        assertEquals("Should have 2 education entries", 2, education.size)
    }

    // ── Programming Languages ────────────────────────────────────────────────

    @Test
    fun programmingLanguagesAreSeeded() = runBlocking {
        val languages = dao.getAllProgrammingLanguages()

        Log.i(TAG, "=== PROGRAMMING LANGUAGES (${languages.size} entries) ===")
        languages.forEach { lang ->
            val bar = "●".repeat(lang.level) + "○".repeat(5 - lang.level)
            Log.i(TAG, "$bar ${lang.name} (${lang.level}/5)")
        }

        assertEquals("Should have 6 programming languages", 6, languages.size)
        assertEquals("Kotlin should be 5/5", 5, languages.first { it.name == "Kotlin" }.level)
    }

    // ── Technologies ─────────────────────────────────────────────────────────

    @Test
    fun technologiesAreSeeded() = runBlocking {
        val categories = dao.getAllTechnologyCategories()
        val allTechs = dao.getAllTechnologies()

        Log.i(TAG, "=== ANDROID TECHNOLOGIES (${categories.size} categories, ${allTechs.size} total) ===")
        categories.forEach { cat ->
            val techs = dao.getTechnologiesForCategory(cat.id)
            Log.i(TAG, "${cat.categoryName}: ${techs.joinToString(", ") { it.name }}")
        }

        assertEquals("Should have 14 technology categories", 14, categories.size)
        assertTrue("Should have many technologies", allTechs.size > 40)
    }

    // ── Other Skills ─────────────────────────────────────────────────────────

    @Test
    fun otherSkillsAreSeeded() = runBlocking {
        val categories = dao.getAllOtherSkillCategories()
        val allSkills = dao.getAllOtherSkills()

        Log.i(TAG, "=== OTHER SKILLS (${categories.size} categories, ${allSkills.size} total) ===")
        categories.forEach { cat ->
            val skills = dao.getOtherSkillsForCategory(cat.id)
            Log.i(TAG, "${cat.categoryName}: ${skills.joinToString(", ") { it.name }}")
        }

        assertEquals("Should have 8 other skill categories", 8, categories.size)
        assertTrue("Should have many other skills", allSkills.size > 25)
    }

    // ── Languages ────────────────────────────────────────────────────────────

    @Test
    fun languagesAreSeeded() = runBlocking {
        val languages = dao.getAllLanguages()

        Log.i(TAG, "=== SPOKEN LANGUAGES (${languages.size} entries) ===")
        languages.forEach { lang ->
            Log.i(TAG, "${lang.name}: ${lang.level}${lang.note?.let { " — $it" } ?: ""}")
        }

        assertEquals("Should have 2 languages", 2, languages.size)
    }

    // ── Personality Traits ───────────────────────────────────────────────────

    @Test
    fun personalityTraitsAreSeeded() = runBlocking {
        val traits = dao.getAllPersonalityTraits()

        Log.i(TAG, "=== PERSONALITY TRAITS (${traits.size} entries) ===")
        traits.forEach { Log.i(TAG, "• ${it.trait}") }

        assertEquals("Should have 4 personality traits", 4, traits.size)
    }

    // ── Interests ────────────────────────────────────────────────────────────

    @Test
    fun interestsAreSeeded() = runBlocking {
        val interests = dao.getAllInterests()

        Log.i(TAG, "=== INTERESTS (${interests.size} entries) ===")
        interests.forEach { Log.i(TAG, "• ${it.name}") }

        assertEquals("Should have 4 interests", 4, interests.size)
    }

    // ── Full Database Summary ────────────────────────────────────────────────

    @Test
    fun fullDatabaseSummary() = runBlocking {
        Log.i(TAG, "╔══════════════════════════════════════╗")
        Log.i(TAG, "║     CV DATABASE — FULL SUMMARY       ║")
        Log.i(TAG, "╠══════════════════════════════════════╣")
        Log.i(TAG, "║ Profile:                1 record     ║")
        Log.i(TAG, "║ Work Experiences:       ${dao.getAllWorkExperiences().size} records    ║")
        Log.i(TAG, "║ Projects:               ${dao.getAllProjects().size} records    ║")
        Log.i(TAG, "║ Project Milestones:    ${dao.getAllMilestones().size} records   ║")
        Log.i(TAG, "║ Education:              ${dao.getAllEducation().size} records    ║")
        Log.i(TAG, "║ Programming Languages:  ${dao.getAllProgrammingLanguages().size} records    ║")
        Log.i(TAG, "║ Tech Categories:       ${dao.getAllTechnologyCategories().size} records   ║")
        Log.i(TAG, "║ Technologies:          ${dao.getAllTechnologies().size} records   ║")
        Log.i(TAG, "║ Skill Categories:       ${dao.getAllOtherSkillCategories().size} records    ║")
        Log.i(TAG, "║ Other Skills:          ${dao.getAllOtherSkills().size} records   ║")
        Log.i(TAG, "║ Languages:              ${dao.getAllLanguages().size} records    ║")
        Log.i(TAG, "║ Personality Traits:     ${dao.getAllPersonalityTraits().size} records    ║")
        Log.i(TAG, "║ Interests:              ${dao.getAllInterests().size} records    ║")
        Log.i(TAG, "╚══════════════════════════════════════╝")

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
