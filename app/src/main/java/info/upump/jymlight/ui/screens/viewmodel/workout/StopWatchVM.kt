package info.upump.jymlight.ui.screens.viewmodel.workout

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.upump.jymlight.dataStore
import info.upump.jymlight.service.StopWatchService
import info.upump.jymlight.service.StopWatchStateHolder
import info.upump.jymlight.utils.KeysForDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Тонкая оболочка над StopWatchService.
 * Состояние (статус/время) берётся из общего StopWatchStateHolder —
 * его наполняет сервис, поэтому экран и уведомление показывают одно и то же.
 * Команды (start/pause/resume/stop) уходят в сервис Intent-ами,
 * таймер продолжает работать в фоне.
 */
@HiltViewModel
class StopWatchVM @Inject constructor(
    private val holder: StopWatchStateHolder,
) : ViewModel() {

    val isSoundEveryTime = holder.isSoundEveryTime
    val formatedTime = holder.formatedTime
    val status = holder.status

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
        viewModelScope.launch(Dispatchers.IO) {
            val saved = context.dataStore.data.map {
                it[KeysForDataStore.SOUND_EVERY_TIME] ?: false
            }.first()
            holder.setSoundEveryTime(saved)
        }
    }

    fun start() {
        ContextCompat.startForegroundService(
            context,
            StopWatchService.intent(context, StopWatchService.ACTION_START)
        )
    }

    fun pause() {
        context.startService(StopWatchService.intent(context, StopWatchService.ACTION_PAUSE))
    }

    fun resume() {
        context.startService(StopWatchService.intent(context, StopWatchService.ACTION_RESUME))
    }

    fun stop() {
        context.startService(StopWatchService.intent(context, StopWatchService.ACTION_STOP))
    }

    fun setSoundEveryTime() {
        val newValue = !holder.isSoundEveryTime.value
        holder.setSoundEveryTime(newValue)
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[KeysForDataStore.SOUND_EVERY_TIME] = newValue
            }
        }
    }
}
