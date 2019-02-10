import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(Config.Libs.kotlin_reflect)

    // Logging
    implementation(Config.Libs.timber)

    implementation(Config.Libs.androidx_navigation_fragment_ktx)
    implementation(Config.Libs.androidx_navigation_ui_ktx)

    implementation(Config.Libs.glide)
    kapt(Config.Libs.glide_processor)

    implementation(Config.Libs.dagger)
    implementation(Config.Libs.dagger_android)
    implementation(Config.Libs.dagger_android_support)
    kapt(Config.Libs.dagger_processor)
}
