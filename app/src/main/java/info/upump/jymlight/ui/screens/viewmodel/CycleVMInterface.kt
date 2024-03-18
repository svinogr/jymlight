package info.upump.jymlight.ui.screens.viewmodel

import android.content.Context
import info.upump.jymlight.models.entity.Cycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface CycleVMInterface {
    val cycleList: StateFlow<List<Cycle>>
    suspend fun getAllPersonal()
    fun getAllDefault()
    fun delete(context: Context, image: String, id: Long)

}