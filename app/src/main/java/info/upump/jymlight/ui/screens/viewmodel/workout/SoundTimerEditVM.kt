package info.upump.jymlight.ui.screens.viewmodel.workout

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.upump.jymlight.dataStore
import info.upump.jymlight.utils.KeysForDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoundTimerEditVM : ViewModel() {
    private val _start = MutableStateFlow(0)
    val start = _start.asStateFlow()

    private val _finish = MutableStateFlow(0)
    val finis = _finish.asStateFlow()

    fun init(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _start.update {
                context.dataStore.data.map {
                    it[KeysForDataStore.START_KEY] ?: 0
                }.first()
            }
            _finish.update {
                context.dataStore.data.map {
                    it[KeysForDataStore.FINISH_KEY] ?: 0
                }.first()
            }
        }
    }

    fun setStart(start: Int) {
        _start.update { start }
    }

    fun setFinish(finish: Int) {
        _finish.update { finish }
    }

    fun save(context: Context) {
        Log.d("sound", "${_start.value}")
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[KeysForDataStore.START_KEY] = _start.value
                preferences[KeysForDataStore.FINISH_KEY] = _finish.value
            }
        }
    }
}