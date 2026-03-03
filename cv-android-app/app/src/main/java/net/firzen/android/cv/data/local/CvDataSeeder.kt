package net.firzen.android.cv.data.local

import net.firzen.android.cv.data.local.entities.*
import timber.log.Timber

/**
 * Seeds the Room database with all CV data on first app launch.
 *
 * All data here is extracted from the LaTeX CV files (cv_9_en.tex / cv_9_cs.tex).
 * Both English ("en") and Czech ("cs") versions are seeded so the app can
 * display localised content based on the device language.
 */
object CvDataSeeder {

    /**
     * Inserts all CV data (both languages) into the database via individual DAOs.
     * Called from [DatabaseModule]'s RoomDatabase.Callback when the database is first created.
     */
    suspend fun seedAll(database: CvDatabase) {
        Timber.i("Seeding profile...")
        database.profileDao.insert(profileEn())
        database.profileDao.insert(profileCs())

        Timber.i("Seeding work experiences...")
        database.workExperienceDao.insertAll(workExperiencesEn() + workExperiencesCs())

        Timber.i("Seeding projects...")
        database.projectDao.insertAll(projectsEn() + projectsCs())

        Timber.i("Seeding project milestones...")
        database.projectMilestoneDao.insertAll(projectMilestonesEn() + projectMilestonesCs())

        Timber.i("Seeding education...")
        database.educationDao.insertAll(educationEn() + educationCs())

        Timber.i("Seeding programming languages...")
        database.programmingLanguageDao.insertAll(programmingLanguagesEn() + programmingLanguagesCs())

        Timber.i("Seeding technology categories and technologies...")
        database.technologyDao.insertCategories(technologyCategoriesEn() + technologyCategoriesCs())
        database.technologyDao.insertTechnologies(technologiesEn() + technologiesCs())

        Timber.i("Seeding other skill categories and skills...")
        database.otherSkillDao.insertCategories(otherSkillCategoriesEn() + otherSkillCategoriesCs())
        database.otherSkillDao.insertSkills(otherSkillsEn() + otherSkillsCs())

        Timber.i("Seeding languages...")
        database.languageDao.insertAll(languagesEn() + languagesCs())

        Timber.i("Seeding personality traits...")
        database.personalityTraitDao.insertAll(personalityTraitsEn() + personalityTraitsCs())

        Timber.i("Seeding interests...")
        database.interestDao.insertAll(interestsEn() + interestsCs())
    }

    // =========================================================================
    // Profile
    // =========================================================================

