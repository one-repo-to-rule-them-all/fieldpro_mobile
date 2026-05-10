package app.fieldpro

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FieldProApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // Release tree (Crashlytics-forwarding) plants in Phase 3 once Firebase wires up.
    }
}
