import com.pwillmann.moviediscovery.Config
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

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
