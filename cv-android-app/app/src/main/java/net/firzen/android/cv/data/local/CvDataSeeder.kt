package net.firzen.android.cv.data.local

import net.firzen.android.cv.data.local.entities.*
import timber.log.Timber

/**
 * Seeds the Room database with all CV data on first app launch.
 *
 * All data here is extracted from the LaTeX CV files (cv_9_en.tex / cv_9_cs.tex).
 * This approach demonstrates Room pre-population without needing a server or bundled JSON --
 * the data is simply hardcoded as Kotlin objects.
 */
object CvDataSeeder {

    /**
     * Inserts all CV data into the database via the DAO.
     * Called from [DatabaseModule]'s RoomDatabase.Callback when the database is first created.
     */
    suspend fun seedAll(dao: CvDao) {
        Timber.i("Seeding profile...")
        dao.insertProfile(profile())

        Timber.i("Seeding work experiences...")
        dao.insertWorkExperiences(workExperiences())

        Timber.i("Seeding projects...")
        val projects = projects()
        dao.insertProjects(projects)

        Timber.i("Seeding project milestones...")
        dao.insertProjectMilestones(projectMilestones())

        Timber.i("Seeding education...")
        dao.insertEducation(education())

        Timber.i("Seeding programming languages...")
        dao.insertProgrammingLanguages(programmingLanguages())

        Timber.i("Seeding technology categories and technologies...")
        dao.insertTechnologyCategories(technologyCategories())
        dao.insertTechnologies(technologies())

        Timber.i("Seeding other skill categories and skills...")
        dao.insertOtherSkillCategories(otherSkillCategories())
        dao.insertOtherSkills(otherSkills())

        Timber.i("Seeding languages...")
        dao.insertLanguages(languages())

        Timber.i("Seeding personality traits...")
        dao.insertPersonalityTraits(personalityTraits())

        Timber.i("Seeding interests...")
        dao.insertInterests(interests())
    }

    // ── Profile ──────────────────────────────────────────────────────────────

    private fun profile() = ProfileEntity(
        id = 1,
        name = "Bc. Ondřej Bockschneider",
        title = "Android Developer",
        dateOfBirth = "11.7.1991",
        address = "Komenského 316/13, 679 04 Adamov",
        phone = "+420 737 491 274",
        email = "obockschneider@gmail.com",
        linkedInUsername = "obockschneider",
        githubUsernames = "ondrejsml,Firzen7",
        stackOverflowId = "1735603/firzen"
    )

    // ── Work Experience ──────────────────────────────────────────────────────

    private fun workExperiences() = listOf(
        WorkExperienceEntity(
            id = 1, company = "Sanctus Media, Ltd.", position = "Android Developer",
            startDate = "2/2019", endDate = null,
            description = "Development of 6 Android applications, 2 Android libraries, " +
                    "2 REST API microservices, 1 Alexa skill, and 9 side projects. " +
                    "Occasionally administration of Linux servers, Google Play Console, " +
                    "Firebase, and AWS infrastructure.\n" +
                    "100% remote work since 2020. Daily communication with team exclusively in English.",
            ordinal = 1
        ),
        WorkExperienceEntity(
            id = 2, company = "Redcroft Care Homes, Ltd.", position = "Support Worker",
            startDate = "3/2017", endDate = "3/2018",
            description = "Support and protection of adults suffering from Learning Disability, " +
                    "ASD and Prader-Willi Syndrome.",
            ordinal = 2
        ),
        WorkExperienceEntity(
            id = 3, company = "FCR Tech, spol. s.r.o.", position = "Java Developer",
            startDate = "6/2016", endDate = "1/2017",
            description = "Development of an application for printing yellow pages. " +
                    "Leading team of four Turkish internship students.",
            ordinal = 3
        ),
        WorkExperienceEntity(
            id = 4, company = "Bach systems, s.r.o.", position = "Java Developer",
            startDate = "1/2014", endDate = "6/2016",
            description = "Development of intranet web applications in Vaadin & Java. " +
                    "Worked on larger projects, such as Archive of Czech National Bank.",
            ordinal = 4
        ),
        WorkExperienceEntity(
            id = 5, company = "Exekutorský úřad Havlíčkův Brod", position = "Java Developer",
            startDate = "1/2014", endDate = "6/2014",
            description = "Development and support of the Hybrid Post application.",
            ordinal = 5
        ),
        WorkExperienceEntity(
            id = 6, company = "Exekutorský úřad Havlíčkův Brod", position = "Administrator",
            startDate = "6/2011", endDate = "1/2014",
            description = "Administration of client stations and servers, development of " +
                    "custom software tools according to employer's needs. " +
                    "Created initial version of Hybrid Post and focused on software development since then.",
            ordinal = 6
        )
    )

