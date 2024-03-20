package info.upump.jymlight.ui.screens.viewmodel

import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.Workout
import kotlinx.coroutines.flow.StateFlow

interface CycleDetailVMInterface {
    val id: StateFlow<Long>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<String>
    val finishDate: StateFlow<String>
    val img: StateFlow<String>
    val imgDefault: StateFlow<String>
    val subItems: StateFlow<List<Workout>>
    fun getBy(id: Long)
    fun deleteSubItem(id: Long)
    fun cleanItem()
    fun copyToPersonal(long: Long)
}