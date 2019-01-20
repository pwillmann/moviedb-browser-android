import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(Config.Libs.kotlin_reflect)

    api(Config.Libs.timber)

    implementation(Config.Libs.androidx_appcompat)
    implementation(Config.Libs.material_design_components)
    implementation(Config.Libs.androidx_navigation_fragment_ktx)
    implementation(Config.Libs.androidx_navigation_ui_ktx)
    implementation(Config.Libs.androidx_recyclerview)
    implementation(Config.Libs.mvrx)
    implementation(Config.Libs.epoxy)
    kapt(Config.Libs.epoxy_processor)

    implementation(Config.Libs.glide)
    kapt(Config.Libs.glide_processor)

    implementation(Config.Libs.dagger)
    implementation(Config.Libs.dagger_android)
    implementation(Config.Libs.dagger_android_support)
    kapt(Config.Libs.dagger_processor)
}
