package arch.ext

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType

fun Project.androidApplication(configure: AppExtension.() -> Unit) =
    extensions.configure(AppExtension::class, configure)

fun Project.androidLibrary(configure: LibraryExtension.() -> Unit) =
    extensions.configure(LibraryExtension::class, configure)

fun Project.archProjects() {
    repositories {
        google()
        mavenCentral()
    }

    // base config for android application modules
    plugins.withType<AppPlugin> {
        project.androidApplication {
            configureAndroid()

            buildTypes {
                getByName("debug") {
                    applicationIdSuffix = ".debug"
                }

                getByName("release") {
                    isShrinkResources = true
                }
            }
        }
    }

    // base config for android library modules
    plugins.withType<LibraryPlugin> {
        androidLibrary {
            configureAndroid()
        }
    }
}

fun Project.archTasks() {
    tasks.register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
