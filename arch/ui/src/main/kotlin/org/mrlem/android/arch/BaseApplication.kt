package org.mrlem.android.arch

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.util.Log
import androidx.annotation.CallSuper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Base application to be extended by the application class.
 *
 * Provides:
 * - easy dependency injection initialization (just provide the koin modules)
 * - strict mode setup in debug builds
 * - timber logging
 */
abstract class BaseApplication : Application() {

    protected abstract val modules: List<Module>
    protected open val strictModeDisabled = false

    @CallSuper
    override fun onCreate() {
        super.onCreate()

        initKoin()
        initStrictMode()
        initTimber()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(modules)
        }
    }

    private fun initStrictMode() {
        if (!strictModeDisabled && BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(object : DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                        return
                    }

                    // only log important events
                    super.log(priority, tag, message, t)
                }
            })
        }
    }

}
