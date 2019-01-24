import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

val TMDB_API_KEY: String by project

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)
    }
    lintOptions.setLintConfig(rootProject.file("lint.xml"))
    buildTypes.forEach {
        it.buildConfigField("String", "TMDB_API_KEY", TMDB_API_KEY)
    }
}

dependencies {
    implementation(project(":model"))
    implementation(project(":service:tmdb:tmdbcore"))
    implementation(Config.Libs.kotlin_stdlib)

    implementation(Config.Libs.rxJava)

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
