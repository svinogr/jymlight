package info.upump.jymlight

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import info.upump.jymlight.service.StopWatchService

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createStopWatchNotificationChannel()
    }

    private fun createStopWatchNotificationChannel() {
        val channel = NotificationChannel(
            StopWatchService.CHANNEL_ID,
            getString(R.string.stopwatch_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
}
