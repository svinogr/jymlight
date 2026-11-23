package info.upump.jymlight.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseVMWithStateLoad : ViewModel() {
    protected val _stateLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _stateLoading
}
