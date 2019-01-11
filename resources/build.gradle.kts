import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)
    }
}

dependencies {
    implementation(Config.Libs.androidx_appcompat)
    implementation(Config.Libs.material_design_components)
}
