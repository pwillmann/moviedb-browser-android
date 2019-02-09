import com.pwillmann.moviediscovery.Config

plugins {
    `java-library`
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:model"))
    implementation(Config.Libs.kotlin_stdlib)

    implementation(Config.Libs.rxJava)

    implementation(Config.Libs.retrofit)
    implementation(Config.Libs.retrofit_rxjava)
}
