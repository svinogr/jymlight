package info.upump.jymlight.ui.screens.viewmodel.web.workout

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.ExerciseRepo
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.ExerciseDescription
import info.upump.jymlight.models.entity.Sets
import info.upump.jymlight.models.entity.TypeMuscle
import info.upump.jymlight.models.entity.Workout
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.WorkoutRet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutDetailVMWEB : info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad(),
    info.upump.jymlight.ui.screens.viewmodel.WorkoutDetailVMInterface {

    private val _workout = MutableStateFlow(Workout())

    override val item: StateFlow<Workout> = _workout.asStateFlow()

    private val _exercises = MutableStateFlow<List<Exercise>>(mutableListOf())

    override val subItems: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    private val _id = MutableStateFlow(0L)

    override val id: StateFlow<Long> = _id.asStateFlow()

    private
    val _title = MutableStateFlow("")

    override
    val title: StateFlow<String> =
        _title.asStateFlow()

    private
    val _comment = MutableStateFlow(
        ""
    )

    override
    val comment: StateFlow<String> =
        _comment.asStateFlow()

    private
    val _startDate =
        MutableStateFlow(
            ""
        )

    override
    val startDate: StateFlow<String> =
        _startDate.asStateFlow()

    private
    val _finishDate =
        MutableStateFlow(
            ""
        )

    override
    val finishDate: StateFlow<String> =
        _finishDate.asStateFlow()


    private
    val _day = MutableStateFlow(Day.TUESDAY)

    override
    val day: StateFlow<Day> = _day.asStateFlow()

    val _isEven = MutableStateFlow<Boolean>(true)

    override val isEven: StateFlow<Boolean> = _isEven.asStateFlow()

    override fun getBy(id: Long) {
        _stateLoading.value = true
        Log.d("id", "get by $id in CycDeW")

        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            val call = RetrofitServiceWEB.getWorkoutService().getWorkoutFullById(id)
            call.enqueue(object : Callback<WorkoutRet> {
                override fun onResponse(call: Call<WorkoutRet>, response: Response<WorkoutRet>) {
                    Log.d("id", "get by $id resp")
                    val cR = response.body()
                    val workout = Workout.mapFullFromRetEntity(cR!!)
                    Log.d("id", "get by $workout resp")
                    Log.d("id", "get by ${cR.exercises.size} resp")
                    _workout.update { workout }
                    _id.update { workout.id }
                    _title.update { workout.title }
                    _comment.update { workout.comment }
                    _startDate.update { workout.startStringFormatDate }
                    _finishDate.update { workout.finishStringFormatDate }
                    _day.update { workout.day }
                    _isEven.update { workout.isWeekEven }
                    _exercises.update { workout.exercises }
                    _stateLoading.value = false
                }

                override fun onFailure(call: Call<WorkoutRet>, t: Throwable) {

                }
            })

        }

    }

    override fun delete(it: Long) {

    }

    override fun deleteSub(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val serviceExercise = RetrofitServiceWEB.getExerciseService().deleteById(id)
            serviceExercise.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        val index = _exercises.value.indexOfFirst { it.id == id }
                        val list = _exercises.value.toMutableList()
                        list.removeAt(index)
                        _exercises.update { list }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun cleanItem() {
        _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val workoutService = RetrofitServiceWEB.getWorkoutService().clean(_id.value)
            workoutService.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        _exercises.update {
                            mutableListOf()
                        }
                    }

                    _stateLoading.value = false
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


        }
    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : info.upump.jymlight.ui.screens.viewmodel.WorkoutDetailVMInterface {
                private val _workout = MutableStateFlow((Workout().apply {
                    id = 1
                    comment =
                        "Предварительные выводы неутешительны: убеждённость некоторых оппонентов влечет за собой процесс внедрения и модернизации переосмысления внешнеэкономических политик. Высокий уровень вовлечения представителей целевой аудитории является четким доказательством простого факта: консультация с широким активом способствует подготовке и реализации направлений прогрессивного развития. Также как перспективное планирование играет важную роль в формировании кластеризации усилий. Имеется спорная точка зрения, гласящая примерно следующее: активно развивающиеся страны третьего мира и по сей день остаются уделом либералов, которые жаждут быть функционально разнесены на независимые элементы."
                    title = "Preview3"
                    isWeekEven = true
                    isDefaultType = true
                    day = Day.TUESDAY
                }))

                override val item: StateFlow<Workout> = _workout.asStateFlow()

                private val _exercises = MutableStateFlow(listOf(
                    Exercise().apply {
                        id = 1
                        title = "Упражнение"
                        typeMuscle = TypeMuscle.ABS
                        isDefaultType = true
                        isTemplate = true
                        setsList = mutableListOf(
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets()
                        )
                        descriptionId = 1
                        exerciseDescription = ExerciseDescription().apply {
                            img = "nach1"
                            defaultImg = "nach1"
                            title = "Новое упраж"
                            id = descriptionId
                        }
                    },
                    Exercise().apply {
                        id = 2
                        title = "Упражнение"
                        typeMuscle = TypeMuscle.BACK
                        isDefaultType = true
                        isTemplate = true
                        setsList = mutableListOf(
                            Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets(), Sets()
                        )
                        descriptionId = 2
                        exerciseDescription = ExerciseDescription().apply {
                            img = "nach1"
                            defaultImg = "nach1"
                            title = "Новое упраж"
                            id = descriptionId
                        }
                    },
                    Exercise().apply {
                        id = 3
                        title = "Упражнение"
                        typeMuscle = TypeMuscle.CALVES
                        isDefaultType = true
                        isTemplate = true
                        setsList = mutableListOf(
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets(),
                            Sets()
                        )
                        descriptionId = 3
                        exerciseDescription = ExerciseDescription().apply {
                            img = "nach1"
                            defaultImg = "nach1"
                            title = "Новое упраж"
                            id = descriptionId
                        }
                    }
                ))

                override val subItems: StateFlow<List<Exercise>> = _exercises.asStateFlow()


                private val _id = MutableStateFlow(_workout.value.id)

                override val id: StateFlow<Long> = _id.asStateFlow()

                private
                val _title = MutableStateFlow("Тренировка")

                override
                val title: StateFlow<String> =
                    _title.asStateFlow()

                private
                val _comment = MutableStateFlow(
                    _workout.value.comment
                )

                override
                val comment: StateFlow<String> =
                    _comment.asStateFlow()

                private
                val _startDate =
                    MutableStateFlow(
                        _workout.value.startStringFormatDate
                    )

                override
                val startDate: StateFlow<String> =
                    _startDate

                private
                val _finishDate =
                    MutableStateFlow(
                        _workout.value.finishStringFormatDate
                    )

                override
                val finishDate: StateFlow<String> =
                    _finishDate

                override fun getBy(
                    id: Long
                ) {
                    TODO("Not yet implemented")
                }

                override fun delete(it: Long) {
                    TODO("Not yet implemented")
                }

                override fun deleteSub(it: Long) {
                    TODO("Not yet implemented")
                }

                override fun cleanItem() {
                    TODO("Not yet implemented")
                }

                private
                val _day = MutableStateFlow(_workout.value.day)

                override
                val day: StateFlow<Day> = _day.asStateFlow()

                val _isEven = MutableStateFlow<Boolean>(true)

                override val isEven: StateFlow<Boolean> = _isEven
            }
        }
    }
}
