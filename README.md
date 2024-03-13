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
