import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":core:dagger"))
    implementation(project(":core:kotterknife"))
    implementation(project(":core:model"))
    implementation(project(":lib:arch"))
    implementation(project(":lib:resource"))
    implementation(project(":lib:service:tmdb:tmdbcore"))
    implementation(project(":lib:epoxy"))
    implementation(project(":feature:detail"))

    implementation(Config.Libs.androidx_appcompat) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.material_design_components)
    implementation(Config.Libs.androidx_recyclerview) { exclude(group = "android.arch.lifecycle") }
    implementation(Config.Libs.androidx_constraintlayout)
    implementation(Config.Libs.androidx_lifecycle_extensions)
    implementation(Config.Libs.androidx_navigation_fragment_ktx)
    implementation(Config.Libs.androidx_navigation_ui_ktx)

    implementation(Config.Libs.timber)

    implementation(Config.Libs.glide) {
        exclude(group = "com.android.support")
    }

    implementation(Config.Libs.mvrx)
    implementation(Config.Libs.epoxy) { exclude(group = "com.android.support") }
    kapt(Config.Libs.epoxy_processor)

    // Dagger
    implementation(Config.Libs.dagger)
    kapt(Config.Libs.dagger_processor)
    implementation(Config.Libs.dagger_android)
    implementation(Config.Libs.dagger_android_support)
    kapt(Config.Libs.dagger_android_processor)
}
