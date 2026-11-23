package info.upump.jymlight.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import dagger.hilt.android.AndroidEntryPoint
import info.upump.jymlight.MainActivityCompose
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.screenscomponents.screen.StopWatchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

/**
 * Foreground service секундомера.
 * Логика таймера живёт здесь, а не во ViewModel — поэтому таймер
 * продолжает идти при свёрнутом приложении и выключенном экране.
 * Уведомление показывает текущее время и кнопки паузы/продолжения/остановки.
 */
@AndroidEntryPoint
class StopWatchService : Service() {

    @Inject
    lateinit var holder: StopWatchStateHolder

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var timerJob: Job? = null

    private var lastTimeStamp = 0L
    private var timeMile = 0L
    private var lastNotifiedSecond = ""

    private var wakeLock: PowerManager.WakeLock? = null

    private val watchNotificationIdStack = mutableListOf<Int>()
    private var watchNotificationCounter = 0

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                startAsForeground()
                startTimer()
            }
            ACTION_PAUSE -> pauseTimer()
            ACTION_RESUME -> startTimer()
            ACTION_STOP -> stopTimerAndService()
            ACTION_TOGGLE_SOUND ->
                holder.setSoundEveryTime(!holder.isSoundEveryTime.value)
            null -> {
                // Перезапуск системой: если секундомер не идёт — сервис больше не нужен.
                if (holder.status.value == StopWatchState.STOP) {
                    stopSelf()
                } else {
                    startAsForeground()
                }
            }
        }
        return START_STICKY
    }

    private fun startAsForeground() {
        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            buildNotification(),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            }
        )
    }

    private fun startTimer() {
        if (holder.status.value == StopWatchState.RESUME) return
        holder.setStatus(StopWatchState.RESUME)
        acquireWakeLock()
        timerJob?.cancel()
        timerJob = serviceScope.launch {
            var chet = 0L
            while (holder.status.value == StopWatchState.RESUME) {
                lastTimeStamp = System.currentTimeMillis()
                delay(10L)
                val delta = System.currentTimeMillis() - lastTimeStamp
                timeMile += delta
                chet += delta
                val formatted = formatTime(timeMile)
                holder.setTime(formatted)
                updateNotificationTime(formatted)

                if (chet >= 60000) {
                    chet = 0
                    if (holder.isSoundEveryTime.value) playMinuteSound()
                    if (holder.isVibrateWatchEveryMinute.value) notifyWatch(formatted)
                }
            }
        }
        updateNotification()
    }

    private fun pauseTimer() {
        holder.setStatus(StopWatchState.PAUSE)
        timerJob?.cancel()
        releaseWakeLock()
        updateNotification()
    }

    private fun stopTimerAndService() {
        timerJob?.cancel()
        releaseWakeLock()
        cancelWatchNotifications()
        timeMile = 0L
        lastTimeStamp = 0L
        holder.reset()
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun playMinuteSound() {
        runCatching {
            MediaPlayer.create(this, R.raw.everytimesound)?.apply {
                setOnCompletionListener { it.release() }
                start()
            }
        }
    }

    // Каждую минуту постим новое короткое уведомление со временем таймера —
    // телефон дублирует его на часы (Xiaomi) и они вибрируют.
    // Обновление одного и того же уведомления вибрации на часах не вызывает,
    // поэтому каждую минуту новый id, а прошлое уведомление отменяем.
    private fun notifyWatch(formatted: String) {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) return
        val notificationManager = NotificationManagerCompat.from(this)
        watchNotificationIdStack.lastOrNull()?.let { notificationManager.cancel(it) }
        val id = WATCH_NOTIFICATION_ID_BASE + watchNotificationCounter++
        watchNotificationIdStack.add(id)
        runCatching {
            val notification = NotificationCompat.Builder(this, WATCH_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_btn_play_24)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(formatted.take(5))
                // HIGH + звук: некоторые часы/прошивки (Xiaomi, MIUI) передают
                // на часы только заметные (heads-up/со звуком) уведомления.
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setCategory(NotificationCompat.CATEGORY_STOPWATCH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setTimeoutAfter(50_000)
                .setVibrate(longArrayOf(0, 300, 200, 300))
                .build()
            notificationManager.notify(id, notification)
        }
    }

    private fun cancelWatchNotifications() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) return
        val notificationManager = NotificationManagerCompat.from(this)
        watchNotificationIdStack.forEach { notificationManager.cancel(it) }
        watchNotificationIdStack.clear()
        watchNotificationCounter = 0
    }

    private fun acquireWakeLock() {
        if (wakeLock?.isHeld == true) return
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "jymlight:stopwatch"
        ).apply { acquire(10 * 60 * 60 * 1000L) } // максимум 10 часов
    }

    private fun releaseWakeLock() {
        wakeLock?.let { if (it.isHeld) it.release() }
        wakeLock = null
    }

    private fun updateNotificationTime(formatted: String) {
        // Обновляем уведомление раз в секунду, а не на каждый тик (10 мс).
        val seconds = formatted.take(5)
        if (seconds == lastNotifiedSecond) return
        lastNotifiedSecond = seconds
        updateNotification()
    }

    private fun updateNotification() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) return
        runCatching {
            NotificationManagerCompat.from(this)
                .notify(NOTIFICATION_ID, buildNotification())
        }
    }

    private fun buildNotification(): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_btn_play_24)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(lastNotifiedSecond.ifEmpty { "00:00" })
            .setContentIntent(openAppPendingIntent())
            .setOngoing(true)
            .setSilent(true)
            .setCategory(NotificationCompat.CATEGORY_STOPWATCH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        when (holder.status.value) {
            StopWatchState.RESUME -> {
                builder.addAction(
                    R.drawable.ic_btn_pause_24,
                    getString(R.string.action_pause),
                    actionPendingIntent(ACTION_PAUSE, REQ_PAUSE)
                )
                builder.addAction(
                    R.drawable.ic_btn_stop_24,
                    getString(R.string.action_stop),
                    actionPendingIntent(ACTION_STOP, REQ_STOP)
                )
            }
            StopWatchState.PAUSE -> {
                builder.addAction(
                    R.drawable.ic_btn_continue_24,
                    getString(R.string.action_resume),
                    actionPendingIntent(ACTION_RESUME, REQ_RESUME)
                )
                builder.addAction(
                    R.drawable.ic_btn_stop_24,
                    getString(R.string.action_stop),
                    actionPendingIntent(ACTION_STOP, REQ_STOP)
                )
            }
            StopWatchState.STOP -> Unit
        }
        return builder.build()
    }

    private fun actionPendingIntent(action: String, requestCode: Int): PendingIntent {
        val intent = Intent(this, StopWatchService::class.java).setAction(action)
        return PendingIntent.getService(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun openAppPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivityCompose::class.java)
        return PendingIntent.getActivity(
            this,
            REQ_OPEN_APP,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun formatTime(timeMiles: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMiles),
            ZoneId.systemDefault()
        )
        val formater = DateTimeFormatter.ofPattern(
            "mm:ss:SSS",
            Locale.getDefault()
        )
        return localDateTime.format(formater)
    }

    override fun onDestroy() {
        timerJob?.cancel()
        releaseWakeLock()
        serviceScope.cancel()
        super.onDestroy()
    }

    companion object {
        const val CHANNEL_ID = "stopwatch_channel"
        const val WATCH_CHANNEL_ID = "stopwatch_watch_channel"
        const val NOTIFICATION_ID = 1001
        private const val WATCH_NOTIFICATION_ID_BASE = 2000

        const val ACTION_START = "info.upump.jymlight.service.action.START"
        const val ACTION_PAUSE = "info.upump.jymlight.service.action.PAUSE"
        const val ACTION_RESUME = "info.upump.jymlight.service.action.RESUME"
        const val ACTION_STOP = "info.upump.jymlight.service.action.STOP"
        const val ACTION_TOGGLE_SOUND = "info.upump.jymlight.service.action.TOGGLE_SOUND"

        private const val REQ_PAUSE = 1
        private const val REQ_RESUME = 2
        private const val REQ_STOP = 3
        private const val REQ_OPEN_APP = 4

        fun intent(context: Context, action: String): Intent =
            Intent(context, StopWatchService::class.java).setAction(action)
    }
}
