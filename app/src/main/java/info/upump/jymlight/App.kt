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
        createStopWatchWatchNotificationChannel()
    }

    private fun createStopWatchNotificationChannel() {
        val channel = NotificationChannel(
            StopWatchService.CHANNEL_ID,
            getString(R.string.stopwatch_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    // Канал для минутных уведомлений на часы: должен быть с вибрацией,
    // иначе Xiaomi часы не завибрируют при дублировании уведомления.
    private fun createStopWatchWatchNotificationChannel() {
        val channel = NotificationChannel(
            StopWatchService.WATCH_CHANNEL_ID,
            getString(R.string.stopwatch_watch_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(0, 300, 200, 300)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
}
