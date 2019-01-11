import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

dependencies {
    implementation(project(":resource"))
    implementation(Config.Libs.androidx_appcompat)
    implementation(Config.Libs.material_design_components)
    implementation(Config.Libs.androidx_navigation_fragment_ktx)
    implementation(Config.Libs.androidx_navigation_ui_ktx)
    implementation(Config.Libs.mvrx)
    implementation(Config.Libs.epoxy)
    kapt(Config.Libs.epoxy_processor)

    implementation(Config.Libs.glide)
    kapt(Config.Libs.glide_processor)
}
