package info.upump.jymlight.ui.screens.viewmodel.web.exercise

import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.ExerciseRepo
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.ExerciseDescription
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.db.exercise.ExerciseReviewVMInterface
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.ExerciseDescriptionRet
import info.upump.web.model.ExerciseRet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseReviewVMWEB : BaseVMWithStateLoad(),
    ExerciseReviewVMInterface {

    private val _item = MutableStateFlow(Exercise().apply {
        exerciseDescription = ExerciseDescription()
    })
    override val item: StateFlow<Exercise> = _item
    override fun getItem(id: Long) {
        _stateLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val exDeSe = RetrofitServiceWEB.getExerciseService().getExerciseFullById(id)
            exDeSe.enqueue(object : Callback<ExerciseRet> {
                override fun onResponse(
                    call: Call<ExerciseRet>,
                    response: Response<ExerciseRet>
                ) {
                    val exerciseDescription = Exercise.mapFromFullRetEntity(response.body()!!)
                    _item.update { exerciseDescription }

                    _stateLoading.value = false
                }

                override fun onFailure(call: Call<ExerciseRet>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}