    // ── Projects ─────────────────────────────────────────────────────────────

    private fun projects() = listOf(
        ProjectEntity(
            id = 1, name = "WattsUp",
            description = "Assistant for EV drivers which allows to find nearest rapid chargers, " +
                    "plan route, select charging stops along the chosen route, and also view the " +
                    "live status of charging stations.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.WattsUp",
            ordinal = 1
        ),
        ProjectEntity(
            id = 2, name = "Sanctuary First",
            description = "Christian spiritual community with modern approach. The app contains " +
                    "videos, podcasts, likes, comments, live streams, music, favorites, " +
                    "Bible reader, etc.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.sanctuaryfirst",
            ordinal = 2
        ),
        ProjectEntity(
            id = 3, name = "CrossReach",
            description = "Originally an electronic version of a physical book annually released " +
                    "by the organization of the same name. Over time, interesting features " +
                    "were added.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.crossreach",
            ordinal = 3
        ),
        ProjectEntity(
            id = 4, name = "Sanctus Tools",
            description = "AAR library created in response to the growing number of Android " +
                    "applications at Sanctus Media. Contains top-level utility functions and " +
                    "Kotlin extensions for quick fade-out animation, detection of overlapping " +
                    "Views, hiding the virtual keyboard, and many more.",
            googlePlayUrl = null,
            ordinal = 4
        ),
        ProjectEntity(
            id = 5, name = "Sanctuary First for Amazon Alexa",
            description = "Alexa Skill that allows user to listen to daily prayers or readings " +
                    "from Bible or other spiritual book. Prayers and readings are downloaded " +
                    "from Sanctuary First API.",
            googlePlayUrl = "https://www.amazon.co.uk/Santus-Media-Ltd-Sanctuary-First/dp/B0811W4GFK",
            ordinal = 5
        )
    )

    // ── Project Milestones ───────────────────────────────────────────────────

