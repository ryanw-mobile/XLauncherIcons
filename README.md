# Changing the Android App Icon Programmatically
<div style="text-align:center"><img src="Screenshot_20230802_212139_framed.png" /></div>


### Complementary Article 
This Sample App implements the approach mentioned in my Medium article at: https://medium.com/@callmeryan/changing-the-android-app-icon-programmatically-c913550330d



### The Underlying Working Principle
We can create multiple launcher icons by declaring `activity-alias` elements in` AndroidManifest.xml`. The effect of changing the app icon is achieved by disabling the current one and enabling a new one. While some say this does not work with things like crashlytics, deep link, etc., indeed this is how Reddit implements this feature in their Android App.


### Requirement
This Android App project was built using ***Android Studio Hedgehog | 2023.1.1 Beta 5***


### Credits
Since greg never uses Android, I put him on an Android App. However, please [subscribe to greg for $1](https://twitter.com/greg16676935420)
