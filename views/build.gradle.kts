import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)
    }
    lintOptions.setLintConfig(rootProject.file("lint.xml"))
}

dependencies {
    implementation(project(":core"))
    implementation(project(":resources"))
    implementation(Config.Libs.kotlin_stdlib)
    implementation(Config.Libs.material_design_components)
    implementation(Config.Libs.androidx_appcompat) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.androidx_recyclerview) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.androidx_constraintlayout)
    implementation(Config.Libs.androidx_navigation_ui_ktx)

    implementation(Config.Libs.glide) {
        exclude(group = "com.android.support")
    }
    implementation(Config.Libs.glide_transformations)
    kapt(Config.Libs.glide_processor)

    implementation(Config.Libs.epoxy_core)
    kapt(Config.Libs.epoxy_processor)
}