    private fun projectMilestones() = listOf(
        // WattsUp milestones (projectId = 1)
        ProjectMilestoneEntity(
            projectId = 1, year = "2019",
            title = "Android version design",
            description = "Design and development of Android version for the existing iOS version " +
                    "and graphical design of dark mode.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            projectId = 1, year = "2020",
            title = "Discovery Mode feature",
            description = "Design and development of the first and most successful paid feature to date.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            projectId = 1, year = "2022",
            title = "Dynamic operator logos",
            description = "Added dynamic logos for charger operators.",
            ordinal = 3
        ),
        ProjectMilestoneEntity(
            projectId = 1, year = "2024",
            title = "Advanced filtering",
            description = "Implementation of advanced filtering, price calculation, " +
                    "and WattsUp Pro paywall dialog.",
            ordinal = 4
        ),
        ProjectMilestoneEntity(
            projectId = 1, year = "2024",
            title = "OCPI support",
            description = "Added support for generic charger operators and close integration " +
                    "with the OCPI standard.",
            ordinal = 5
        ),
        ProjectMilestoneEntity(
            projectId = 1, year = "2025",
            title = "Major redesign",
            description = "The beginning of a major redesign and transition to Mapbox 11.",
            ordinal = 6
        ),
        // Sanctuary First milestones (projectId = 2)
        ProjectMilestoneEntity(
            projectId = 2, year = "2020–2021",
            title = "Initial Android version",
            description = "Development of the initial Android version based on the graphical design.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            projectId = 2, year = "2021",
            title = "User section design",
            description = "Initial graphical design of the user section.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            projectId = 2, year = "2022",
            title = "Comments & likes",
            description = "Database architecture design for comments and likes + graphical design " +
                    "of the comments section.",
            ordinal = 3
        ),
        ProjectMilestoneEntity(
            projectId = 2, year = "2023",
            title = "Pusher integration",
            description = "Dynamic updates of likes, comments, and the start/end of live streams " +
                    "using the Pusher library.",
            ordinal = 4
        ),
        ProjectMilestoneEntity(
            projectId = 2, year = "2025",
            title = "Major refactoring",
            description = "Major refactoring of core architecture; added Bible reader and " +
                    "playlists for guided meditations.",
            ordinal = 5
        ),
        // CrossReach milestones (projectId = 3)
        ProjectMilestoneEntity(
            projectId = 3, year = "2024",
            title = "Google Sign-In & Stripe",
            description = "Implementation of Google sign-in and payments via the native Stripe API.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            projectId = 3, year = "2025",
            title = "Deep links",
            description = "Added support for deep links.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            projectId = 3, year = "2025",
            title = "Push notifications",
            description = "Notifications using Pusher Beams with dynamic Room DB updates on-demand.",
            ordinal = 3
        ),
        // Sanctus Tools (projectId = 4) — library, no timeline milestones
        // Alexa skill milestones (projectId = 5)
        ProjectMilestoneEntity(
            projectId = 5, year = "2019",
            title = "Design & development",
            description = "Complete design and development of the Alexa Skill.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            projectId = 5, year = "2019",
            title = "Amazon Store publication",
            description = "Publication on the Amazon Store.",
            ordinal = 2
        )
    )

    // ── Education ────────────────────────────────────────────────────────────

    private fun education() = listOf(
        EducationEntity(
            id = 1, institution = "Palacký University Olomouc",
            degree = "BSc Applied Computer Science",
            startYear = 2011, endYear = 2015
        ),
        EducationEntity(
            id = 2, institution = "Gymnázium Havlíčkův Brod",
            degree = "Maturita",
            startYear = 2003, endYear = 2011
        )
    )

    // ── Programming Languages ────────────────────────────────────────────────

    private fun programmingLanguages() = listOf(
        ProgrammingLanguageEntity(id = 1, name = "Kotlin", level = 5),
        ProgrammingLanguageEntity(id = 2, name = "Java", level = 5),
        ProgrammingLanguageEntity(id = 3, name = "Bash", level = 4),
        ProgrammingLanguageEntity(id = 4, name = "C/C++", level = 3),
        ProgrammingLanguageEntity(id = 5, name = "Swift", level = 2),
        ProgrammingLanguageEntity(id = 6, name = "Common Lisp", level = 2)
    )

    // ── Technology Categories & Technologies ─────────────────────────────────
    // Category IDs are hardcoded to match the foreign keys in technologies()

    private fun technologyCategories() = listOf(
        TechnologyCategoryEntity(id = 1, categoryName = "Basics"),
        TechnologyCategoryEntity(id = 2, categoryName = "UI"),
        TechnologyCategoryEntity(id = 3, categoryName = "Parallelization"),
        TechnologyCategoryEntity(id = 4, categoryName = "Networking"),
        TechnologyCategoryEntity(id = 5, categoryName = "Data"),
        TechnologyCategoryEntity(id = 6, categoryName = "Security"),
        TechnologyCategoryEntity(id = 7, categoryName = "Architecture"),
        TechnologyCategoryEntity(id = 8, categoryName = "Notifications"),
        TechnologyCategoryEntity(id = 9, categoryName = "Maps"),
        TechnologyCategoryEntity(id = 10, categoryName = "Media"),
        TechnologyCategoryEntity(id = 11, categoryName = "Monetization"),
        TechnologyCategoryEntity(id = 12, categoryName = "Tooling"),
        TechnologyCategoryEntity(id = 13, categoryName = "Testing"),
        TechnologyCategoryEntity(id = 14, categoryName = "Telemetry")
    )

