# Changing the Android App Icon Programmatically ![Gradle Build](https://github.com/ryanw-mobile/XLauncherIcons/actions/workflows/main_build.yml/badge.svg)

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

This project was configured to build using Android Studio Iguana | 2023.2.1. You will need to have
Java 17 to build the project.

Alternatively, you can find the ready-to-install APKs and App Bundles under
the [release section](https://github.com/ryanw-mobile/XLauncherIcons/releases).

### Credits

Since greg never uses Android, I put him on an Android App. However,
please [subscribe to greg for $1](https://twitter.com/greg16676935420)

### Technical details

### Dependencies

* [AndroidX Core KTX](https://developer.android.com/jetpack/androidx/releases/core) - Apache 2.0 - Extensions to Java APIs for Android development
* [JUnit](https://junit.org/junit5/) - EPL 2.0 - A simple framework to write repeatable tests
* [AndroidX Test Ext JUnit](https://developer.android.com/jetpack/androidx/releases/test) - Apache 2.0 - Extensions for Android testing
* [AndroidX Espresso](https://developer.android.com/training/testing/espresso) - Apache 2.0 - UI testing framework
* [AndroidX Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Apache 2.0 - Lifecycles-aware components
* [AndroidX Activity Compose](https://developer.android.com/jetpack/androidx/releases/activity) - Apache 2.0 - Jetpack Compose integration with Activity
* [Jetpack Compose BOM](https://developer.android.com/jetpack/compose/bom) - Apache 2.0 - Bill of Materials for Jetpack Compose
* [AndroidX Compose UI](https://developer.android.com/jetpack/androidx/releases/compose-ui) - Apache 2.0 - UI components for Jetpack Compose
* [AndroidX Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3) - Apache 2.0 - Material Design components for Jetpack Compose

### Plugins

* [Android Application Plugin](https://developer.android.com/studio/build/gradle-plugin-3-0-0-migration) - Google - Plugin for building Android applications
* [Jetbrains Kotlin Android Plugin](https://kotlinlang.org/docs/gradle.html) - JetBrains - Plugin for Kotlin Android projects
* [Ktlint Plugin](https://github.com/JLLeitschuh/ktlint-gradle) - JLLeitschuh - Plugin for Kotlin linter
* [Compose Compiler Plugin](https://developer.android.com/jetpack/compose) - JetBrains - Plugin for Jetpack Compose
