package info.upump.jymlight.ui.screens.viewmodel.workout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import info.upump.jymlight.ui.screens.screenscomponents.screen.StopWatchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class StopWatchVM : ViewModel() {
    private val _formatedTime = MutableStateFlow("00:00:00")
    val formatedTime = _formatedTime.asStateFlow()
    private val _status = MutableStateFlow(StopWatchState.STOP)
    val status = _status.asStateFlow()
    private var scope = CoroutineScope(Dispatchers.IO)

    private var lastTimeStamp = 0L
    private var timeMile = 0L

    @RequiresApi(Build.VERSION_CODES.O)
    fun start() {
        scope.launch(Dispatchers.IO) {
            _status.update { StopWatchState.RESUME }
            while (status.value == StopWatchState.RESUME) {

                lastTimeStamp = System.currentTimeMillis()
                delay(10L)
                timeMile += System.currentTimeMillis() - lastTimeStamp
                _formatedTime.update { formatTime(timeMile) }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun stop() {
        _status.update { StopWatchState.STOP }
        scope.cancel()
        scope = CoroutineScope(Dispatchers.IO)
        lastTimeStamp = 0L
        timeMile = 0L
        _formatedTime.update { "00:00:00" }

    }

    fun pause() {
        _status.update { StopWatchState.PAUSE }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun resume() {
        _status.update { StopWatchState.RESUME }
        start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}