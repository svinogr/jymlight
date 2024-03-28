package info.upump.jymlight.ui.screens.viewmodel.web.exercise

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.TypeMuscle
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.db.exercise.ExerciseChooseVMInterface
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.ExerciseRet
import info.upump.web.model.WorkoutRet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExerciseChooseVMWEB : BaseVMWithStateLoad(),
    ExerciseChooseVMInterface {
    private var parentId: Long = 0L

    private val _day = MutableStateFlow(Day.TUESDAY)
    override val day: StateFlow<Day> = _day

    private val _subItems = MutableStateFlow<List<Exercise>>(mutableListOf())
    override val subItems: StateFlow<List<Exercise>> = _subItems

    override fun getAllExerciseForParent(parent: Long) {
        this.parentId = parent

        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true

            val templateExerciseService =
                RetrofitServiceWEB.getTemplateService().getAllTemplateExercise()
            templateExerciseService.enqueue(object : Callback<List<ExerciseRet>> {
                override fun onResponse(
                    call: Call<List<ExerciseRet>>,
                    response: Response<List<ExerciseRet>>
                ) {
                    if (response.code() == 200) {
                        val listExercise = response.body()
                        val list = listExercise!!.map { Exercise.mapFromFullRetEntity(it) }

                        _subItems.update { list }
                    }
                }

                override fun onFailure(call: Call<List<ExerciseRet>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun saveForParentChosen(id: Long, callback: (id: Long) -> Unit) {
        Log.d("Save exercise", "save $id")
        viewModelScope.launch(Dispatchers.IO) {
            val workoutRet = WorkoutRet()
            workoutRet.id = parentId
            workoutRet.startDate = "2017-12-31T21:00:00.000+00:00"
            workoutRet.finishDate = "2017-12-31T21:00:00.000+00:00"

            val exerciseService = RetrofitServiceWEB.getExerciseService().copy(id, workoutRet)
            exerciseService.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 201) {
                        val location = response.headers().get("Location")!!.split("/")
                        val idS = location[location.size - 1]
                        callback(idS.toLong())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun filterBy(type: TypeMuscle) {
        _subItems.value.filter {
            it.typeMuscle == type
        }
    }
}