    private fun technologies() = listOf(
        // Basics (1)
        TechnologyEntity(categoryId = 1, name = "Android SDK"),
        TechnologyEntity(categoryId = 1, name = "Activity"),
        TechnologyEntity(categoryId = 1, name = "Services"),
        TechnologyEntity(categoryId = 1, name = "Fragments"),
        TechnologyEntity(categoryId = 1, name = "Composable functions"),
        TechnologyEntity(categoryId = 1, name = "Lifecycle"),
        // UI (2)
        TechnologyEntity(categoryId = 2, name = "Jetpack Compose"),
        TechnologyEntity(categoryId = 2, name = "XML layouts"),
        TechnologyEntity(categoryId = 2, name = "Custom Views (Canvas)"),
        TechnologyEntity(categoryId = 2, name = "Material Design"),
        // Parallelization (3)
        TechnologyEntity(categoryId = 3, name = "Kotlin Coroutines"),
        TechnologyEntity(categoryId = 3, name = "Java Executors"),
        // Networking (4)
        TechnologyEntity(categoryId = 4, name = "OkHttp"),
        TechnologyEntity(categoryId = 4, name = "Retrofit"),
        TechnologyEntity(categoryId = 4, name = "REST APIs"),
        TechnologyEntity(categoryId = 4, name = "JSON"),
        TechnologyEntity(categoryId = 4, name = "GSON"),
        TechnologyEntity(categoryId = 4, name = "Base64"),
        // Data (5)
        TechnologyEntity(categoryId = 5, name = "Room DB"),
        TechnologyEntity(categoryId = 5, name = "SQLite"),
        TechnologyEntity(categoryId = 5, name = "Shared Preferences"),
        // Security (6)
        TechnologyEntity(categoryId = 6, name = "AES"),
        TechnologyEntity(categoryId = 6, name = "R8 / ProGuard"),
        TechnologyEntity(categoryId = 6, name = "Secure token handling"),
        TechnologyEntity(categoryId = 6, name = "JWT"),
        TechnologyEntity(categoryId = 6, name = "Google Sign-In"),
        // Architecture (7)
        TechnologyEntity(categoryId = 7, name = "Lifecycle-aware components"),
        TechnologyEntity(categoryId = 7, name = "ViewModel"),
        TechnologyEntity(categoryId = 7, name = "Navigation Component"),
        TechnologyEntity(categoryId = 7, name = "KMM"),
        // Notifications (8)
        TechnologyEntity(categoryId = 8, name = "Push notifications"),
        TechnologyEntity(categoryId = 8, name = "Deep links"),
        TechnologyEntity(categoryId = 8, name = "Pusher"),
        TechnologyEntity(categoryId = 8, name = "Pusher Beams"),
        TechnologyEntity(categoryId = 8, name = "Pushwoosh"),
        // Maps (9)
        TechnologyEntity(categoryId = 9, name = "Mapbox SDK"),
        // Media (10)
        TechnologyEntity(categoryId = 10, name = "ExoPlayer"),
        // Monetization (11)
        TechnologyEntity(categoryId = 11, name = "Google Play Billing library"),
        TechnologyEntity(categoryId = 11, name = "Stripe SDK"),
        // Tooling (12)
        TechnologyEntity(categoryId = 12, name = "Gradle"),
        TechnologyEntity(categoryId = 12, name = "Google Play Console"),
        TechnologyEntity(categoryId = 12, name = "App Signing"),
        // Testing (13)
        TechnologyEntity(categoryId = 13, name = "Unit tests"),
        TechnologyEntity(categoryId = 13, name = "System tests"),
        TechnologyEntity(categoryId = 13, name = "Organizing of internal beta testing"),
        // Telemetry (14)
        TechnologyEntity(categoryId = 14, name = "Sentry"),
        TechnologyEntity(categoryId = 14, name = "Mixpanel")
    )

    // ── Other Skill Categories & Skills ──────────────────────────────────────

