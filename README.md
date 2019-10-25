# KotlinBaseBoilerplate

This is a sample boilerplate project, that contains the following elements:

- Kotlin
- Retrofit
- Coroutines
- Glide
- AndroidX
- Android Navigation Components
- Model-View-ViewModel architectural pattern
- Firebase Crashlytics integration (WIP)
- Application build.gradle structure (WIP)

Currently this project is work-in-progress, using the [Weatherbit.io API](https://weatherbit.io/) as primary data source.
It used to connect to the WeatherStack API, but migrated due limitations.

This tutorial is based on the tutorial ["Forecast App - Android Kotlin MVVM Tutorial Course"](https://www.youtube.com/playlist?list=PLB6lc7nQ1n4jTLDyU2muTBo8xk0dg0D_w),
by [Reso Coder](https://github.com/ResoCoder), with a few modifications to fit the current requirements of this project.

### How to run/compile/etc:

1. After cloning the repository, create a "gradle.properties" file under the "app" directory, with the attribute "WEATHERBIT_API_KEY", and assign the api key provided bit the service (you have to register first)
2. Create a couple of keystore files, one for "debug" and another for "release" build flavors and put them on the project root folder. If you don't want to use them, you can comment the corresponding code in the "signingConfigs" on the app's build.gradle file
3. On the app's build.gradle file, **comment** the line "apply plugin: 'com.google.gms.google-services'" in order to run the project without Crashlytics.
In case you want to use Firebase's Crashlytics or another analytics, please fork the project and generate link it on your account and put the google-services.json file on a folder corresponding to each flavor - "release" and "debug". I'll be working on this later for anyone to use, thou. 

### TODO List:

1. Implement Unit System (Celsius, Farenheit, etc) properly (API call in Weatherbit.io case)
2. Package refactor with proper naming on some instances
3. Refactor data classes attributes
4. Finish Crashlytics configuration for each build flavor (only debug is working so far)
5. Add a splash screen and some nice UI stuff