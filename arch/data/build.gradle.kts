import arch.Deps
import arch.ext.api

plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {
    // external
    api(kotlin("stdlib"))
    api(Deps.Rx.all)
    api(Deps.Retrofit.all)
    api(Deps.Timber.all)
}
