# AI Agent Instructions

## Project Overview
**XLauncherIcons** is an Android application that demonstrates how to programmatically change the app's launcher icon without a full re-install. This is achieved using `activity-alias` declarations in the `AndroidManifest.xml`.

### Key Technologies
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Build System:** Gradle (Kotlin DSL) with Version Catalog
- **Static Analysis:** Detekt, Kotlinter (ktlint)
- **Target SDK:** 36 (Android 15+)
- **Min SDK:** 26 (Android 8.0+)

### Core Architecture & Logic
The app defines multiple `<activity-alias>` elements in `AndroidManifest.xml`, each targeting the same `MainActivity` but with different `android:icon` and `android:label` attributes.

The core logic is structured following MVVM principles:
- **IconManager**: Encapsulates `PackageManager` logic for enabling/disabling `activity-alias` components and querying the active icon.
- **MainViewModel**: Manages the UI state, specifically the currently active icon component name, and coordinates icon switches via the `IconManager`.
- **MainActivity**: Serves as the entry point, initializes the `ViewModel`, and collects its state.
- **MainScreen**: A stateless Composable that renders the UI based on the state provided by `MainActivity`.

When a user selects a new icon:
1. The `MainViewModel` calls `IconManager.setIcon()`.
2. The `IconManager` disables all other aliases and enables the selected one using `PackageManager.setComponentEnabledSetting`.
3. The UI state is updated, providing visual feedback (a border) around the selected icon.

## Building and Running
The project uses the Gradle wrapper (`./gradlew`).

### Key Commands
- **Assemble Debug APK:** `./gradlew assembleDebug`
- **Install Debug APK:** `./gradlew installDebug`
- **Run Unit Tests:** `./gradlew test`
- **Run Instrumented Tests:** `./gradlew connectedAndroidTest` (requires an emulator/device)
- **Static Analysis (Detekt):** `./gradlew detekt`
- **Lint Kotlin:** `./gradlew lintKotlin`
- **Format Kotlin:** `./gradlew formatKotlin`

### CI/CD
The project uses GitHub Actions for CI (defined in `.github/workflows/main_build.yml`). It includes automated building, testing, and linting.

## Development Conventions

### Coding Style & Formatting
- **Kotlin Style:** The project follows standard Kotlin coding conventions, enforced by **Kotlinter** (ktlint).
- **Auto-formatting:** The `preBuild` task is configured to depend on `formatKotlin`, ensuring code is formatted before every build.
- **Static Analysis:** **Detekt** is used for code smell detection and is part of the `check` task.

### Project Structure
- `app/src/main/java/com/rwmobi/xlaunchericons/`: Contains the core application logic and UI.
- `app/src/main/res/`: Contains Android resources, including the various icons used for the launcher.
- `gradle/libs.versions.toml`: Centralized dependency management.

### Testing Practices
- **Unit Tests**: Located in `app/src/test`. These tests verify the business logic in `MainViewModel` using **MockK** and **kotlinx-coroutines-test**.
- **Instrumented Tests**: Located in `app/src/androidTest`, primarily testing UI interactions with Compose using the **Robot Pattern**.
- **Managed Devices:** Gradle Managed Devices are configured for CI testing (e.g., `pixel2Api35`).
