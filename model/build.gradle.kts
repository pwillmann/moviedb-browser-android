import com.pwillmann.moviediscovery.Config

plugins {
    `java-library`
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(Config.Libs.moshi)
    implementation(Config.Libs.moshi_kotlin)
    kapt(Config.Libs.moshi_processor)
}
