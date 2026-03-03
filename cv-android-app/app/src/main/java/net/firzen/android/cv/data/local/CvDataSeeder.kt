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
        address = "Komenského 316/13, 679 04 Adamov",
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
        TechnologyEntity(language = "en", categoryId = 1, name = "Activity", description = "Activities are the fundamental building block of every Android application. In the past, there was actually nothing else besides Activities+Views in apps. They have their own lifecycle and are still used in every application at least once."),
        TechnologyEntity(language = "en", categoryId = 1, name = "Services", description = "Useful when some code needs to run in the background while the user has minimized the app, locked the screen, or otherwise paused it. In short, it's something that can reliably work even when the application has been not only paused but also deallocated. A typical example is playing audio or video in the background along with a notification. I naturally have experience with them, but I wouldn't say I'm an expert in this area."),
        TechnologyEntity(language = "en", categoryId = 1, name = "Fragments", description = "Fragments were a big step up from plain Views for me, since they have their own lifecycle, which to some extent eliminates the need for tight coupling with the parent component. Except for a few new projects recently, I've used them everywhere."),
        TechnologyEntity(language = "en", categoryId = 1, name = "Composable functions", description = ""),
        TechnologyEntity(language = "en", categoryId = 1, name = "Lifecycle", description = "Lifecycles are very important in Android. These are events that occur when the user leaves the app, changes the language, locks the screen, or when the system deallocates the app. The application must then respond appropriately to these changes depending on the goal. ViewModels and Coroutines greatly simplify reacting to these changes."),
        // UI (2)
        TechnologyEntity(language = "en", categoryId = 2, name = "Jetpack Compose", description = "Compose is something that is relatively new for me, but similar to when I first tried Kotlin, after a brief experiment I was already sure that it would help me be much more productive, and that it's simply the right path forward."),
        TechnologyEntity(language = "en", categoryId = 2, name = "XML layouts", description = "I have about 7 years of experience with classic XML layouts, and during that time I've learned to quickly implement practically any design. I used ConstraintLayout extensively, which is very good for supporting landscape mode."),
        TechnologyEntity(language = "en", categoryId = 2, name = "Custom Views (Canvas)", description = ""),
        TechnologyEntity(language = "en", categoryId = 2, name = "Material Design", description = ""),
        // Parallelization (3)
        TechnologyEntity(language = "en", categoryId = 3, name = "Kotlin Coroutines", description = "I only started using Coroutines recently. It was yet another technology I had been eyeing for a long time but was afraid would be difficult to learn. The opposite turned out to be true \u2014 I discovered that Dispatchers are very similar to my Executor setup, and that coroutines also automatically respond to application lifecycle events."),
        TechnologyEntity(language = "en", categoryId = 3, name = "Java Executors", description = "I discovered Executors back in 2019. Those were 'dark times' when the internet was flooded with examples that had all logic added directly into Activities, and threads were spawned directly when needed \u2014 nobody cared about their count or lifecycle. Even back then I could see this wasn't a good approach, and I started organizing threads into thread pools using the Executor class in my apps. Already at that time I was using 3 thread categories \u2014 IO, network, and UI."),
        // Networking (4)
        TechnologyEntity(language = "en", categoryId = 4, name = "OkHttp", description = "I've been using this library for many years and have only had the best experiences with it. Everything needed for any type of request can be defined very concisely and clearly. The library is also really very stable and even allows implementing custom interceptors, e.g. for caching purposes. It's indispensable for all kinds of non-standard APIs that don't follow conventional structure."),
        TechnologyEntity(language = "en", categoryId = 4, name = "Retrofit", description = "I'm embarrassingly new to Retrofit, because it always seemed complex to me, so I used OkHttp for everything. In reality, however, this library is very simple and useful. Configuring entities via annotations is exactly the approach I'd expect. It's great for eliminating boilerplate code."),
        TechnologyEntity(language = "en", categoryId = 4, name = "REST APIs", description = "Who doesn't know REST APIs, right? It's like asking a driver if they work with a steering wheel. I actually have it in my CV mainly for indexing purposes. However, I don't just use REST APIs on the client side \u2014 I've also created a few myself."),
        TechnologyEntity(language = "en", categoryId = 4, name = "JSON", description = "JSON is such a small unassuming thing that doesn't get talked about much, but I'm sure all of us who experienced XML or even more cumbersome data transfer formats love it. JSON is used in practically every project I've ever worked on."),
        TechnologyEntity(language = "en", categoryId = 4, name = "GSON", description = ""),
        TechnologyEntity(language = "en", categoryId = 4, name = "Base64", description = ""),
        // Data (5)
        TechnologyEntity(language = "en", categoryId = 5, name = "Room DB", description = "Room is, in my opinion, truly excellent. I've worked with raw SQL and with Hibernate, and both are extremes that I consider rather unfortunate in real-world use. I like that Room doesn't try to completely replace SQL but rather builds on top of it. The cherry on top is the elegant versioning system and migration definitions. I use it everywhere it makes sense."),
        TechnologyEntity(language = "en", categoryId = 5, name = "SQLite", description = ""),
        TechnologyEntity(language = "en", categoryId = 5, name = "Shared Preferences", description = "In my opinion, Shared Preferences are mediocre, which is why I created a wrapper called PrefTool. Not yet published, but maybe one day."),
        // Security (6)
        TechnologyEntity(language = "en", categoryId = 6, name = "AES", description = "We used AES encryption for communication with an internal API at Sanctus Media. This encryption, as I discovered, is a built-in part of Java."),
        TechnologyEntity(language = "en", categoryId = 6, name = "R8 / ProGuard", description = "I have a mixed relationship with ProGuard. It's great when it works, and minification significantly reduces APK size, but when it doesn't work as expected, it becomes a little debugging hell full of incantations in proguard-rules.pro. In any case, ProGuard can definitely surprise you \u2014 especially with a non-functioning release build after everyone believed the app worked flawlessly (but only in debug mode)."),
        TechnologyEntity(language = "en", categoryId = 6, name = "Secure token handling", description = ""),
        TechnologyEntity(language = "en", categoryId = 6, name = "JWT", description = ""),
        TechnologyEntity(language = "en", categoryId = 6, name = "Google Sign-In", description = "Implementing Google Sign-In in the CrossReach app was quite a painful experience, but I managed it in the end. Along the way there were many non-working snippets in the documentation, optional settings in Google Cloud Console that turned out to be mandatory, the impossibility of reasonable testing before uploading the binary to internal testing in Google Play Console, etc. In short, it was weeks of fun. But I'm sure I would manage it faster on a second try."),
        // Architecture (7)
        TechnologyEntity(language = "en", categoryId = 7, name = "ViewModel", description = ""),
        TechnologyEntity(language = "en", categoryId = 7, name = "Navigation Component", description = ""),
        TechnologyEntity(language = "en", categoryId = 7, name = "KMM", description = "I've tried many 'code once, run everywhere' solutions. Xamarin, Cordova, Flutter, React \u2014 all these frameworks promise miracles, but they all have a large entry cost: the need to learn an entirely new language. This ultimately means migrating essentially all existing code, which I'd say is unacceptable in most cases. Moreover, these frameworks tend to have many limitations, and it's rarely possible to leverage the full potential of the target platform through them. Kotlin Multiplatform Mobile therefore greatly impressed me because both Android and iOS developer(s) can keep their existing code unchanged. The shared code portion is in Kotlin, which seems acceptable to me. I successfully got KMM running for one yet-unreleased project. The only limitation I see is the inability to use libraries written in Java \u2014 for KMM to work, all shared code must be in Kotlin."),
        // Notifications (8)
        TechnologyEntity(language = "en", categoryId = 8, name = "Push notifications", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Deep links", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Pusher", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Pusher Beams", description = ""),
        TechnologyEntity(language = "en", categoryId = 8, name = "Pushwoosh", description = ""),
        // Maps (9)
        TechnologyEntity(language = "en", categoryId = 9, name = "Mapbox SDK", description = "These maps were chosen for WattsUp due to the generous free tier of queries for calculating routes from point A to B. I have quite a mixed relationship with this library. While it works very well for basic tasks, it's not very optimized for displaying many points on the map. Additionally, new versions of the library repeatedly introduced undocumented breaking changes, and I spent entire weeks implementing them on more than one occasion."),
        // Media (10)
        TechnologyEntity(language = "en", categoryId = 10, name = "ExoPlayer", description = "I used ExoPlayer, or rather its wrapper called Media3, in the Sanctuary First and CrossReach apps. Probably because I started working with this library right after its first version was released, I ran into a number of issues \u2014 from non-functioning documentation to not entirely sensible limitations. Media3, for example, doesn't include support for fullscreen playback, which I'd say is hardly a luxury. I therefore created my own wrapper called Just Play, which significantly simplifies using the Media3 library and adds various missing features."),
        // Monetization (11)
        TechnologyEntity(language = "en", categoryId = 11, name = "Google Play Billing library", description = ""),
        TechnologyEntity(language = "en", categoryId = 11, name = "Stripe SDK", description = ""),
        // Tooling (12)
        TechnologyEntity(language = "en", categoryId = 12, name = "Gradle", description = "Some might say that Gradle is complex and full of various hacks. I see it differently, however, because I've experienced Maven. Anyone who has worked with Maven will surely agree that Gradle is a gift from above, and therefore its minor imperfections are not up for discussion."),
        TechnologyEntity(language = "en", categoryId = 12, name = "Google Play Console", description = "I have experience with most tasks in Google Play Console \u2014 for example, publishing new versions, managing the list of testers, testing payments, setting up deep links, managing the app listing, filling in app content, managing users and configuring their access to statistics, etc."),
        TechnologyEntity(language = "en", categoryId = 12, name = "App Signing", description = ""),
        // Testing (13)
        TechnologyEntity(language = "en", categoryId = 13, name = "Unit tests", description = "For unit tests I use the standard JUnit library."),
        TechnologyEntity(language = "en", categoryId = 13, name = "System tests", description = ""),
        TechnologyEntity(language = "en", categoryId = 13, name = "Organizing of internal beta testing", description = "I've organized manual testing before releasing major updates several times. This mainly concerned WattsUp, but also at least once Sanctuary First and CrossReach. On my part, this mainly involved creating forms via Google Forms, which were focused on testing the parts of the app that were added, changed, or could have had bugs introduced in the given version."),
        // Telemetry (14)
        TechnologyEntity(language = "en", categoryId = 14, name = "Sentry", description = ""),
        TechnologyEntity(language = "en", categoryId = 14, name = "Mixpanel", description = "I've used this analytics tool in several projects. Of course, I created my own EventReporter to prevent vendor lock-in, but so far Mixpanel looks good.")
    )

    private fun technologiesCs() = listOf(
        // Základy (15)
        TechnologyEntity(language = "cs", categoryId = 15, name = "Activity", description = "Jsou základním stavebním kamenem každé Android aplikace, a dříve dokonce ani nic jiného, než Activity+Views v aplikacích nebylo. Mají svůj životní cyklus a jsou dodnes používány v každé aplikaci alespoň jednou."),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Services", description = "Hodí se v momentě, kdy musí nějaký kód běžet na pozadí v situaci, kdy uživatel aplikaci minimalizoval, zamkl obrazovku, či jinak pozastavil. Zkrátka a dobře je to něco, co může spolehlivě fungovat i v momentě, kdy byla aplikace nejen pozastavena, ale i dealokována. Typickým příkladem je přehrávání zvuku či videa na pozadí spolu s notifikací. Zkušenost s nimi pochopitelně mám, ale neřekl bych, že jsem v tomto expert."),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Fragmenty", description = "Fragmenty pro mě byly velký posun od prostých Views, neboť mají vlastní životní cyklus, což do jisté míry eliminuje nutnost úzké provázanosti s rodičovskou komponentou. Až na pár nových projektů z poslední doby jsem je použil všude."),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Composable funkce", description = ""),
        TechnologyEntity(language = "cs", categoryId = 15, name = "Lifecycle", description = "Životní cyklus či spíše cykly jsou v Androidu velmi důležité. Jsou to události nastávající v momentech, kdy uživatel opustí aplikaci, změní jazyk, zamkne obrazovku, anebo když systém aplikaci dealokuje. Aplikace pak na tyto změny musí vhodně reagovat podle toho, co je cílem. ViewModely a Coroutines pak značně usnadňují reakce na tyto změny."),
        // UI (16)
        TechnologyEntity(language = "cs", categoryId = 16, name = "Jetpack Compose", description = "Compose je něco, co je pro mě sice relativně novinkou, ale podobně jako to bylo v případě Kotlinu, jsem si byl již po krátkém experimentu jistý, že je to něco, co mi pomůže být mnohem produktivnější, a že je to zkrátka správná cesta dalšího vývoje."),
        TechnologyEntity(language = "cs", categoryId = 16, name = "XML layouty", description = "S klasickými XML layouty mám asi 7 let zkušeností, a za tu dobu jsem se s nimi naučil rychle implementovat prakticky jakýkoli design. Hojně jsem používal ConstraintLayout, který je velmi dobrý pro podporu režimu na šířku."),
        TechnologyEntity(language = "cs", categoryId = 16, name = "Custom Views (Canvas)", description = ""),
        TechnologyEntity(language = "cs", categoryId = 16, name = "Material Design", description = ""),
        // Paralelizace (17)
        TechnologyEntity(language = "cs", categoryId = 17, name = "Kotlin Coroutines", description = "Coroutines jsem začal používat teprve nedávno. Byla to opět technologie, po které jsem už dlouho pokukoval, ale měl strach, že bude náročné se to naučit. Opak byl ale pravdou \u2013 zjistil jsem, že Dispatchery se velmi podobají mému uspořádání Executorů, a že coroutines navíc automatický reagují na události životního cyklu aplikace."),
        TechnologyEntity(language = "cs", categoryId = 17, name = "Java Executors", description = "Executory jsem objevil už v roce 2019. Tehdy to byly \u201Etemné časy\u201C, kdy byl internet zahlcený příklady s veškerou logikou přidanou přímo do Aktivit, a vlákna se spouštěla přímo, když byla potřeba \u2013 nijak se neřešil jejich počet či životní cyklus.. Už v té době jsem viděl, že toto není dobrý postup, a začal ve svých aplikacích vlákna organizovat do thread-poolů pomocí třídy Executor. Již tehdy jsem používal 3 kategorie vláken \u2013 IO, network a UI."),
        // Síťování (18)
        TechnologyEntity(language = "cs", categoryId = 18, name = "OkHttp", description = "Tuto knihovnu používám už mnoho let, a mám s ní jen ty nejlepší zkušenosti. Vše potřebné k jakémukoli typu dotazu lze definovat velmi stručně a přehledně. Knihovna je navíc opravdu velmi stabilní, a umožňuje i implementaci vlastního interceptoru např. pro účely cachování. Je nepostradatelná pro všechny možné nestandardní API, které se vymykají běžné struktuře."),
        TechnologyEntity(language = "cs", categoryId = 18, name = "Retrofit", description = "Retrofit znám trestuhodně poměrně čerstvě, protože mi vždy přišel složitý, a tak jsem používal OkHttp na všechno. Ve skutečnosti je ale tato knihovna velmi jednoduchá a užitečná. Konfigurace entit pomocí anotací je přesně ten způsob, jaký bych očekával. Je to dobrá věc pro eliminaci boilerplate kódu."),
        TechnologyEntity(language = "cs", categoryId = 18, name = "REST APIs", description = "Kdo by neznal REST API, že? Je to, jako se ptát řidiče, jestli pracuje s volantem. V CV to mám vlastně jen kvůli indexování. REST API však nepoužívám jen ze strany klienta \u2013 pár jsem jich i sám vytvořil."),
        TechnologyEntity(language = "cs", categoryId = 18, name = "JSON", description = "JSON je taková malá nenápadná věc, o které se moc nemluví, ale jsem si jistý, že jej milují všichni z nás, kdo zažili XML nebo ještě krkolomnější formáty pro přenos dat. JSON používají prakticky všechny projekty, na kterých jsem kdy pracoval."),
        TechnologyEntity(language = "cs", categoryId = 18, name = "GSON", description = ""),
        TechnologyEntity(language = "cs", categoryId = 18, name = "Base64", description = ""),
        // Data (19)
        TechnologyEntity(language = "cs", categoryId = 19, name = "Room DB", description = "Room je za mě opravdu skvělá věc. Pracoval jsem s čistým SQL i s Hibernate, a obojí jsou extrémy za mě nepříliš šťastné v reálném provozu. Líbí se mi, že Room se nesnaží zcela nahradit SQL, ale je jeho nadstavbou. Třešnička na dortu je pak elegantní systém verzí a definice migrací. Používám všude, kde to má smysl."),
        TechnologyEntity(language = "cs", categoryId = 19, name = "SQLite", description = ""),
        TechnologyEntity(language = "cs", categoryId = 19, name = "Shared Preferences", description = "Shared Preferences jsou podle mě nic moc, a proto jsem vytvořil jejich nadstavbu zvanou PrefTool. Zatím nezveřejněno, ale jednoho dne možná ano."),
        // Bezpečnost (20)
        TechnologyEntity(language = "cs", categoryId = 20, name = "AES", description = "Šifrování AES jsme použili pro komunikaci s interním API v Sanctus Media. Toto šifrování je, jak jsem zjistil, přímo součástí Javy."),
        TechnologyEntity(language = "cs", categoryId = 20, name = "R8 / ProGuard", description = "S ProGuardem mám smíšený vztah.. Je skvělý, když funguje, a navíc minify výrazně zmenší velikost APK souboru, ale když nefunguje, jak má, tak je to takové malé debugovací peklo plné zaklínadel v proguard-rules.pro. Každopádně ProGuard rozhodně umí překvapit \u2013 a to zejména nefungujícím release buildem po tom, co všichni věřili, že aplikace bezchybně funguje (avšak pouze v debug režimu)."),
        TechnologyEntity(language = "cs", categoryId = 20, name = "Secure token handling", description = ""),
        TechnologyEntity(language = "cs", categoryId = 20, name = "JWT", description = ""),
        TechnologyEntity(language = "cs", categoryId = 20, name = "Google Sign-In", description = "Implementace Google sign-in v aplikaci CrossReach byla poměrně strastiplná, ale nakonec jsem to zvládl. Na cestě k úspěchu čekalo mnoho nefungujících snippetů v dokumentaci, nepovinná nastavení v Google Cloud Console, která se ukázala ve skutečnosti být povinná; nemožnost rozumného testování před nahráním binárky do interního testování v Google Play Console, atd. Zkrátka a dobře to byla zábava na několik týdnů. Jsem si ale jistý, že na druhý pokus už bych to zvládl rychleji."),
        // Architektura (21)
        TechnologyEntity(language = "cs", categoryId = 21, name = "ViewModel", description = ""),
        TechnologyEntity(language = "cs", categoryId = 21, name = "Navigation Component", description = ""),
        TechnologyEntity(language = "cs", categoryId = 21, name = "KMM", description = "Zkoušel jsem již mnoho řešení na způsob \u201Ecode once, run everywhere\u201C. Xamarin, Cordova, Flutter, React.. všechny tyto frameworky slibují zázrak, ale všechny mají také velkou vstupní daň: Nutnost se naučit zcela nový jazyk. To ve výsledku znamená migrovat v podstatě veškerý současný kód, a to je, řekl bych, ve většině případů nepřijatelné. Navíc tyto frameworky mívají mnoho omezení, a jen málokdy je možné jejich prostřednictvím využít plný potenciál cílové platformy. Kotlin Multiplatform Mobile mě proto nesmírně zaujal tím, že Android i iOS vývojář(i) si mohou ponechat svůj dosavadní kód v nezměněné formě. Sdílená část kódu je pak v Kotlinu, což mi přijde přijatelné. Úspěšně jsem KMM zprovoznil pro jeden dosud nevydaný projekt. Jako jediné omezení bych viděl nemožnost použití knihoven psaných v Javě \u2013 aby KMM fungovalo, tak večkěrý stílený kód musí být v Kotlinu."),
        // Notifikace (22)
        TechnologyEntity(language = "cs", categoryId = 22, name = "Push notifications", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Deep links", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Pusher", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Pusher Beams", description = ""),
        TechnologyEntity(language = "cs", categoryId = 22, name = "Pushwoosh", description = ""),
        // Mapy (23)
        TechnologyEntity(language = "cs", categoryId = 23, name = "Mapbox SDK", description = "Tyto mapy byly vybrány ve WattsUpu vzhledem ke štědrému free-tieru dotazů pro výpočet trasy z body A do B. Ke knihovně mám dost rozporuplný vztah. Sice pro základní věci funguje velmi dobře, avšak pro zobrazení mnoha bodů v mapě není příliš optimalizovaná, a navíc nové verze knihovny opakovaně přinesly breaking changes, které byly nedokumentované, a strávil jsem jejich implementací nejednou celé týdny."),
        // Média (24)
        TechnologyEntity(language = "cs", categoryId = 24, name = "ExoPlayer", description = "ExoPlayer, respektive jeho nadstavbu zvanou Media3 jsem použil v aplikaci Sanctuary First a CrossReach. Asi vzhledem k tomu, že jsem začal s touto knihovnou pracovat těsně po vydání její prvně verze, jsem narazil na řadu problémů \u2013 od nefunkční dokumentace, až po ne zcela smysluplná omezení. Media3 například neobsahuje podporu pro celoobrazovkové přehrávání, což bych řekl, že není žádný luxus. Vytvořil jsem proto vlastní nadstavbu zvanou Just Play, která použití knihovny Media3 značně ulehčuje, a přidává různé chybějící funkce."),
        // Monetizace (25)
        TechnologyEntity(language = "cs", categoryId = 25, name = "Google Play Billing library", description = ""),
        TechnologyEntity(language = "cs", categoryId = 25, name = "Stripe SDK", description = ""),
        // Nástroje (26)
        TechnologyEntity(language = "cs", categoryId = 26, name = "Gradle", description = "Někdo by si snad mohl říct, že Gradle je složitý, a plný různých hacků. Já to ale vidím jinak, neboť jsem zažil Maven. Kdo pracoval s Mavenem, jistě bude souhlasit, že Gradle je dar Boží, a o jeho mírných nedokonalostech se proto nediskutuje."),
        TechnologyEntity(language = "cs", categoryId = 26, name = "Google Play Console", description = "V Google Play Console mám zkušenosti s většinou úkolů \u2013 například vydávání nových verzí, správa seznamu testerů, testování plateb, nastavení deeplinků, správa app listingu, vyplnění app content, správa uživatelů a nastavení jejich přístupu ke statistikám, atd."),
        TechnologyEntity(language = "cs", categoryId = 26, name = "App Signing", description = ""),
        // Testování (27)
        TechnologyEntity(language = "cs", categoryId = 27, name = "Unit testy", description = "Pro unit testy používám standardní knihovnu JUnit."),
        TechnologyEntity(language = "cs", categoryId = 27, name = "Systémové testy", description = ""),
        TechnologyEntity(language = "cs", categoryId = 27, name = "Organizace interního testování", description = "Již vícekrát jsem organizoval manuální testování před vydáním zásadních novinek. Týkalo se to hlavně WattsUpu, ale minimálně jednou také Sanctuary First a CrossReach. Z mé strany se jednalo zejména o vytvoření formulářů přes Google Forms, které byly zaměřené na testování částí aplikace, které byly v dané verzi přidány, měněny, nebo do nich mohly být vneseny chyby."),
        // Telemetrie (28)
        TechnologyEntity(language = "cs", categoryId = 28, name = "Sentry", description = ""),
        TechnologyEntity(language = "cs", categoryId = 28, name = "Mixpanel", description = "Tento analytický nástroj jsem použil hned v několika projektech. Samozřejmě jsem vytvořil vlastní EventReporter pro zamezení vendor lock-inu, ale zatím Mixpanel vypadá dobře.")
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
        OtherSkillEntity(language = "en", categoryId = 3, name = "Jenkins", description = "We used Jenkins at Bach Systems for automated deployment of websites from the master branch in Git. Jenkins also ran tests before deployment on its own, so it served as a final check before going live."),
        OtherSkillEntity(language = "en", categoryId = 3, name = "Teamcity", description = ""),
        // Backend (4)
        OtherSkillEntity(language = "en", categoryId = 4, name = "Ktor", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "MariaDB", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "MySQL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "PostgreSQL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 4, name = "Elasticsearch", description = "I honestly don't love it that much due to its complexity, but once you reach the point where the database and search actually work, I must say it's a very reliable solution. While it's quite resource-intensive, its performance and hardware demands practically don't change with an increasing number of queries, which is quite useful. Theoretically, you can run this search engine in a distributed manner and scale the system's performance (though I don't have experience with that yet)."),
        // Server-side (5)
        OtherSkillEntity(language = "en", categoryId = 5, name = "Apache", description = ""),
        OtherSkillEntity(language = "en", categoryId = 5, name = "Nginx", description = ""),
        OtherSkillEntity(language = "en", categoryId = 5, name = "Supervisord", description = "Supervisord is a very simple and elegant process management tool. I successfully used it for deploying a microservice in the form of a fat JAR containing a REST API with its own embedded web server (using Ktor). Compared to the need to compile a WAR and the complex configuration of an application server, Supervisord seemed much better to me."),
        OtherSkillEntity(language = "en", categoryId = 5, name = "TLS/SSL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 5, name = "Wowza Streaming Engine", description = ""),
        // Infrastructure (6)
        OtherSkillEntity(language = "en", categoryId = 6, name = "AWS", description = ""),
        OtherSkillEntity(language = "en", categoryId = 6, name = "Hetzner", description = ""),
        OtherSkillEntity(language = "en", categoryId = 6, name = "Linode", description = ""),
        OtherSkillEntity(language = "en", categoryId = 6, name = "Digital Ocean", description = ""),
        // OS (7)
        OtherSkillEntity(language = "en", categoryId = 7, name = "GNU/Linux", description = "Proud Linux user since 2007! I started with Ubuntu, and around 2014 switched to Debian, which I use as my main system to this day. On the server side, in addition to Debian and Ubuntu, I also have experience with CentOS."),
        OtherSkillEntity(language = "en", categoryId = 7, name = "Android", description = ""),
        OtherSkillEntity(language = "en", categoryId = 7, name = "Mac OS", description = "I used Mac OS as my work system from 2022 to 2025. However, since the beginning of 2026, I've returned to Debian for work as well."),
        OtherSkillEntity(language = "en", categoryId = 7, name = "Windows", description = "I'm old enough to remember even Windows 95. Professionally, I dealt with basic administration of Windows XP and 7."),
        // Others (8)
        OtherSkillEntity(language = "en", categoryId = 8, name = "HTML/CSS", description = ""),
        OtherSkillEntity(language = "en", categoryId = 8, name = "SQL", description = ""),
        OtherSkillEntity(language = "en", categoryId = 8, name = "LaTeX", description = "I'm just an amateur when it comes to LaTeX, but for creating the PDF version of my CV, my humble skills were sufficient. :-)")
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
        OtherSkillEntity(language = "cs", categoryId = 11, name = "Jenkins", description = "Jenkins jsme používali v Bach systems pro automatizované nasazování webových stránek z master branche v Gitu. Jenkins také sám spouštěl testy před nasazením, a tak to byla taková poslední kontrola před samotným provozem."),
        OtherSkillEntity(language = "cs", categoryId = 11, name = "Teamcity", description = ""),
        // Backend (12)
        OtherSkillEntity(language = "cs", categoryId = 12, name = "Ktor", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "MariaDB", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "MySQL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "PostgreSQL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 12, name = "Elasticsearch", description = "Nemám jen upřímně až tak v lásce pro jeho složitost, avšak jakmile se člověk dostane do bodu, kdy databáze a hledání funguje, tak musím říct, že je to velice spolehlivé řešení. Sice je to docela hardwarově náročné, ale jeho výkonnost a HW nároky se s rostoucím počtem dotazů prakticky nemění, což je dost užitečné. Alespoň teoreticky pak lze provozovat tento search engine distribuovaně a škálovat tak výkonnost systému (s tím ale zatím nemám zkušenost)."),
        // Server-side (13)
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Apache", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Nginx", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Supervisord", description = "Supervisord je velmi jednoduchý a elegantní nástroj pro správu procesů. Velmi úspěšně jsem jej použil pro nasazení mikroslužby ve formě fatJaru obsahujícího REST API s vlastním embedded web serverem (pomocí Ktoru). Narozdíl od nutnosti kompilace waru, a složité konfigurace aplikačního serveru mi Supervisord přišel o mnoho lepší."),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "TLS/SSL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 13, name = "Wowza Streaming Engine", description = ""),
        // Infrastruktura (14)
        OtherSkillEntity(language = "cs", categoryId = 14, name = "AWS", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 14, name = "Hetzner", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 14, name = "Linode", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 14, name = "Digital Ocean", description = ""),
        // OS (15)
        OtherSkillEntity(language = "cs", categoryId = 15, name = "GNU/Linux", description = "Hrdý uživatel Linuxu od roku 2007! Začínal jsem s Ubuntu, a kolem roku 2014 přešel na Debian, který používám jako hlavní systém dodnes. Na serveru mám zkušenosti kromě Debianu a Ubuntu také s CentOS."),
        OtherSkillEntity(language = "cs", categoryId = 15, name = "Android", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 15, name = "Mac OS", description = "V letech 2022-2025 jsem Mac OS používal jako pracovní systém. Od počátku roku 2026 jsem se však vrátil zpět k Debianu i pro práci."),
        OtherSkillEntity(language = "cs", categoryId = 15, name = "Windows", description = "Jsem již starý, a tak pamatuji dokonce ještě Windows 95. Profesně jsem se pak věnoval základní správě Windows XP a 7."),
        // Ostatní (16)
        OtherSkillEntity(language = "cs", categoryId = 16, name = "HTML/CSS", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 16, name = "SQL", description = ""),
        OtherSkillEntity(language = "cs", categoryId = 16, name = "LaTeX", description = "V LaTeXu jsem sice pouhý amatér, ale například na tvorbu PDF verze mého CV moje skromné dovednosti stačily. :-)")
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
        InterestEntity(language = "en", name = "Photography", description = "I started taking photos around 2007. One might think that after all this time I must be a professional, but I'm still just an amateur. Actually, that was intentional \u2014 I didn't want to ruin this hobby. You can find some of my better photos here: https://gurushots.com/firzen/photos"),
        InterestEntity(language = "en", name = "Graphics", description = "I don't do graphics that often, but I occasionally create something, including commercially, as part of mobile app design. I mainly use Inkscape."),
        InterestEntity(language = "en", name = "Artificial Intelligence", description = ""),
        InterestEntity(language = "en", name = "Travel", description = "I spend most of my time sitting at a computer, and that's probably why I sometimes become a pilgrim and do something extreme. My record so far was walking 2,500 km from Edinburgh through Scotland, England, the Netherlands, Belgium, France and Spain, all the way to Santiago and Finisterre. During the journey I kept a journal here: https://www.facebook.com/pilgrimstemple")
    )

    private fun interestsCs() = listOf(
        InterestEntity(language = "cs", name = "Fotografie", description = "Fotit jsem začal už někdy kolem roku 2007. Jeden by si myslel, že za tu dobu už musím být profesionál, ale stále jsem pouze amatérem. Vlastně to bylo záměrné, protože jsem si nechtěl tento koníček zkazit. Několik povedených fotografií naleznete zde: https://gurushots.com/firzen/photos"),
        InterestEntity(language = "cs", name = "Počítačová grafika", description = "Grafice se nevěnuju až tak často, ale občas něco vytvořím, a to i komerečně, jako součást designu mobilních aplikací. Používám zejména Inkscape."),
        InterestEntity(language = "cs", name = "Umělá inteligence", description = ""),
        InterestEntity(language = "cs", name = "Cestování", description = "Většinu času sedím u počítače, a asi proto se občas stanu poutníkem, a podniknu něco extrémního. Můj dosavadní rekord bylo ujít 2500 km z Edinburghu přes Skotsko, Anglii, Nizozemí, Belgii, Francii a Španělsko, až do Santiaga a na Fisterru. Během cesty jsem psal zápisky zde: https://www.facebook.com/pilgrimstemple")
    )
}
