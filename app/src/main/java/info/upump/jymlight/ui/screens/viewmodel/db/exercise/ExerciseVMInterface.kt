package info.upump.jymlight.ui.screens.viewmodel.db.exercise

import info.upump.jymlight.models.entity.Sets
import kotlinx.coroutines.flow.StateFlow

interface ExerciseVMInterface {
    val subItems: StateFlow<List<Sets>>
    fun getBy(id: Long)
    fun deleteSub(id: Long)
    fun cleanItem()
}