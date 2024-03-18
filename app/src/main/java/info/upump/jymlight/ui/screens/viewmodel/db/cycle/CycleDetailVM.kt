package info.upump.jymlight.ui.screens.viewmodel.db.cycle

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
import info.upump.jymlight.ui.screens.viewmodel.CycleDetailVMInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class CycleDetailVM : info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad(),
    CycleDetailVMInterface {
    private val workoutRepo: RepoActions<WorkoutEntity> = WorkoutRepo.get()
    private val cycleRepoDB: RepoActionsSpecific<CycleEntity, CycleFullEntityWithWorkouts> =
        CycleRepoDB.get()

    private var _cycle = MutableStateFlow(Cycle())
    override val item: StateFlow<Cycle> = _cycle.asStateFlow()

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
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            cycleRepoDB.getFullEntityBy(id).map {
                Cycle.mapFullFromDbEntity(it)
            }.collect { cycle ->
                _workouts.update { cycle.workoutList }
                _cycle.update { cycle }
                _id.update { cycle.id }
                _title.update { cycle.title }
                _comment.update { cycle.comment }
                _startDate.update { cycle.startStringFormatDate }
                _finishDate.update { cycle.finishStringFormatDate }
                _image.update { cycle.image }
                _imageDefault.update { cycle.imageDefault }
                _stateLoading.value = false
            }
        }
    }

    override fun deleteSubItem(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            workoutRepo.delete(id)
        }
    }

    override fun cleanItem() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true
            cycleRepoDB.deleteChilds(id.value)
        }
    }

    override fun copyToPersonal(id: Long) {
        _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val cycleRepoDB = CycleRepoDB.get() as CycleRepoDB
            val today = Cycle.formatDateToString(Date())
            cycleRepoDB.copyToPersonal(id, today)
            _stateLoading.value = false

        }
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

                override val item: StateFlow<Cycle> = _cycle

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