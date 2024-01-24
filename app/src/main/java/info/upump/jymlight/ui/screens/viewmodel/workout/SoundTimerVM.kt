package info.upump.jymlight.ui.screens.viewmodel.workout

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.upump.jymlight.dataStore
import info.upump.jymlight.ui.screens.screenscomponents.screen.StopWatchState
import info.upump.jymlight.utils.KeysForDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
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


class SoundTimerVM() : ViewModel() {
    private val _isSound = MutableStateFlow(false)
    val isSound = _isSound.asStateFlow()
    private val _formatedTime = MutableStateFlow("00")
    val formatedTime = _formatedTime.asStateFlow()
    private val _status = MutableStateFlow(StopWatchState.STOP)
    val status = _status.asStateFlow()
    private var scope = CoroutineScope(Dispatchers.IO)

    private var lastTimeStamp = 0L
    private var timeMile = 0L

    private val _startSoundMiles = MutableStateFlow(0)
    val startSoundMiles = _startSoundMiles.asStateFlow()

    private val _finishSoundMiles = MutableStateFlow(0)
    val finishSoundMiles = _finishSoundMiles.asStateFlow()

    fun init(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _startSoundMiles.update {
                context.dataStore.data.map {
                    it[KeysForDataStore.START_KEY] ?: 0
                }.first()
            }
            _finishSoundMiles.update {
                context.dataStore.data.map {
                    it[KeysForDataStore.FINISH_KEY] ?: 0
                }.first()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun start(context: Context) {
        //Todo shared preferenc

        var isSoundStart = false
        var isSoundFinish = false

        scope.launch(Dispatchers.IO) {
            /*      _status.update { StopWatchState.RESUME }
                  while (status.value == StopWatchState.RESUME) {

                      lastTimeStamp = System.currentTimeMillis()
                      delay(1L)
                      timeMile += System.currentTimeMillis() - lastTimeStamp

                      if (timeMile >= startSoundMiles.value.first() && !isSoundStart) {
                          _isSound.update { true }
                          Log.d("sound", "sound start")
                      }

                      if (timeMile >= finishSoundMiles.value.first() && !isSoundFinish) {
                          _isSound.update { true }
                          Log.d("sound", "sound finish")
                      }

                  }*/
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
    fun resume(context: Context) {
        _status.update { StopWatchState.RESUME }
        start(context)
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

    fun setStart(it: Int, context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                 preferences[KeysForDataStore.START_KEY] = it
                _startSoundMiles.update { it }
            }

        }
    }

    fun setFinish(it: Int, context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[KeysForDataStore.FINISH_KEY] = it
                _finishSoundMiles.update { it }
            }

        }
    }

}