    private fun profileEn() = ProfileEntity(
        id = 1, language = "en",
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

    private fun profileCs() = ProfileEntity(
        id = 1, language = "cs",
        name = "Bc. Ondřej Bockschneider",
        title = "Android Developer",
        dateOfBirth = "11.7.1991",
        address = "Komenského 316/13, 679 04 Adamov (20 minut od Brna)",
        phone = "+420 737 491 274",
        email = "obockschneider@gmail.com",
        linkedInUsername = "obockschneider",
        githubUsernames = "ondrejsml,Firzen7",
        stackOverflowId = "1735603/firzen"
    )

    // =========================================================================
    // Work Experience
    // =========================================================================

    private fun workExperiencesEn() = listOf(
        WorkExperienceEntity(
            language = "en", company = "Sanctus Media, Ltd.", position = "Android Developer",
            startDate = "2/2019", endDate = null,
            description = "Development of 6 Android applications, 2 Android libraries, " +
                    "2 REST API microservices, 1 Alexa skill, and 9 side projects. " +
                    "Occasionally administration of Linux servers, Google Play Console, " +
                    "Firebase, and AWS infrastructure.\n" +
                    "100% remote work since 2020. Daily communication with team exclusively in English.",
            ordinal = 1
        ),
        WorkExperienceEntity(
            language = "en", company = "Redcroft Care Homes, Ltd.", position = "Support Worker",
            startDate = "3/2017", endDate = "3/2018",
            description = "Support and protection of adults suffering from Learning Disability, " +
                    "ASD and Prader-Willi Syndrome.",
            ordinal = 2
        ),
        WorkExperienceEntity(
            language = "en", company = "FCR Tech, spol. s.r.o.", position = "Java Developer",
            startDate = "6/2016", endDate = "1/2017",
            description = "Development of an application for printing yellow pages. " +
                    "Leading team of four Turkish internship students.",
            ordinal = 3
        ),
        WorkExperienceEntity(
            language = "en", company = "Bach systems, s.r.o.", position = "Java Developer",
            startDate = "1/2014", endDate = "6/2016",
            description = "Development of intranet web applications in Vaadin & Java. " +
                    "Worked on larger projects, such as Archive of Czech National Bank.",
            ordinal = 4
        ),
        WorkExperienceEntity(
            language = "en", company = "Exekutorský úřad Havlíčkův Brod", position = "Java Developer",
            startDate = "1/2014", endDate = "6/2014",
            description = "Development and support of the Hybrid Post application.",
            ordinal = 5
        ),
        WorkExperienceEntity(
            language = "en", company = "Exekutorský úřad Havlíčkův Brod", position = "Administrator",
            startDate = "6/2011", endDate = "1/2014",
            description = "Administration of client stations and servers, development of " +
                    "custom software tools according to employer's needs. " +
                    "Created initial version of Hybrid Post and focused on software development since then.",
            ordinal = 6
        )
    )

    private fun workExperiencesCs() = listOf(
        WorkExperienceEntity(
            language = "cs", company = "Sanctus Media, Ltd.", position = "Android Developer",
            startDate = "2/2019", endDate = null,
            description = "Vývoj celkem 6 Android aplikací, 2 Android knihoven, " +
                    "2 REST API microservices, 1 schopnosti pro Alexu a 9 pomocných projektů. " +
                    "Okrajověji také správa Linuxových serverů, Google Play Console, " +
                    "Firebase nebo AWS infrastruktury.\n" +
                    "100% remote práce od roku 2020. Denní komunikace s týmem výhradně v angličtině.",
            ordinal = 1
        ),
        WorkExperienceEntity(
            language = "cs", company = "Redcroft Care Homes, Ltd.", position = "Support Worker",
            startDate = "3/2017", endDate = "3/2018",
            description = "Práce v chráněném bydlení s klienty trpícími poruchou učení, " +
                    "ASD a Prader-Willi syndromem.",
            ordinal = 2
        ),
        WorkExperienceEntity(
            language = "cs", company = "FCR Tech, spol. s.r.o.", position = "Java Programátor",
            startDate = "6/2016", endDate = "1/2017",
            description = "Vývoj aplikace pro tisk zlatých stránek. " +
                    "Vedení čtyřčlenného týmu studentů z Turecka.",
            ordinal = 3
        ),
        WorkExperienceEntity(
            language = "cs", company = "Bach systems, s.r.o.", position = "Java Programátor",
            startDate = "1/2014", endDate = "6/2016",
            description = "Vývoj webových aplikací s pomocí technologie Vaadin v Javě. " +
                    "Pracoval jsem zde zejména na projektu Dabs Web vyvíjeného pro ČNB.",
            ordinal = 4
        ),
        WorkExperienceEntity(
            language = "cs", company = "Exekutorský úřad Havlíčkův Brod", position = "Programátor",
            startDate = "1/2014", endDate = "6/2014",
            description = "Vývoj a podpora aplikace Hybrid Post.",
            ordinal = 5
        ),
        WorkExperienceEntity(
            language = "cs", company = "Exekutorský úřad Havlíčkův Brod", position = "Správce sítě",
            startDate = "6/2011", endDate = "1/2014",
            description = "Administrace klientských a serverových stanic, vývoj software na míru " +
                    "dle potřeb zaměstnavatele. Zde jsem vytvořil program Hybrid Post " +
                    "a uvědomil si, že se chci stát programátorem.",
            ordinal = 6
        )
    )

    // =========================================================================
    // Projects
    // =========================================================================

    private fun projectsEn() = listOf(
        ProjectEntity(
            id = 1, language = "en", name = "WattsUp",
            description = "Assistant for EV drivers which allows to find nearest rapid chargers, " +
                    "plan route, select charging stops along the chosen route, and also view the " +
                    "live status of charging stations.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.WattsUp",
            ordinal = 1
        ),
        ProjectEntity(
            id = 2, language = "en", name = "Sanctuary First",
            description = "Christian spiritual community with modern approach. The app contains " +
                    "videos, podcasts, likes, comments, live streams, music, favorites, " +
                    "Bible reader, etc.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.sanctuaryfirst",
            ordinal = 2
        ),
        ProjectEntity(
            id = 3, language = "en", name = "CrossReach",
            description = "Originally an electronic version of a physical book annually released " +
                    "by the organization of the same name. Over time, interesting features " +
                    "were added.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.crossreach",
            ordinal = 3
        ),
        ProjectEntity(
            id = 4, language = "en", name = "Sanctus Tools",
            description = "AAR library created in response to the growing number of Android " +
                    "applications at Sanctus Media. Contains top-level utility functions and " +
                    "Kotlin extensions for quick fade-out animation, detection of overlapping " +
                    "Views, hiding the virtual keyboard, and many more.",
            googlePlayUrl = null,
            ordinal = 4
        ),
        ProjectEntity(
            id = 5, language = "en", name = "Sanctuary First for Amazon Alexa",
            description = "Alexa Skill that allows user to listen to daily prayers or readings " +
                    "from Bible or other spiritual book. Prayers and readings are downloaded " +
                    "from Sanctuary First API.",
            googlePlayUrl = "https://www.amazon.co.uk/Santus-Media-Ltd-Sanctuary-First/dp/B0811W4GFK",
            ordinal = 5
        )
    )

    private fun projectsCs() = listOf(
        ProjectEntity(
            id = 6, language = "cs", name = "WattsUp",
            description = "Asistent pro řidiče elektromobilů, který umožňuje nalézt nejbližší " +
                    "rychlé nabíječky, naplánovat trasu, zvolit zastávky pro nabití podél " +
                    "zvolené trasy a zobrazit status nabíječek v reálném čase.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.WattsUp",
            ordinal = 1
        ),
        ProjectEntity(
            id = 7, language = "cs", name = "Sanctuary First",
            description = "Duchovní, křesťanská komunita ve velmi moderním pojetí. Aplikace " +
                    "obsahuje videa, podcasty, lajky, komentáře, živé streamy, hudbu, " +
                    "oblíbené položky, čtečku Bible, atd.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.sanctuaryfirst",
            ordinal = 2
        ),
        ProjectEntity(
            id = 8, language = "cs", name = "CrossReach",
            description = "Původně to byla pouze elektronická verze knihy každoročně vydávané " +
                    "stejnojmennou organizací. Časem byly ale přidány zajímavé funkce.",
            googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.crossreach",
            ordinal = 3
        ),
        ProjectEntity(
            id = 9, language = "cs", name = "Sanctus Tools",
            description = "AAR knihovna vytvořená v reakci na rostoucí počet Android aplikací " +
                    "v Sanctus Media, a zároveň na častou redundanci boilerplate kódu. " +
                    "Obsahuje utility top-level funkce, ale také spoustu Kotlin extensions.",
            googlePlayUrl = null,
            ordinal = 4
        ),
        ProjectEntity(
            id = 10, language = "cs", name = "Sanctuary First pro Amazon Alexa",
            description = "Schopnost (aplikace), která uživateli přečte denní modlitbu či " +
                    "výňatek z Bible, popř. jiné duchovní knihy. Modlitby jsou stahovány " +
                    "ze Sanctuary First API.",
            googlePlayUrl = "https://www.amazon.co.uk/Santus-Media-Ltd-Sanctuary-First/dp/B0811W4GFK",
            ordinal = 5
        )
    )

    // =========================================================================
    // Project Milestones
    // =========================================================================

    private fun projectMilestonesEn() = listOf(
        // WattsUp milestones (projectId = 1)
        ProjectMilestoneEntity(
            language = "en", projectId = 1, year = "2019",
            title = "Android version design",
            description = "Design and development of Android version for the existing iOS version " +
                    "and graphical design of dark mode.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 1, year = "2020",
            title = "Discovery Mode feature",
            description = "Design and development of the first and most successful paid feature to date.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 1, year = "2022",
            title = "Dynamic operator logos",
            description = "Added dynamic logos for charger operators.",
            ordinal = 3
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 1, year = "2024",
            title = "Advanced filtering",
            description = "Implementation of advanced filtering, price calculation, " +
                    "and WattsUp Pro paywall dialog.",
            ordinal = 4
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 1, year = "2024",
            title = "OCPI support",
            description = "Added support for generic charger operators and close integration " +
                    "with the OCPI standard.",
            ordinal = 5
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 1, year = "2025",
            title = "Major redesign",
            description = "The beginning of a major redesign and transition to Mapbox 11.",
            ordinal = 6
        ),
        // Sanctuary First milestones (projectId = 2)
        ProjectMilestoneEntity(
            language = "en", projectId = 2, year = "2020-2021",
            title = "Initial Android version",
            description = "Development of the initial Android version based on the graphical design.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 2, year = "2021",
            title = "User section design",
            description = "Initial graphical design of the user section.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 2, year = "2022",
            title = "Comments & likes",
            description = "Database architecture design for comments and likes + graphical design " +
                    "of the comments section.",
            ordinal = 3
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 2, year = "2023",
            title = "Pusher integration",
            description = "Dynamic updates of likes, comments, and the start/end of live streams " +
                    "using the Pusher library.",
            ordinal = 4
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 2, year = "2025",
            title = "Major refactoring",
            description = "Major refactoring of core architecture; added Bible reader and " +
                    "playlists for guided meditations.",
            ordinal = 5
        ),
        // CrossReach milestones (projectId = 3)
        ProjectMilestoneEntity(
            language = "en", projectId = 3, year = "2024",
            title = "Google Sign-In & Stripe",
            description = "Implementation of Google sign-in and payments via the native Stripe API.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 3, year = "2025",
            title = "Deep links",
            description = "Added support for deep links.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 3, year = "2025",
            title = "Push notifications",
            description = "Notifications using Pusher Beams with dynamic Room DB updates on-demand.",
            ordinal = 3
        ),
        // Alexa skill milestones (projectId = 5)
        ProjectMilestoneEntity(
            language = "en", projectId = 5, year = "2019",
            title = "Design & development",
            description = "Complete design and development of the Alexa Skill.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "en", projectId = 5, year = "2019",
            title = "Amazon Store publication",
            description = "Publication on the Amazon Store.",
            ordinal = 2
        )
    )

    private fun projectMilestonesCs() = listOf(
        // WattsUp milestones (projectId = 6 for CS)
        ProjectMilestoneEntity(
            language = "cs", projectId = 6, year = "2019",
            title = "Návrh Android verze",
            description = "Návrh a vývoj Android verze pro existující iOS verzi " +
                    "a grafický návrh tmavého režimu.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 6, year = "2020",
            title = "Discovery Mode",
            description = "Návrh a vývoj první a dosud nejúspěšnější placené funkce.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 6, year = "2022",
            title = "Dynamická loga operátorů",
            description = "Přidána dynamická loga operátorů nabíječek.",
            ordinal = 3
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 6, year = "2024",
            title = "Pokročilé filtrování",
            description = "Implementace pokročilého filtrování, výpočtu cen " +
                    "a paywall dialogu WattsUp Pro.",
            ordinal = 4
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 6, year = "2024",
            title = "Podpora OCPI",
            description = "Přidána podpora obecného operátora nabíječek " +
                    "a úzká provázanost se standardem OCPI.",
            ordinal = 5
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 6, year = "2025",
            title = "Velký redesign",
            description = "Počátek velkého redesignu a přechod na Mapbox 11.",
            ordinal = 6
        ),
        // Sanctuary First milestones (projectId = 7 for CS)
        ProjectMilestoneEntity(
            language = "cs", projectId = 7, year = "2020-2021",
            title = "Prvotní Android verze",
            description = "Vývoj prvotní Android verze dle grafického návrhu.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 7, year = "2021",
            title = "Návrh uživatelské sekce",
            description = "Prvotní grafický návrh uživatelské sekce.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 7, year = "2022",
            title = "Komentáře a lajky",
            description = "Návrh databázové architektury komentářů s reakcemi a lajky " +
                    "+ grafický návrh sekce komentářů.",
            ordinal = 3
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 7, year = "2023",
            title = "Integrace Pusher",
            description = "Dynamické updaty lajků, komentářů a začátku / konce živých streamů " +
                    "s pomocí knihovny Pusher.",
            ordinal = 4
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 7, year = "2025",
            title = "Velký refactoring",
            description = "Velký refactoring základní architektury; přidána čtečka Bible " +
                    "a playlisty pro vedené meditace.",
            ordinal = 5
        ),
        // CrossReach milestones (projectId = 8 for CS)
        ProjectMilestoneEntity(
            language = "cs", projectId = 8, year = "2024",
            title = "Google Sign-In a Stripe",
            description = "Přihlášení pomocí Google sign-in a platby přes nativní Stripe API.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 8, year = "2025",
            title = "Deep linky",
            description = "Přidána podpora Deep linků.",
            ordinal = 2
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 8, year = "2025",
            title = "Notifikace",
            description = "Notifikace pomocí Pusher Beams.",
            ordinal = 3
        ),
        // Alexa skill milestones (projectId = 10 for CS)
        ProjectMilestoneEntity(
            language = "cs", projectId = 10, year = "2019",
            title = "Návrh a vývoj",
            description = "Kompletní návrh a vývoj aplikace.",
            ordinal = 1
        ),
        ProjectMilestoneEntity(
            language = "cs", projectId = 10, year = "2019",
            title = "Zveřejnění na Amazonu",
            description = "Zveřejnění v obchodě Amazon.",
            ordinal = 2
        )
    )

    // =========================================================================
    // Education
    // =========================================================================

    private fun educationEn() = listOf(
        EducationEntity(language = "en", institution = "Palacký University Olomouc",
            degree = "BSc Applied Computer Science", startYear = 2011, endYear = 2015),
        EducationEntity(language = "en", institution = "Gymnázium Havlíčkův Brod",
            degree = "Maturita", startYear = 2003, endYear = 2011)
    )

    private fun educationCs() = listOf(
        EducationEntity(language = "cs", institution = "Univerzita Palackého Olomouc",
            degree = "Bc., Aplikovaná informatika", startYear = 2011, endYear = 2015),
        EducationEntity(language = "cs", institution = "Gymnázium Havlíčkův Brod",
            degree = "Maturita", startYear = 2003, endYear = 2011)
    )

    // =========================================================================
    // Programming Languages
    // =========================================================================

    private fun programmingLanguagesEn() = listOf(
        ProgrammingLanguageEntity(language = "en", name = "Kotlin", level = 5),
        ProgrammingLanguageEntity(language = "en", name = "Java", level = 5),
        ProgrammingLanguageEntity(language = "en", name = "Bash", level = 4),
        ProgrammingLanguageEntity(language = "en", name = "C/C++", level = 3),
        ProgrammingLanguageEntity(language = "en", name = "Swift", level = 2),
        ProgrammingLanguageEntity(language = "en", name = "Common Lisp", level = 2)
    )

    private fun programmingLanguagesCs() = listOf(
        ProgrammingLanguageEntity(language = "cs", name = "Kotlin", level = 5),
        ProgrammingLanguageEntity(language = "cs", name = "Java", level = 5),
        ProgrammingLanguageEntity(language = "cs", name = "Bash", level = 4),
        ProgrammingLanguageEntity(language = "cs", name = "C/C++", level = 3),
        ProgrammingLanguageEntity(language = "cs", name = "Swift", level = 2),
        ProgrammingLanguageEntity(language = "cs", name = "Common Lisp", level = 2)
    )

    // =========================================================================
    // Technology Categories & Technologies
    // =========================================================================
    // EN categories get IDs 1–14, CS categories get IDs 15–28.
    // Technologies reference their category by these explicit IDs.

    private fun technologyCategoriesEn() = listOf(
        TechnologyCategoryEntity(id = 1, language = "en", categoryName = "Basics"),
        TechnologyCategoryEntity(id = 2, language = "en", categoryName = "UI"),
        TechnologyCategoryEntity(id = 3, language = "en", categoryName = "Parallelization"),
        TechnologyCategoryEntity(id = 4, language = "en", categoryName = "Networking"),
        TechnologyCategoryEntity(id = 5, language = "en", categoryName = "Data"),
        TechnologyCategoryEntity(id = 6, language = "en", categoryName = "Security"),
        TechnologyCategoryEntity(id = 7, language = "en", categoryName = "Architecture"),
        TechnologyCategoryEntity(id = 8, language = "en", categoryName = "Notifications"),
        TechnologyCategoryEntity(id = 9, language = "en", categoryName = "Maps"),
        TechnologyCategoryEntity(id = 10, language = "en", categoryName = "Media"),
        TechnologyCategoryEntity(id = 11, language = "en", categoryName = "Monetization"),
        TechnologyCategoryEntity(id = 12, language = "en", categoryName = "Tooling"),
        TechnologyCategoryEntity(id = 13, language = "en", categoryName = "Testing"),
        TechnologyCategoryEntity(id = 14, language = "en", categoryName = "Telemetry")
    )

    private fun technologyCategoriesCs() = listOf(
        TechnologyCategoryEntity(id = 15, language = "cs", categoryName = "Základy"),
        TechnologyCategoryEntity(id = 16, language = "cs", categoryName = "UI"),
        TechnologyCategoryEntity(id = 17, language = "cs", categoryName = "Paralelizace"),
        TechnologyCategoryEntity(id = 18, language = "cs", categoryName = "Síťování"),
        TechnologyCategoryEntity(id = 19, language = "cs", categoryName = "Data"),
        TechnologyCategoryEntity(id = 20, language = "cs", categoryName = "Bezpečnost"),
        TechnologyCategoryEntity(id = 21, language = "cs", categoryName = "Architektura"),
        TechnologyCategoryEntity(id = 22, language = "cs", categoryName = "Notifikace"),
        TechnologyCategoryEntity(id = 23, language = "cs", categoryName = "Mapy"),
        TechnologyCategoryEntity(id = 24, language = "cs", categoryName = "Média"),
        TechnologyCategoryEntity(id = 25, language = "cs", categoryName = "Monetizace"),
        TechnologyCategoryEntity(id = 26, language = "cs", categoryName = "Nástroje"),
        TechnologyCategoryEntity(id = 27, language = "cs", categoryName = "Testování"),
        TechnologyCategoryEntity(id = 28, language = "cs", categoryName = "Telemetrie")
    )

    private fun technologiesEn() = listOf(
        // Basics (1)
        TechnologyEntity(language = "en", categoryId = 1, name = "Activity", description = ""),
        TechnologyEntity(language = "en", categoryId = 1, name = "Services", description = ""),
        TechnologyEntity(language = "en", categoryId = 1, name = "Fragments", description = ""),
        TechnologyEntity(language = "en", categoryId = 1, name = "Composable functions", description = ""),
        TechnologyEntity(language = "en", categoryId = 1, name = "Lifecycle", description = ""),
        // UI (2)
        TechnologyEntity(language = "en", categoryId = 2, name = "Jetpack Compose", description = ""),
        TechnologyEntity(language = "en", categoryId = 2, name = "XML layouts", description = ""),
        TechnologyEntity(language = "en", categoryId = 2, name = "Custom Views (Canvas)", description = ""),
        TechnologyEntity(language = "en", categoryId = 2, name = "Material Design", description = ""),
        // Parallelization (3)
        TechnologyEntity(language = "en", categoryId = 3, name = "Kotlin Coroutines", description = ""),
        TechnologyEntity(language = "en", categoryId = 3, name = "Java Executors", description = ""),
        // Networking (4)
        TechnologyEntity(language = "en", categoryId = 4, name = "OkHttp", description = ""),
        TechnologyEntity(language = "en", categoryId = 4, name = "Retrofit", description = ""),
        TechnologyEntity(language = "en", categoryId = 4, name = "REST APIs", description = ""),
        TechnologyEntity(language = "en", categoryId = 4, name = "JSON", description = ""),
        TechnologyEntity(language = "en", categoryId = 4, name = "GSON", description = ""),
        TechnologyEntity(language = "en", categoryId = 4, name = "Base64", description = ""),
        // Data (5)
        TechnologyEntity(language = "en", categoryId = 5, name = "Room DB", description = ""),
        TechnologyEntity(language = "en", categoryId = 5, name = "SQLite", description = ""),
        TechnologyEntity(language = "en", categoryId = 5, name = "Shared Preferences", description = ""),
        // Security (6)
        TechnologyEntity(language = "en", categoryId = 6, name = "AES", description = ""),
        TechnologyEntity(language = "en", categoryId = 6, name = "R8 / ProGuard", description = ""),
        TechnologyEntity(language = "en", categoryId = 6, name = "Secure token handling", description = ""),
        TechnologyEntity(language = "en", categoryId = 6, name = "JWT", description = ""),
        TechnologyEntity(language = "en", categoryId = 6, name = "Google Sign-In", description = ""),
        // Architecture (7)
        TechnologyEntity(language = "en", categoryId = 7, name = "ViewModel", description = ""),
        TechnologyEntity(language = "en", categoryId = 7, name = "Navigation Component", description = ""),
        TechnologyEntity(language = "en", categoryId = 7, name = "KMM", description = ""),
        // Notifications (8)
        TechnologyEntity(language = "en", categoryId = 8, name = "Push notifications", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Deep links", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Pusher", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Pusher Beams", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Pushwoosh", description = ""),
        // Maps (9)
        TechnologyEntity(language = "en", categoryId = 9, name = "Mapbox SDK", description = ""),
        // Media (10)
        TechnologyEntity(language = "en", categoryId = 10, name = "ExoPlayer", description = ""),
        // Monetization (11)
        TechnologyEntity(language = "en", categoryId = 11, name = "Google Play Billing library", description = ""),
        TechnologyEntity(language = "en", categoryId = 11, name = "Stripe SDK", description = ""),
        // Tooling (12)
        TechnologyEntity(language = "en", categoryId = 12, name = "Gradle", description = ""),
        TechnologyEntity(language = "en", categoryId = 12, name = "Google Play Console", description = ""),
        TechnologyEntity(language = "en", categoryId = 12, name = "App Signing", description = ""),
        // Testing (13)
        TechnologyEntity(language = "en", categoryId = 13, name = "Unit tests", description = ""),
        TechnologyEntity(language = "en", categoryId = 13, name = "System tests", description = ""),
        TechnologyEntity(language = "en", categoryId = 13, name = "Organizing of internal beta testing", description = ""),
        // Telemetry (14)
        TechnologyEntity(language = "en", categoryId = 14, name = "Sentry", description = ""),
        TechnologyEntity(language = "en", categoryId = 14, name = "Mixpanel", description = "")
    )

    private fun technologiesCs() = listOf(
        // Základy (15)
        TechnologyEntity(language = "cs", categoryId = 15, name = "Activity", description = ""),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Services", description = ""),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Fragmenty", description = ""),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Composable funkce", description = ""),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Lifecycle", description = ""),
        // UI (16)
        TechnologyEntity(language = "cs", categoryId = 16, name = "Jetpack Compose", description = ""),
        TechnologyEntity(language = "cs", categoryId = 16, name = "XML layouty", description = ""),
        TechnologyEntity(language = "cs", categoryId = 16, name = "Custom Views (Canvas)", description = ""),
        TechnologyEntity(language = "cs", categoryId = 16, name = "Material Design", description = ""),
        // Paralelizace (17)
        TechnologyEntity(language = "cs", categoryId = 17, name = "Kotlin Coroutines", description = ""),
        TechnologyEntity(language = "cs", categoryId = 17, name = "Java Executors", description = ""),
        // Síťování (18)
        TechnologyEntity(language = "cs", categoryId = 18, name = "OkHttp", description = ""),
        TechnologyEntity(language = "cs", categoryId = 18, name = "Retrofit", description = ""),
        TechnologyEntity(language = "cs", categoryId = 18, name = "REST APIs", description = ""),
        TechnologyEntity(language = "cs", categoryId = 18, name = "JSON", description = ""),
        TechnologyEntity(language = "cs", categoryId = 18, name = "GSON", description = ""),
        TechnologyEntity(language = "cs", categoryId = 18, name = "Base64", description = ""),
        // Data (19)
        TechnologyEntity(language = "cs", categoryId = 19, name = "Room DB", description = ""),
        TechnologyEntity(language = "cs", categoryId = 19, name = "SQLite", description = ""),
        TechnologyEntity(language = "cs", categoryId = 19, name = "Shared Preferences", description = ""),
        // Bezpečnost (20)
        TechnologyEntity(language = "cs", categoryId = 20, name = "AES", description = ""),
        TechnologyEntity(language = "cs", categoryId = 20, name = "R8 / ProGuard", description = ""),
        TechnologyEntity(language = "cs", categoryId = 20, name = "Secure token handling", description = ""),
        TechnologyEntity(language = "cs", categoryId = 20, name = "JWT", description = ""),
        TechnologyEntity(language = "cs", categoryId = 20, name = "Google Sign-In", description = ""),
        // Architektura (21)
        TechnologyEntity(language = "cs", categoryId = 21, name = "ViewModel", description = ""),
        TechnologyEntity(language = "cs", categoryId = 21, name = "Navigation Component", description = ""),
        TechnologyEntity(language = "cs", categoryId = 21, name = "KMM", description = ""),
        // Notifikace (22)
        TechnologyEntity(language = "cs", categoryId = 22, name = "Push notifications", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Deep links", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Pusher", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Pusher Beams", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Pushwoosh", description = ""),
        // Mapy (23)
        TechnologyEntity(language = "cs", categoryId = 23, name = "Mapbox SDK", description = ""),
        // Média (24)
        TechnologyEntity(language = "cs", categoryId = 24, name = "ExoPlayer", description = ""),
        // Monetizace (25)
        TechnologyEntity(language = "cs", categoryId = 25, name = "Google Play Billing library", description = ""),
        TechnologyEntity(language = "cs", categoryId = 25, name = "Stripe SDK", description = ""),
        // Nástroje (26)
        TechnologyEntity(language = "cs", categoryId = 26, name = "Gradle", description = ""),
        TechnologyEntity(language = "cs", categoryId = 26, name = "Google Play Console", description = ""),
        TechnologyEntity(language = "cs", categoryId = 26, name = "App Signing", description = ""),
        // Testování (27)
        TechnologyEntity(language = "cs", categoryId = 27, name = "Unit testy", description = ""),
        TechnologyEntity(language = "cs", categoryId = 27, name = "Systémové testy", description = ""),
        TechnologyEntity(language = "cs", categoryId = 27, name = "Organizace interního testování", description = ""),
        // Telemetrie (28)
        TechnologyEntity(language = "cs", categoryId = 28, name = "Sentry", description = ""),
        TechnologyEntity(language = "cs", categoryId = 28, name = "Mixpanel", description = "")
    )

    // =========================================================================
    // Other Skill Categories & Skills
    // =========================================================================
    // EN categories get IDs 1–8, CS categories get IDs 9–16.

    private fun otherSkillCategoriesEn() = listOf(
        OtherSkillCategoryEntity(id = 1, language = "en", categoryName = "Versioning"),
        OtherSkillCategoryEntity(id = 2, language = "en", categoryName = "Organization"),
        OtherSkillCategoryEntity(id = 3, language = "en", categoryName = "CI/CD"),
        OtherSkillCategoryEntity(id = 4, language = "en", categoryName = "Backend"),
        OtherSkillCategoryEntity(id = 5, language = "en", categoryName = "Server-side"),
        OtherSkillCategoryEntity(id = 6, language = "en", categoryName = "Infrastructure"),
        OtherSkillCategoryEntity(id = 7, language = "en", categoryName = "OS"),
        OtherSkillCategoryEntity(id = 8, language = "en", categoryName = "Others")
    )

    private fun otherSkillCategoriesCs() = listOf(
        OtherSkillCategoryEntity(id = 9, language = "cs", categoryName = "Verzování"),
        OtherSkillCategoryEntity(id = 10, language = "cs", categoryName = "Organizace"),
        OtherSkillCategoryEntity(id = 11, language = "cs", categoryName = "CI/CD"),
        OtherSkillCategoryEntity(id = 12, language = "cs", categoryName = "Backend"),
        OtherSkillCategoryEntity(id = 13, language = "cs", categoryName = "Server-side"),
        OtherSkillCategoryEntity(id = 14, language = "cs", categoryName = "Infrastruktura"),
        OtherSkillCategoryEntity(id = 15, language = "cs", categoryName = "OS"),
        OtherSkillCategoryEntity(id = 16, language = "cs", categoryName = "Ostatní")
    )

    private fun otherSkillsEn() = listOf(
        // Versioning (1)
        OtherSkillEntity(language = "en", categoryId = 1, name = "Git", description = ""),
        OtherSkillEntity(language = "en", categoryId = 1, name = "GitLab", description = ""),
        OtherSkillEntity(language = "en", categoryId = 1, name = "GitHub", description = ""),
        // Organization (2)
        OtherSkillEntity(language = "en", categoryId = 2, name = "Slack", description = ""),
        OtherSkillEntity(language = "en", categoryId = 2, name = "Zoom", description = ""),
        OtherSkillEntity(language = "en", categoryId = 2, name = "GitHub Issues", description = ""),
        OtherSkillEntity(language = "en", categoryId = 2, name = "Jira", description = ""),
        OtherSkillEntity(language = "en", categoryId = 2, name = "Clockify", description = ""),
        // CI/CD (3)
        OtherSkillEntity(language = "en", categoryId = 3, name = "Jenkins", description = ""),
        OtherSkillEntity(language = "en", categoryId = 3, name = "Teamcity", description = ""),
        // Backend (4)
        OtherSkillEntity(language = "en", categoryId = 4, name = "Ktor", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "MariaDB", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "MySQL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "PostgreSQL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "Elasticsearch", description = ""),
        // Server-side (5)
        OtherSkillEntity(language = "en", categoryId = 5, name = "Apache", description = ""),
        OtherSkillEntity(language = "en", categoryId = 5, name = "Nginx", description = ""),
        OtherSkillEntity(language = "en", categoryId = 5, name = "Supervisord", description = ""),
        OtherSkillEntity(language = "en", categoryId = 5, name = "TLS/SSL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 5, name = "Wowza Streaming Engine", description = ""),
        // Infrastructure (6)
        OtherSkillEntity(language = "en", categoryId = 6, name = "AWS", description = ""),
        OtherSkillEntity(language = "en", categoryId = 6, name = "Hetzner", description = ""),
        OtherSkillEntity(language = "en", categoryId = 6, name = "Linode", description = ""),
        OtherSkillEntity(language = "en", categoryId = 6, name = "Digital Ocean", description = ""),
        // OS (7)
        OtherSkillEntity(language = "en", categoryId = 7, name = "GNU/Linux", description = ""),
        OtherSkillEntity(language = "en", categoryId = 7, name = "Android", description = ""),
        OtherSkillEntity(language = "en", categoryId = 7, name = "Mac OS", description = ""),
        OtherSkillEntity(language = "en", categoryId = 7, name = "Windows", description = ""),
        // Others (8)
        OtherSkillEntity(language = "en", categoryId = 8, name = "HTML/CSS", description = ""),
        OtherSkillEntity(language = "en", categoryId = 8, name = "SQL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 8, name = "LaTeX", description = "")
    )

    private fun otherSkillsCs() = listOf(
        // Verzování (9)
        OtherSkillEntity(language = "cs", categoryId = 9, name = "Git", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 9, name = "GitLab", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 9, name = "GitHub", description = ""),
        // Organizace (10)
        OtherSkillEntity(language = "cs", categoryId = 10, name = "Slack", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 10, name = "Zoom", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 10, name = "GitHub Issues", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 10, name = "Jira", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 10, name = "Clockify", description = ""),
        // CI/CD (11)
        OtherSkillEntity(language = "cs", categoryId = 11, name = "Jenkins", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 11, name = "Teamcity", description = ""),
        // Backend (12)
        OtherSkillEntity(language = "cs", categoryId = 12, name = "Ktor", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "MariaDB", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "MySQL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "PostgreSQL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "Elasticsearch", description = ""),
        // Server-side (13)
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Apache", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Nginx", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Supervisord", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "TLS/SSL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Wowza Streaming Engine", description = ""),
        // Infrastruktura (14)
        OtherSkillEntity(language = "cs", categoryId = 14, name = "AWS", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 14, name = "Hetzner", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 14, name = "Linode", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 14, name = "Digital Ocean", description = ""),
        // OS (15)
        OtherSkillEntity(language = "cs", categoryId = 15, name = "GNU/Linux", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 15, name = "Android", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 15, name = "Mac OS", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 15, name = "Windows", description = ""),
        // Ostatní (16)
        OtherSkillEntity(language = "cs", categoryId = 16, name = "HTML/CSS", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 16, name = "SQL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 16, name = "LaTeX", description = "")
    )

    // =========================================================================
    // Languages (spoken)
    // =========================================================================

    private fun languagesEn() = listOf(
        LanguageEntity(language = "en", name = "Czech", level = "Native", note = null),
        LanguageEntity(language = "en", name = "English", level = "Intermediate (~C1)",
            note = "Fluent interaction with native speakers")
    )

    private fun languagesCs() = listOf(
        LanguageEntity(language = "cs", name = "Čeština", level = "Mateřský jazyk", note = null),
        LanguageEntity(language = "cs", name = "Angličtina", level = "Pokročilý",
            note = "Znalost umožňující profesionální práci")
    )

    // =========================================================================
    // Personality Traits
    // =========================================================================

    private fun personalityTraitsEn() = listOf(
        PersonalityTraitEntity(language = "en", trait = "Independence", description = ""),
        PersonalityTraitEntity(language = "en", trait = "Honesty", description = ""),
        PersonalityTraitEntity(language = "en", trait = "Perseverance", description = ""),
        PersonalityTraitEntity(language = "en", trait = "Passion to learn new things", description = "")
    )

    private fun personalityTraitsCs() = listOf(
        PersonalityTraitEntity(language = "cs", trait = "Samostatnost", description = ""),
        PersonalityTraitEntity(language = "cs", trait = "Poctivost", description = ""),
        PersonalityTraitEntity(language = "cs", trait = "Vytrvalost", description = ""),
        PersonalityTraitEntity(language = "cs", trait = "Rád experimentuji a učím se nové věci", description = "")
    )

    // =========================================================================
    // Interests
    // =========================================================================

    private fun interestsEn() = listOf(
        InterestEntity(language = "en", name = "Photography", description = ""),
        InterestEntity(language = "en", name = "Graphics", description = ""),
        InterestEntity(language = "en", name = "Artificial Intelligence", description = ""),
        InterestEntity(language = "en", name = "Travel", description = "")
    )

    private fun interestsCs() = listOf(
        InterestEntity(language = "cs", name = "Fotografie", description = ""),
        InterestEntity(language = "cs", name = "Počítačová grafika", description = ""),
        InterestEntity(language = "cs", name = "Umělá inteligence", description = ""),
        InterestEntity(language = "cs", name = "Cestování", description = "")
    )
}
