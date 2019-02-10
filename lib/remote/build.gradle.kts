import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

dependencies {
    implementation(Config.Libs.kotlin_stdlib)
    implementation(Config.Libs.timber)

    implementation(Config.Libs.rxJava)

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
