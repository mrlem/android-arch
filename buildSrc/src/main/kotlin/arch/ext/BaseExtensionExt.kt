package arch.ext

import arch.Sources
import arch.Versions
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion

fun BaseExtension.configureAndroid() {
    setCompileSdkVersion(Versions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures.viewBinding = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            java.setSrcDirs(
                listOf(
                    Sources.Main.KOTLIN,
                    Sources.Main.RESOURCES
                )
            )
        }
        getByName("test") {
            java.setSrcDirs(
                listOf(
                    Sources.Test.KOTLIN,
                    Sources.Test.RESOURCES
                )
            )
        }
    }
}