    private fun otherSkillCategories() = listOf(
        OtherSkillCategoryEntity(id = 1, categoryName = "Versioning"),
        OtherSkillCategoryEntity(id = 2, categoryName = "Organization"),
        OtherSkillCategoryEntity(id = 3, categoryName = "CI/CD"),
        OtherSkillCategoryEntity(id = 4, categoryName = "Backend"),
        OtherSkillCategoryEntity(id = 5, categoryName = "Server-side"),
        OtherSkillCategoryEntity(id = 6, categoryName = "Infrastructure"),
        OtherSkillCategoryEntity(id = 7, categoryName = "OS"),
        OtherSkillCategoryEntity(id = 8, categoryName = "Others")
    )

    private fun otherSkills() = listOf(
        // Versioning (1)
        OtherSkillEntity(categoryId = 1, name = "Git"),
        OtherSkillEntity(categoryId = 1, name = "GitLab"),
        OtherSkillEntity(categoryId = 1, name = "GitHub"),
        // Organization (2)
        OtherSkillEntity(categoryId = 2, name = "Slack"),
        OtherSkillEntity(categoryId = 2, name = "Zoom"),
        OtherSkillEntity(categoryId = 2, name = "GitHub Issues"),
        OtherSkillEntity(categoryId = 2, name = "Jira"),
        OtherSkillEntity(categoryId = 2, name = "Clockify"),
        // CI/CD (3)
        OtherSkillEntity(categoryId = 3, name = "Jenkins"),
        OtherSkillEntity(categoryId = 3, name = "Teamcity"),
        // Backend (4)
        OtherSkillEntity(categoryId = 4, name = "Ktor"),
        OtherSkillEntity(categoryId = 4, name = "MariaDB"),
        OtherSkillEntity(categoryId = 4, name = "MySQL"),
        OtherSkillEntity(categoryId = 4, name = "PostgreSQL"),
        OtherSkillEntity(categoryId = 4, name = "Elasticsearch"),
        // Server-side (5)
        OtherSkillEntity(categoryId = 5, name = "Apache"),
        OtherSkillEntity(categoryId = 5, name = "Nginx"),
        OtherSkillEntity(categoryId = 5, name = "Supervisord"),
        OtherSkillEntity(categoryId = 5, name = "TLS/SSL"),
        OtherSkillEntity(categoryId = 5, name = "Wowza Streaming Engine"),
        // Infrastructure (6)
        OtherSkillEntity(categoryId = 6, name = "AWS"),
        OtherSkillEntity(categoryId = 6, name = "Hetzner"),
        OtherSkillEntity(categoryId = 6, name = "Linode"),
        OtherSkillEntity(categoryId = 6, name = "Digital Ocean"),
        // OS (7)
        OtherSkillEntity(categoryId = 7, name = "GNU/Linux"),
        OtherSkillEntity(categoryId = 7, name = "Android"),
        OtherSkillEntity(categoryId = 7, name = "Mac OS"),
        OtherSkillEntity(categoryId = 7, name = "Windows"),
        // Others (8)
        OtherSkillEntity(categoryId = 8, name = "HTML/CSS"),
        OtherSkillEntity(categoryId = 8, name = "SQL"),
        OtherSkillEntity(categoryId = 8, name = "LaTeX")
    )

    // ── Languages ────────────────────────────────────────────────────────────

    private fun languages() = listOf(
        LanguageEntity(id = 1, name = "Czech", level = "Native", note = null),
        LanguageEntity(
            id = 2, name = "English", level = "Intermediate (~C1)",
            note = "Fluent interaction with native speakers"
        )
    )

    // ── Personality Traits ───────────────────────────────────────────────────

    private fun personalityTraits() = listOf(
        PersonalityTraitEntity(id = 1, trait = "Independence"),
        PersonalityTraitEntity(id = 2, trait = "Honesty"),
        PersonalityTraitEntity(id = 3, trait = "Perseverance"),
        PersonalityTraitEntity(id = 4, trait = "Passion to learn new things")
    )

    // ── Interests ────────────────────────────────────────────────────────────

    private fun interests() = listOf(
        InterestEntity(id = 1, name = "Photography"),
        InterestEntity(id = 2, name = "Graphics"),
        InterestEntity(id = 3, name = "Artificial Intelligence"),
        InterestEntity(id = 4, name = "Travel")
    )
}
