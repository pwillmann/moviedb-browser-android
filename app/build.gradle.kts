import com.pwillmann.moviediscovery.Config
import com.pwillmann.moviediscovery.Config.Android.applicationId
import com.pwillmann.moviediscovery.Config.Android.versionCode
import com.pwillmann.moviediscovery.Config.Android.versionName

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

androidExtensions {
    isExperimental = true
}

val VERSION_CODE: String by project
val VERSION_NAME: String by project

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)
        versionCode = Integer.parseInt(VERSION_CODE)
        versionName = VERSION_NAME
    }
    lintOptions.setLintConfig(rootProject.file("lint.xml"))
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core"))
    implementation(project(":model"))
    implementation(project(":resource"))
    implementation(project(":service:tmdb:tmdbcore"))
    implementation(project(":service:tmdb:tmdbmock"))
    implementation(project(":service:tmdb:tmdbremote"))
    implementation(project(":view"))
    implementation(project(":feature:browser"))
    implementation(project(":feature:detail"))
    implementation(Config.Libs.kotlin_stdlib)

    // AndroidX
    implementation(Config.Libs.androidx_appcompat) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.androidx_recyclerview) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.androidx_constraintlayout)
    implementation(Config.Libs.androidx_navigation_fragment_ktx)
    implementation(Config.Libs.androidx_navigation_ui_ktx)

    // AirBnB
    implementation(Config.Libs.mvrx)
    implementation(Config.Libs.epoxy) { exclude(group = "com.android.support") }
    kapt(Config.Libs.epoxy_processor)

    // ReactiveX
    implementation(Config.Libs.rxJava)
    implementation(Config.Libs.rxAndroid)

    // Retrofit, Moshi, Networking
    implementation(Config.Libs.retrofit)
    implementation(Config.Libs.retrofit_rxjava)
    implementation(Config.Libs.retrofit_moshi)
    implementation(Config.Libs.moshi)
    implementation(Config.Libs.moshi_kotlin)
    kapt(Config.Libs.moshi_processor)

    // Dagger
    implementation(Config.Libs.dagger)
    kapt(Config.Libs.dagger_processor)
    implementation(Config.Libs.dagger_android)
    implementation(Config.Libs.dagger_android_support)
    kapt(Config.Libs.dagger_android_processor)
}
