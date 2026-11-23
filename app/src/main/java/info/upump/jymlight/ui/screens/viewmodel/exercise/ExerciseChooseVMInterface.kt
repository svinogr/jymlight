package info.upump.jymlight.ui.screens.viewmodel.exercise

import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Exercise
import kotlinx.coroutines.flow.StateFlow

interface ExerciseChooseVMInterface {
    val day: StateFlow<Day>
    val subItems: StateFlow<List<Exercise>>
    fun getAllExerciseForParent(parent: Long)
    fun saveForParentChosen(id: Long, callback: (id: Long) -> Unit)

}
