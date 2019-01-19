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
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version "3.16.0"
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

