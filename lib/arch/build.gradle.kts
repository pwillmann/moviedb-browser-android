import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:kotterknife"))

    implementation(Config.Libs.timber)

    implementation(Config.Libs.androidx_appcompat)
    implementation(Config.Libs.androidx_navigation_fragment_ktx)
    implementation(Config.Libs.androidx_navigation_ui_ktx)
    implementation(Config.Libs.androidx_recyclerview)
    implementation(Config.Libs.mvrx)
    implementation(Config.Libs.epoxy)
    kapt(Config.Libs.epoxy_processor)

    implementation(Config.Libs.dagger)
    implementation(Config.Libs.dagger_android)
    implementation(Config.Libs.dagger_android_support)
    kapt(Config.Libs.dagger_processor)
}
