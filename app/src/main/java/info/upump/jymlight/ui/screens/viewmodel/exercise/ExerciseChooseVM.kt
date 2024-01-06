package info.upump.jymlight.ui.screens.viewmodel.exercise

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.TypeMuscle
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch


class ExerciseChooseVM : BaseVMWithStateLoad(),
    ExerciseChooseVMInterface {
    private val exerciseRepo = ExerciseRepo.get()
    private val workoutRepo = WorkoutRepo.get()
    private var parentId: Long = 0L

    private val _day = MutableStateFlow(Day.TUESDAY) //TODO изменить на каритинку
    override val day: StateFlow<Day> = _day

    private val _subItems = MutableStateFlow<List<Exercise>>(mutableListOf())
    override val subItems: StateFlow<List<Exercise>> = _subItems

    override fun getAllExerciseForParent(parentId: Long) {
        this.parentId = parentId

        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true

            exerciseRepo.getAllFullEntityTemplate().map {
                it.map { e ->
                    Exercise.mapFromFullDbEntity(e)
                }
            }.collect { list ->
                _subItems.value = list
            }
        }
    }

    override fun saveForParentChosen(id: Long, callback: (id: Long) -> Unit) {
        Log.d("Save exercise", "save $id")
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseRepo = ExerciseRepo.get()

            exerciseRepo.getFullEntityBy(id).take(1).collect { exeFullEnt ->  // костыль
                val exeDescId = exeFullEnt.exerciseDescriptionEntity._id
                Log.d("Save exercise", "save $id")
                val exercise = Exercise()
                exercise.id = 0
                exercise.parentId = parentId
                Log.d("Save exercise", "save $parentId")
                exercise.typeMuscle = TypeMuscle.valueOf(exeFullEnt.exerciseEntity.type_exercise!!)
                exercise.isDefaultType = false
                exercise.isTemplate = false
                exercise.descriptionId = exeDescId
                exercise.comment = exeFullEnt.exerciseEntity.comment!!

                val save =  exerciseRepo.save(Exercise.mapToEntity(exercise))

                launch(Dispatchers.Main) {
                    callback(save._id)
                }
            }
        }
    }

    fun filterBy(type: TypeMuscle) {
        _subItems.value.filter {
            it.typeMuscle == type
        }
    }


}