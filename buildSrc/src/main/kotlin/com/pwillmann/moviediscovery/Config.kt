@file:Suppress("unused")

package com.pwillmann.moviediscovery

object Config {

    object Versions {
        // Tooling
        val kotlin = "1.3.20"
        val timber = "4.7.1"
        val spotless = "3.16.0"

        // AndroidX / Google
        val appcompat = "1.1.0-alpha01"
        val constraint = "2.0.0-alpha3"
        val navigation = "1.0.0-alpha09"
        val lifecycle = "2.0.0-beta01"
        val ktxCore = "1.1.0-alpha03"
        val recyclerview = "1.1.0-alpha01"
        val material = "1.1.0-alpha02"

        // DI
        val assistedinject = "0.3.2"
        val dagger = "2.19"

        // Networking
        val retrofit = "2.5.0"
        val moshi = "1.8.0"

        // AirBnB
        val epoxy = "3.2.0"
        val mvrx = "0.7.2"

        // ReactiveX
        val rxJava = "2.2.5"
        val rxAndroid = "2.1.0"
        val rxBinding = "3.0.0-alpha2"


        val glide = "4.8.0"

    }

    object Android {
        val applicationId = "com.pwillmann.moviediscovery"
        val compileSdk = 28
        val minSdk = 21
        val targetSdk = 28
        val versionCode = 1
        val versionName = "1.0"
    }

    object Libs {
        // Tooling
        val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
        val timber = "com.jakewharton.timber:timber:${Versions.timber}"

        // AndroidX
        val androidx_core = "androidx.core:core-ktx:${Versions.ktxCore}"
        val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
        val androidx_lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        val androidx_navigation_fragment_ktx = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigation}"
        val androidx_navigation_ui_ktx = "android.arch.navigation:navigation-ui-ktx:${Versions.navigation}"
        val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

        // Material Design Components
        val material_design_components = "com.google.android.material:material:${Versions.material}"

        // AirBnBs MvRx + Epoxy
        val mvrx = "com.airbnb.android:mvrx:${Versions.mvrx}"
        val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
        val epoxy_processor = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"

        // Retrofit, Moshi
        val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        val retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
        val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        val moshi_adapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
        val moshi_processor = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

        // RxJava
        val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
        val rxbinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"

        // Dagger
        val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        val dagger_processor = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
        val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        val dagger_android_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
        val assistedinject = "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.assistedinject}"
        val assistedinject_processor = "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.assistedinject}"


        // Glide
        val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        val glide_processor = "com.github.bumptech.glide:compiler:${Versions.glide}"
        val glide_transformations = "jp.wasabeef:glide-transformations:3.3.0"


    }

}
