import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    implementation(Config.Libs.kotlin_reflect)
    implementation(Config.Libs.androidx_appcompat)
    implementation(Config.Libs.androidx_recyclerview)
}
