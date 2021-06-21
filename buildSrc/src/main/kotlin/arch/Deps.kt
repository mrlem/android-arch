package arch

object Deps {

    object Android {
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val viewbinding = "androidx.databinding:viewbinding:4.2.0"
        const val activityKtx = "androidx.activity:activity-ktx:1.2.3"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.3"
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        const val material = "com.google.android.material:material:1.3.0"
    }

    object Timber {
        val all = listOf(
            "com.jakewharton.timber:timber:4.7.1"
        )
    }

    object Koin {
        val all = listOf(
            "io.insert-koin:koin-android:${Versions.koin}",
            "io.insert-koin:koin-android-scope:${Versions.koin}",
            "io.insert-koin:koin-android-viewmodel:${Versions.koin}"
        )
    }

    object Retrofit {
        val all = listOf(
            "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}",
            "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}",
            "com.squareup.okhttp3:logging-interceptor:4.9.1"
        )
    }

    object Rx {
        val all = listOf(
            "io.reactivex.rxjava3:rxjava:3.0.12",
            "io.reactivex.rxjava3:rxandroid:3.0.0",
            "io.reactivex.rxjava3:rxkotlin:3.0.1"
        )
    }

}
