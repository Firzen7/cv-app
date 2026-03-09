# About

Due to the fact that future is here, and even [Linus Torvalds himself](https://github.com/torvalds/AudioNoise)(check bottom of his Readme) is experimenting with AI agents, I decided to give it a try. Also at the time of developing this project, I was preparing for an interview at pretty AI friendly company, and wanted to get some experience with it.

I have decided to use Google Antigravity with Claude Opus 4.6 model. Slight technical limitation was that Antigraviti does not support Android or even Kotlin. But that was actually ok, since I preferred to review all changes in Android Studio and in emulator anyway.

The whole process was pretty fascinating, and I definitely did not make it easy for Opus 4.6. My prompts were very vague (check [`ai_chats/`](ai_chats/)), and I let the agent to create initial visual designs of the app as well. Of course I had to correct many changes - for example, Opus initially generated all DAOs in one file, and UI logic was using them directly without any repository! But usually it was enough to explain necessary changes by another prompt.

Overall, this was an experiment inspired by thought, which could be described as: *"Hmm, I wonder how far I can get by just prompting Antigravity with my Android app idea."* And considering this all took me just 4 days to create, I would say that I got pretty far! I only wrote around 30 lines of code manually in this whole project. **That means less than 1% of this code was written by me!**

This sounds amazing, but of course comes with few dangers:
 1. I **do not** fully understand all the generated code, since it was **not** reviewed line-by-line yet. However, I **do** understand the overall structure, libraries and featurues used.
 2. This project is **not** intended to be taken as release ready! I would never release code that I do not fully understand to production.
 3. There are several issues with the code and structure as of now. For example, there are redundant DB entities, performance issues, and also probably somem bug related to `Flow` (which I do not understand yet). Also the comments in the code are currently very bad. I might clean 
 
**So please keep in mind that this is a demonstration of Google Antigravity and Claude Opus 4.6 possibilities, and not my own skills.**

Below is Readme created by Opus 4.6, which might be helpful:

# CV Android App

A native Android application that presents my professional CV as an interactive, beautifully designed mobile experience. Built with modern Android technologies and designed for demonstration during in-person job interviews.

> **This project was built collaboratively with an AI coding assistant.** The entire development story — from initial design concepts to the final polished app — is documented in the [`ai_chats/`](ai_chats/) directory.

---

## ✨ Features

- **4-tab bottom navigation** — Profile, Experience, Skills, Projects
- **Profile screen** — Photo, contact icons (phone, email, LinkedIn, GitHub, StackOverflow), languages, personality traits, and interests
- **Experience screen** — Timeline-style layout with circle markers and cards for each work experience
- **Skills screen** — Programming languages with dot-based skill indicators, collapsible technology categories with fade-out gradient previews
- **Projects screen** — List of 5 professional projects with icons, descriptions, and detail navigation
- **Project detail screen** — Full project description, milestone timeline, and links to Google Play / Amazon Store
- **Onboarding screen** — Shown on first launch; also accessible by tapping the profile photo
- **Clickable suggestion chips** — Chips with descriptions open a detail dialog with clickable hyperlinks
- **Bilingual support** — Full Czech and English localization, including all DB content; reactive locale switching without app restart
- **Dark mode** — Full dark theme support with subtle card outlines and adaptive chip styling

---

## 🏗️ Architecture

The app follows **Clean Architecture** with clear separation between layers:

```
app/src/main/java/net/firzen/android/cv/
├── data/                          # Data layer
│   ├── di/                        # Hilt DI modules
│   │   ├── DatabaseModule.kt      # Provides Room DB + DAO singletons
│   │   └── PreferencesModule.kt   # Provides SharedPreferences
│   ├── local/
│   │   ├── CvDatabase.kt          # Room Database (13 entities)
│   │   ├── CvDataSeeder.kt        # Bilingual CV data pre-population
│   │   ├── OnboardingPreferences.kt
│   │   ├── dao/                   # 11 individual DAO interfaces
│   │   └── entities/              # 13 Room entity classes
│   └── repository/
│       └── CvRepository.kt        # Maps entities → domain models
│
├── domain/                        # Domain layer
│   ├── model/                     # 11 plain domain model classes
│   ├── GetProfileDataUseCase.kt
│   ├── GetWorkExperiencesUseCase.kt
│   ├── GetSkillsDataUseCase.kt
│   ├── GetProjectsUseCase.kt
│   └── GetProjectByIdUseCase.kt
│
├── presentation/                  # Presentation layer
│   ├── models/                    # 5 ViewModels (reactive locale via flatMapLatest)
│   ├── screens/                   # 6 Compose screen composables
│   └── dialogs/                   # ChipDetailDialog with clickable URLs
│
├── navigation/                    # Navigation
│   ├── Screen.kt                  # Sealed class defining nav destinations
│   ├── BottomNavBar.kt            # Material 3 bottom navigation
│   └── CvNavHost.kt              # NavHost routing
│
├── CvApp.kt                      # @HiltAndroidApp with eager DB init
└── MainActivity.kt               # Single-activity entry point
```

---

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| **Language** | Kotlin |
| **UI** | Jetpack Compose, Material Design 3 |
| **Architecture** | MVVM, Clean Architecture, Use Cases |
| **Database** | Room (13 entities, 11 DAOs, reactive Flows) |
| **DI** | Hilt (Dagger) |
| **Async** | Kotlin Coroutines, StateFlow, Flow |
| **Navigation** | Navigation Compose |
| **Logging** | Timber |
| **Min SDK** | 26 (Android 8.0) |
| **Target SDK** | 36 |

---

## 📖 The Development Story

This app was built over several collaborative sessions with an AI assistant in early 2026. Each conversation is preserved in the [`ai_chats/`](ai_chats/) directory. Here's how the project evolved:

### Session 1: Design Concepts
> 📄 [`Designing CV Android App.md`](ai_chats/Designing%20CV%20Android%20App.md)

The journey started with the LaTeX source files of my PDF CV. The AI read both the English and Czech versions, identified the key sections (profile, work experience, education, skills, languages, personality, interests), and generated **4 design mockups** for the app. I reviewed them and provided feedback — preferring the clean card-based layout with a cerulean blue accent and a 4-tab bottom navigation.

### Session 2: From Blank Screens to a Full App
> 📄 [`Implementing CV App Screens.md`](ai_chats/Implementing%20CV%20App%20Screens.md)

This was the largest session, covering the bulk of the implementation:

1. **Scaffolding** — Created a blank project with Hilt DI, 4 blank screens, and bottom navigation. The AI took inspiration from my existing `restaurant-compose-example` project for architectural patterns.

2. **Room Database** — Designed and implemented **13 entity tables** with foreign keys and cascade deletes, covering all CV sections. Built a `CvDataSeeder` that populates all data from the LaTeX files. Wrote **11 instrumented tests** to verify the DB contents.

3. **Clean Architecture refactoring** — At my request, the AI split the monolithic `CvDao` into 11 individual DAOs, introduced a `CvRepository` layer, created domain model classes (so Entity objects never leak into the presentation layer), and added Use Cases following the pattern from my reference project.

4. **Profile Screen** — Card-based layout with a real photo, contact icon row (with a dropdown for two GitHub profiles), and chips for languages, personality, and interests. Added `@Preview` support with sample data.

5. **Experience Screen** — Timeline-style layout using Canvas-drawn connector lines. Solved the `fillMaxHeight()` measurement issue with `IntrinsicSize.Min`.

6. **Skills Screen** — Initially built with individual chips, then redesigned at my request to use **collapsible categories** with a fade-out gradient preview. The AI implemented overflow detection to only show the expand arrow when chips actually overflow the row.

7. **Projects Screen** — Simple 5-row list with app icons (two of which the AI generated — a Sanctuary First for Alexa icon and a Sanctus Tools icon). Tapping navigates to the detail screen.

8. **Project Detail Screen** — Header card with project info, milestone timeline matching the Experience Screen pattern, and functional Google Play / Amazon Store buttons.

Throughout this session, I made manual refinements: reorganizing package structure, adjusting comment styles, replacing `Log` with Timber, and moving hardcoded strings to `strings.xml` for localization readiness.

### Session 3: Locale, Flows, and Bilingual Data
> 📄 [`Reactive Locale Switching.md`](ai_chats/Reactive%20Locale%20Switching.md)

This session focused on making the app truly bilingual:

1. **Profile photo** — Replaced the initials placeholder with my actual photo.

2. **Room Flow migration** — Converted all DAOs from one-shot `suspend` queries to reactive `Flow` return types, fixing a race condition where screens showed empty data on first launch because the DB hadn't finished seeding yet.

3. **Onboarding Screen** — Created a welcome screen shown on first launch (persisted via SharedPreferences), with proper edge-to-edge layout handling via `systemBarsPadding()`.

4. **Czech strings.xml** — Full Czech translation of all UI strings.

5. **Bilingual database** — Added a `language` column to every entity, doubled the seeded data with Czech translations, and threaded locale through all layers (DAOs → Repository → Use Cases → ViewModels).

6. **App icon exploration** — Generated multiple icon concepts. After several iterations (from corporate to personal), I ended up adding the icon manually.

7. **Reactive locale switching** — Instead of restarting the Activity on language change (the simpler approach), I chose the more elegant solution: ViewModels use `MutableStateFlow<String>` for the locale + `flatMapLatest` to re-subscribe to use cases when the locale changes, triggered by `LifecycleEventEffect(ON_RESUME)` in each screen.

### Session 4: Polish and Details
> 📄 [`Refining Chip Colors.md`](ai_chats/Refining%20Chip%20Colors.md)

The final session focused on polish and new features:

1. **Persistent onboarding** — Made the onboarding screen appear only once after install (persisted to SharedPreferences), not on every cold start.

2. **Clickable chips with descriptions** — Added a nullable `description` column to 4 entities, created bilingual descriptions (via CSV files I prepared manually), and built a reusable `ChipDetailDialog` with clickable URL detection. Chips with descriptions are visually highlighted with a filled style.

3. **Dark mode card outlines** — Added subtle `outlineVariant` borders to all cards, visible only in dark mode.

4. **Chip color refinements** — Iterated on chip styling for both light and dark modes: bright text for highlighted chips in dark mode, original subtle text in light mode, consistent outline colors matching cards.

5. **Clickable hyperlinks in dialogs** — Made URLs in chip descriptions open in the external browser.

6. **Onboarding via photo tap** — Added navigation to the onboarding screen when tapping the profile photo, with auto-hiding bottom nav bar.

7. **Scrollable dialog content** — Made the `ChipDetailDialog` body scrollable for long descriptions.

---

## 🚀 How to Build

1. Open the `cv-android-app/` directory in **Android Studio**
2. Perform **Gradle Sync**
3. **Run** on an emulator or device (min SDK 26)

### Running Tests

```bash
./gradlew :app:connectedDebugAndroidTest
```

Check Logcat with tag `CvDbTest` to see DB contents.

---

## 📁 Repository Structure

```
├── ai_chats/               # Full AI conversation logs (4 sessions)
├── cv/                      # LaTeX source files of the CV (EN + CS)
├── cv-android-app/          # Android Studio project
├── designs/                 # Screen mockup images (6 screens)
├── docs/                    # Walkthrough documentation
├── images/                  # Project icons and assets
├── restaurant-compose-example/  # Reference project for architecture patterns
└── texts/                   # Description CSVs for chip dialogs
```

---

## 📝 License

This is a personal portfolio project. All CV content is specific to the author.
