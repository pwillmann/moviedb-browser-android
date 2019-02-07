import com.android.build.gradle.BaseExtension
import com.pwillmann.moviediscovery.Config


buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.0")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${com.pwillmann.moviediscovery.Config.Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${com.pwillmann.moviediscovery.Config.Versions.kotlin}")
        classpath("com.jakewharton:butterknife-gradle-plugin:10.0.0")
    }
}

plugins {
    id("com.gradle.build-scan") version "2.1"
    id("com.diffplug.gradle.spotless") version com.pwillmann.moviediscovery.Config.Versions.spotless
    id("io.gitlab.arturbosch.detekt") version com.pwillmann.moviediscovery.Config.Versions.detekt
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
    publishAlways()
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    project.pluginManager.apply("com.diffplug.gradle.spotless")
    project.pluginManager.apply("io.gitlab.arturbosch.detekt")

    spotless {
        java {
            // This is required otherwise the code in android modules isn"t picked up by spotless.
            target("**/*.java")
            trimTrailingWhitespace()
            removeUnusedImports()
            googleJavaFormat()
        }

        kotlin {
            target("**/*.kt")
            ktlint("0.29.0").userData(hashMapOf("indent_size" to "4", "android" to "true", "max_line_length" to "200"))
        }

        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint("0.29.0").userData(hashMapOf("indent_size" to "4", "android" to "true", "max_line_length" to "200"))
        }

        format("misc") {
            target("**/.gitignore", "**/*.gradle", "**/*.md", "**/*.sh", "**/*.yml")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    detekt {
        toolVersion = com.pwillmann.moviediscovery.Config.Versions.detekt
        config = files("$rootDir/default-detekt-config.yml")
        reports {
            xml.destination = file("${project.projectDir}/build/reports/detekt.xml")
            html.destination = file("${project.projectDir}/build/reports/detekt.html")
        }
        parallel = true
        filters = ".*build.*;.*/resources/.*;.*/tmp/.*"
        baseline = file("detekt-baseline.xml")
    }

    project.plugins.whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin, is com.android.build.gradle.LibraryPlugin -> {
                the<BaseExtension>().apply {
                    compileSdkVersion(Config.Android.compileSdk)

                    defaultConfig {
                        minSdkVersion(Config.Android.minSdk)
                        targetSdkVersion(Config.Android.targetSdk)
                    }
                    lintOptions.setLintConfig(rootProject.file("lint.xml"))

                }
            }

        }
    }
}

