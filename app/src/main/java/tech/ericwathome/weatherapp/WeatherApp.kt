package tech.ericwathome.weatherapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import tech.ericwathome.weatherapp.di.applicationModule
import tech.ericwathome.weatherapp.di.dataModule
import timber.log.Timber

class WeatherApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

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
                },
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
                applicationModule,
                dataModule,
            )
        }
    }
}