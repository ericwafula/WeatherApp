package tech.ericwathome.weatherapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initCrashHandler()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(
                object : Timber.DebugTree() {
                    override fun createStackElementTag(element: StackTraceElement): String {
                        return super.createStackElementTag(element) + ":" + element.lineNumber
                    }
                }
            )
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    private fun initCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler())
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            workManagerFactory()
            modules(
                
            )
        }
    }
}