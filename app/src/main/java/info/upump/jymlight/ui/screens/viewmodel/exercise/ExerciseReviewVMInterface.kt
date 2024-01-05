package info.upump.jymlight.ui.screens.viewmodel.exercise

import info.upump.jymlight.models.entity.Exercise
import kotlinx.coroutines.flow.StateFlow

interface ExerciseReviewVMInterface {
    val item: StateFlow<Exercise>
    fun getItem(id: Long)
}