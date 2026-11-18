package info.upump.jymlight.service

import info.upump.jymlight.ui.screens.screenscomponents.screen.StopWatchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Общее состояние секундомера. Живёт в Hilt-синглтоне,
 * поэтому им делятся и экран (StopWatchVM), и StopWatchService.
 */
@Singleton
class StopWatchStateHolder @Inject constructor() {

    private val _status = MutableStateFlow(StopWatchState.STOP)
    val status = _status.asStateFlow()

    private val _formatedTime = MutableStateFlow("00:00:00")
    val formatedTime = _formatedTime.asStateFlow()

    private val _isSoundEveryTime = MutableStateFlow(false)
    val isSoundEveryTime = _isSoundEveryTime.asStateFlow()

    fun setStatus(state: StopWatchState) {
        _status.update { state }
    }

    fun setTime(time: String) {
        _formatedTime.update { time }
    }

    fun setSoundEveryTime(sound: Boolean) {
        _isSoundEveryTime.update { sound }
    }

    fun reset() {
        _status.update { StopWatchState.STOP }
        _formatedTime.update { "00:00:00" }
    }
}
