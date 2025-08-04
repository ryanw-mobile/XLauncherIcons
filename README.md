# Changing the Android App Icon Programmatically <br/>![Gradle Build](https://github.com/ryanw-mobile/XLauncherIcons/actions/workflows/main_build.yml/badge.svg) [![Renovate enabled](https://img.shields.io/badge/renovate-enabled-brightgreen.svg)](https://renovatebot.com/)

<div style="text-align:center"><img src="Screenshot_20230802_212139_framed.png" /></div>

### Complementary Article

This Sample App implements the approach mentioned in my Medium article
at: https://medium.com/@callmeryan/changing-the-android-app-icon-programmatically-c913550330d

### The Underlying Working Principle

We can create multiple launcher icons by declaring `activity-alias` elements
in` AndroidManifest.xml`. The effect of changing the app icon is achieved by disabling the current
one and enabling a new one. While some say this does not work with things like crashlytics, deep
link, etc., indeed this is how Reddit implements this feature in their Android App.

### Let's download and run it!

This project was configured to build using Android Studio Narwhal Feature Drop | 2025.1.2 RC 1. You will need to have
Java 17 to build the project.

Alternatively, you can find the ready-to-install APKs and App Bundles under
the [release section](https://github.com/ryanw-mobile/XLauncherIcons/releases).

### Credits

Since greg never uses Android, I put him on an Android App. However,
please [subscribe to greg for just 10¢ a day](https://x.com/greg16676935420)

### Technical details

### Dependencies

* [AndroidX Core KTX](https://developer.android.com/jetpack/androidx/releases/core) - Apache 2.0 - Kotlin extensions for core Android libraries
* [JUnit](https://junit.org/) - EPL 1.0 - Unit testing framework for Java
* [AndroidX Test Ext JUnit](https://developer.android.com/jetpack/androidx/releases/test) - Apache 2.0 - JUnit extensions for AndroidX tests
* [AndroidX Espresso](https://developer.android.com/training/testing/espresso) - Apache 2.0 - UI testing framework for Android
* [AndroidX Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Apache 2.0 - Lifecycle-aware components
* [AndroidX Activity Compose](https://developer.android.com/jetpack/androidx/releases/activity) - Apache 2.0 - Jetpack Compose integration with Activity
* [Jetpack Compose BOM](https://developer.android.com/jetpack/compose/bom) - Apache 2.0 - Compose Bill of Materials for consistent versioning
* [Jetpack Compose UI](https://developer.android.com/jetpack/compose/ui) - Apache 2.0 - Compose UI core components
* [Jetpack Compose Graphics](https://developer.android.com/jetpack/compose/graphics) - Apache 2.0 - Compose graphics utilities
* [Jetpack Compose Tooling](https://developer.android.com/jetpack/compose/tooling) - Apache 2.0 - Tooling support for Compose UI
* [Jetpack Compose Tooling Preview](https://developer.android.com/jetpack/compose/tooling) - Apache 2.0 - UI previews for Compose
* [Jetpack Compose Test Manifest](https://developer.android.com/jetpack/compose/testing) - Apache 2.0 - Compose test manifest configuration
* [Jetpack Compose Test JUnit4](https://developer.android.com/jetpack/compose/testing) - Apache 2.0 - Compose test support with JUnit4
* [Jetpack Compose Material3](https://developer.android.com/jetpack/compose/material3) - Apache 2.0 - Material Design 3 components for Compose
* [AndroidX Test Rules](https://developer.android.com/jetpack/androidx/releases/test) - Apache 2.0 - AndroidX testing rules

### Plugins

* [Android Application Plugin](https://developer.android.com/studio/build/gradle-plugin-3-0-0-migration) - Google - Plugin for building Android applications
* [Kotlin Android Plugin](https://kotlinlang.org/docs/gradle.html) - JetBrains - Plugin for Kotlin Android development
* [Compose Compiler Plugin](https://developer.android.com/jetpack/compose) - JetBrains - Plugin for Jetpack Compose
* [Detekt Plugin](https://detekt.dev/) - Artur Bosch - A static code analysis tool for Kotlin projects
* [Kotlinter Plugin](https://github.com/jeremymailen/kotlinter-gradle) - Jeremy Mailen - Kotlin linter based on ktlint
