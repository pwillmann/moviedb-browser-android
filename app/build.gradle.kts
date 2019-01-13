import com.pwillmann.moviediscovery.Config
import com.pwillmann.moviediscovery.Config.Android.applicationId
import com.pwillmann.moviediscovery.Config.Android.versionCode
import com.pwillmann.moviediscovery.Config.Android.versionName

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

androidExtensions {
    isExperimental = true
}

val VERSION_CODE: String by project
val VERSION_NAME: String by project

android {
    compileSdkVersion(Config.Android.compileSdk)
    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Android.minSdk)
        targetSdkVersion(Config.Android.targetSdk)
        versionCode = Integer.parseInt(VERSION_CODE)
        versionName = VERSION_NAME
    }
    lintOptions.setLintConfig(rootProject.file("lint.xml"))
}

dependencies {
    implementation(project(":core"))
    implementation(project(":model"))
    implementation(project(":resource"))
    implementation(project(":service:remote"))
    implementation(project(":view"))
    implementation(project(":feature:browser"))
    implementation(project(":feature:detail"))
    implementation(Config.Libs.kotlin_stdlib)

    implementation(Config.Libs.androidx_appcompat) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.androidx_recyclerview) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.androidx_constraintlayout)

    implementation(Config.Libs.mvrx)
    implementation(Config.Libs.epoxy) { exclude(group = "com.android.support") }
    kapt(Config.Libs.epoxy_processor)

    implementation(Config.Libs.rxJava)
    implementation(Config.Libs.rxAndroid)

    implementation(Config.Libs.retrofit)
    implementation(Config.Libs.retrofit_rxjava)
    implementation(Config.Libs.retrofit_moshi)
    implementation(Config.Libs.moshi)
    implementation(Config.Libs.moshi_kotlin)
    kapt(Config.Libs.moshi_processor)

    implementation(Config.Libs.koin_android_viewmodel)
}
