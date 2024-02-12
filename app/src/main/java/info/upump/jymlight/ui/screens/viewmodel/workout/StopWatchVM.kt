package info.upump.jymlight.ui.screens.viewmodel.workout

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.upump.jymlight.R
import info.upump.jymlight.dataStore
import info.upump.jymlight.ui.screens.screenscomponents.screen.StopWatchState
import info.upump.jymlight.utils.KeysForDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class StopWatchVM : ViewModel() {
    private val _isSoundEveryTime = MutableStateFlow(false)
    val isSoundEveryTime = _isSoundEveryTime.asStateFlow()
    private val _formatedTime = MutableStateFlow("00:00:00")
    val formatedTime = _formatedTime.asStateFlow()
    private val _status = MutableStateFlow(StopWatchState.STOP)
    val status = _status.asStateFlow()
    private var scope = CoroutineScope(Dispatchers.IO)

    private var lastTimeStamp = 0L
    private var timeMile = 0L
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
        viewModelScope.launch(Dispatchers.IO) {
            _isSoundEveryTime.update {
                context.dataStore.data.map {
                    it[KeysForDataStore.SOUND_EVERY_TIME] ?: false
                }.first()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun start() {
        val player = MediaPlayer.create(context, R.raw.everytimesound)
        scope.launch(Dispatchers.IO) {
            _status.update { StopWatchState.RESUME }
            var chet = 0
            while (status.value == StopWatchState.RESUME) {
                lastTimeStamp = System.currentTimeMillis()
                delay(10L)
                chet += 10
                timeMile += System.currentTimeMillis() - lastTimeStamp
                _formatedTime.update { formatTime(timeMile) }

                if(chet >= 2000 && _isSoundEveryTime.value) {
                    player.start()
                    chet = 0
                }
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

    fun setSoundEveryTime() {
        _isSoundEveryTime.update { !_isSoundEveryTime.value }
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[KeysForDataStore.SOUND_EVERY_TIME] = _isSoundEveryTime.value
            }
        }
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