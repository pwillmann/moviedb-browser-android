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
    implementation(project(":models"))
    implementation(Config.Libs.kotlin_stdlib)

    implementation(Config.Libs.rxJava)

    implementation(Config.Libs.retrofit_core)
    implementation(Config.Libs.retrofit_rxjava)
    implementation(Config.Libs.retrofit_moshi)

    implementation(Config.Libs.moshi_core)
    implementation(Config.Libs.moshi_kotlin)
    kapt(Config.Libs.moshi_processor)
}
