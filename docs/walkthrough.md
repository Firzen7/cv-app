# CV Android App — Walkthrough

## What was built

### Phase 1: Blank Screens + Bottom Navigation ✅
- **Hilt DI** setup ([CvApp.kt](file:///home/firzen/active_projects/2026/cv-android-app/cv-android-app/app/src/main/java/net/firzen/android/cv/CvApp.kt), Gradle plugins) — matching the `restaurant-compose-example` patterns
- **4-tab bottom navigation** (Profile, Experience, Skills, Projects) using Material 3 `NavigationBar`
- **Navigation system** using Compose [NavHost](file:///home/firzen/active_projects/2026/cv-android-app/cv-android-app/app/src/main/java/net/firzen/android/cv/navigation/CvNavHost.kt#23-50) with proper back stack management
- Verified on emulator (Android 15) — light and dark themes, portrait and landscape

### Phase 2: Room DB Structure ✅
- **13 entity tables** covering all CV sections from the LaTeX files
- **Foreign keys** with cascade delete for parent-child relationships (e.g., Project → Milestones)
- **CvDao** with typed insert and query methods, all `suspend` for coroutine usage
- **Hilt DI module** providing singleton database + automatic data seeding on first create
- **CvDataSeeder** with all CV data hardcoded from the [.tex](file:///home/firzen/active_projects/2026/cv-android-app/cv/cv_9_cs.tex) files

### Phase 3: Instrumented Tests ✅
- **11 test methods** covering every DB table
- Each test prints contents to Logcat (tag: `CvDbTest`) for visual verification
- [fullDatabaseSummary()](file:///home/firzen/active_projects/2026/cv-android-app/cv-android-app/app/src/androidTest/java/net/firzen/android/cv/CvDatabaseTest.kt#240-275) test prints a formatted table of all record counts

## Project structure

```
app/src/main/java/net/firzen/android/cv/
├── CvApp.kt                          # @HiltAndroidApp Application class
├── MainActivity.kt                    # Single-activity entry point with Scaffold
├── navigation/
│   ├── Screen.kt                      # Sealed class defining 4 nav destinations
│   ├── BottomNavBar.kt                # Material 3 bottom navigation composable
│   └── CvNavHost.kt                   # NavHost routing screens
├── presentation/
│   ├── profile/ProfileScreen.kt       # Blank placeholder (to be built out)
│   ├── experience/ExperienceScreen.kt
│   ├── skills/SkillsScreen.kt
│   └── projects/ProjectsScreen.kt
├── data/
│   ├── di/DatabaseModule.kt           # Hilt module providing DB + DAO
│   └── local/
│       ├── CvDatabase.kt             # Room Database (13 entities)
│       ├── CvDao.kt                  # Data Access Object
│       ├── CvDataSeeder.kt           # CV data pre-population
│       └── entities/                 # 13 entity files
│           ├── ProfileEntity.kt
│           ├── WorkExperienceEntity.kt
│           ├── ProjectEntity.kt
│           ├── ProjectMilestoneEntity.kt
│           ├── EducationEntity.kt
│           ├── ProgrammingLanguageEntity.kt
│           ├── TechnologyCategoryEntity.kt
│           ├── TechnologyEntity.kt
│           ├── OtherSkillCategoryEntity.kt
│           ├── OtherSkillEntity.kt
│           ├── LanguageEntity.kt
│           ├── PersonalityTraitEntity.kt
│           └── InterestEntity.kt
└── ui/theme/                          # (unchanged from Android Studio template)

app/src/androidTest/java/net/firzen/android/cv/
└── CvDatabaseTest.kt                  # 11 instrumented tests
```

## How to verify

1. **Gradle Sync** the project in Android Studio
2. **Build** → should compile without errors
3. **Run the app** → bottom navigation should still work as before
4. **Run tests**: Right-click [CvDatabaseTest.kt](file:///home/firzen/active_projects/2026/cv-android-app/cv-android-app/app/src/androidTest/java/net/firzen/android/cv/CvDatabaseTest.kt) → Run, or:
   ```
   ./gradlew :app:connectedDebugAndroidTest
   ```
5. **Check Logcat** → filter by tag `CvDbTest` to see all printed DB contents
