import arch.Deps
import arch.ext.api

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"

        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    // external
    api(kotlin("stdlib"))
    api(Deps.Android.activityKtx)
    api(Deps.Android.constraintlayout)
    api(Deps.Android.coreKtx)
    api(Deps.Android.fragmentKtx)
    api(Deps.Android.liveDataKtx)
    api(Deps.Android.material)
    api(Deps.Android.viewbinding)
    api(Deps.Koin.all)
    api(Deps.Rx.all)
}
