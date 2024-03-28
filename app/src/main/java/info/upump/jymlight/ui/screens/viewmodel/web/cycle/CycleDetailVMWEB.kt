package info.upump.jymlight.ui.screens.viewmodel.web.cycle

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.RepoActions
import info.upump.database.RepoActionsSpecific
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts
import info.upump.database.entities.WorkoutEntity
import info.upump.database.repo.db.CycleRepoDB
import info.upump.database.repo.db.WorkoutRepo
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Workout
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.CycleDetailVMInterface
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.CycleRet
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

class CycleDetailVMWEB : BaseVMWithStateLoad(),
    CycleDetailVMInterface {
    private val workoutRepo: RepoActions<WorkoutEntity> = WorkoutRepo.get()
    private val cycleRepoDB: RepoActionsSpecific<CycleEntity, CycleFullEntityWithWorkouts> =
        CycleRepoDB.get()

    private var _workouts = MutableStateFlow<List<Workout>>(mutableListOf())
    override val subItems: StateFlow<List<Workout>> = _workouts

    private val _id = MutableStateFlow(0L)
    override val id: StateFlow<Long> = _id.asStateFlow()

    private val _image = MutableStateFlow("")
    override val img: StateFlow<String> = _image.asStateFlow()

    private val _imageDefault = MutableStateFlow("")
    override val imgDefault: StateFlow<String> = _imageDefault.asStateFlow()


    private val _title = MutableStateFlow("")
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _comment = MutableStateFlow("")
    override val comment: StateFlow<String> = _comment.asStateFlow()

    private val _startDate = MutableStateFlow("")
    override val startDate: StateFlow<String> = _startDate.asStateFlow()

    private val _finishDate = MutableStateFlow("")
    override val finishDate: StateFlow<String> = _finishDate.asStateFlow()

    override fun getBy(id: Long) {
        Log.d("id", "get by $id in CycDeW")

        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            val call = RetrofitServiceWEB.getCycleService().getCycleFullById(id)
            call.enqueue(object : Callback<CycleRet> {
                override fun onResponse(call: Call<CycleRet>, response: Response<CycleRet>) {
                    Log.d("id", "get by $id resp")
                    val cR = response.body()
                    val cycle = Cycle.mapFullFromRetEntity(cR!!)
                    Log.d("id", "get by $cycle resp")
                    _workouts.update { cycle.workoutList }
                    _id.update { cycle.id }
                    _title.update { cycle.title }
                    _comment.update { cycle.comment }
                    _startDate.update { cycle.startStringFormatDate }
                    _finishDate.update { cycle.finishStringFormatDate }
                    _image.update { cycle.image }
                    _imageDefault.update { cycle.imageDefault }
                    _stateLoading.value = false
                }

                override fun onFailure(call: Call<CycleRet>, t: Throwable) {

                }
            })

        }
    }

    override fun deleteSubItem(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceWEB.getWorkoutService().deleteById(id)
            service.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        val index = _workouts.value.indexOfFirst { it.id == id }
                        val list = _workouts.value.toMutableList()
                        list.removeAt(index)
                        _workouts.update { list }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        }
    }

    override fun cleanItem() {
     /*   viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceWEB.getWorkoutService().clean(_id.value)
            service.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        _workouts.update { listOf() }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        }*/
        /*  viewModelScope.launch(Dispatchers.IO) {
              _stateLoading.value = true
              cycleRepoDB.deleteChilds(id.value)
          }*/
    }

    override fun copyToPersonal(id: Long) {
        /*        _stateLoading.value = true
                viewModelScope.launch(Dispatchers.IO) {
                    val cycleRepoDB = CycleRepoDB.get() as CycleRepoDB
                    val today = Cycle.formatDateToString(Date())
                    cycleRepoDB.copyToPersonal(id, today)
                    _stateLoading.value = false

                }*/
    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : CycleDetailVMInterface {
                private val _itemList = MutableStateFlow(
                    mutableListOf(
                        Cycle().apply { title = "Preview2" },
                        Cycle().apply { title = "Preview 3" })
                )

                private val _cycle = MutableStateFlow(Cycle().apply {
                    imageDefault = "drew"
                    image = ""
                    title = "Preview"
                    comment = "это Preview"
                })


                private var _workouts = MutableStateFlow<List<Workout>>(listOf(
                    Workout(
                        isWeekEven = false, isDefaultType = false,
                        isTemplate = false, day = Day.FRIDAY, exercises = listOf()
                    ).apply { title = "Новая" }, Workout(
                        isWeekEven = false, isDefaultType = false,
                        isTemplate = false, day = Day.THURSDAY, exercises = listOf()
                    ).apply { title = "Новая 1" }, Workout(

                        isWeekEven = false, isDefaultType = false,
                        isTemplate = false, day = Day.MONDAY, exercises = listOf()
                    ).apply { title = "Новая 2" }
                ))
                override val subItems: StateFlow<List<Workout>> = _workouts


                private val _id = MutableStateFlow(_cycle.value.id)
                override val id: StateFlow<Long> = _id.asStateFlow()

                private val _title = MutableStateFlow(_cycle.value.title)
                override val title: StateFlow<String> = _title.asStateFlow()

                private val _comment = MutableStateFlow(_cycle.value.comment)
                override val comment: StateFlow<String> = _comment.asStateFlow()

                private val _startDate = MutableStateFlow(_cycle.value.startStringFormatDate)
                override val startDate: StateFlow<String> = _startDate

                private val _finishDate = MutableStateFlow(_cycle.value.finishStringFormatDate)
                override val finishDate: StateFlow<String> = _finishDate

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun deleteSubItem(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun cleanItem() {
                    TODO("Not yet implemented")
                }

                override fun copyToPersonal(id: Long) {
                    TODO("Not yet implemented")
                }

                private val _img = MutableStateFlow(_cycle.value.image)
                override val img: StateFlow<String> = _img.asStateFlow()

                private val _imgDefault = MutableStateFlow(_cycle.value.imageDefault)
                override val imgDefault: StateFlow<String> = _imgDefault.asStateFlow()
            }
        }
    }
}