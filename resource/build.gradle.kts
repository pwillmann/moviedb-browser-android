import com.pwillmann.moviediscovery.Config

plugins {
    id("com.android.library")
}

dependencies {
    implementation(Config.Libs.androidx_appcompat)
    implementation(Config.Libs.material_design_components)
}
