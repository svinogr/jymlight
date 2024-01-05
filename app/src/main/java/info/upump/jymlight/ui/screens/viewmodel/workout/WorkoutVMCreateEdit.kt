package info.upump.jymlight.ui.screens.viewmodel.workout


import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.WorkoutRepo
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Entity
import info.upump.jymlight.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class WorkoutVM() : info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad(), WorkoutVMInterface {
    companion object {
        val vmOnlyForPreview by lazy {
            object : WorkoutVMInterface {

                private val _workout = MutableStateFlow(Workout().apply {
                    day = Day.TUESDAY
                    title = "Preview Workout"
                    comment = "это Preview Workout"
                })

                override val item: StateFlow<Workout> = _workout.asStateFlow()

                private val _id = MutableStateFlow(item.value.id)
                override val id: StateFlow<Long> = _id.asStateFlow()
                override val parentId: StateFlow<Long>
                    get() = TODO("Not yet implemented")

                private val _title = MutableStateFlow(_workout.value.title)
                override val title: StateFlow<String> = _title.asStateFlow()

                private val _comment = MutableStateFlow(_workout.value.comment)
                override val comment: StateFlow<String> = _comment.asStateFlow()

                private val _startDate = MutableStateFlow(_workout.value.startStringFormatDate)
                override val startDate: StateFlow<String> = _startDate

                private val _finishDate = MutableStateFlow(_workout.value.finishStringFormatDate)
                override val finishDate: StateFlow<String> = _finishDate

                private val _day = MutableStateFlow(_workout.value.day)
                override val day: StateFlow<Day> = _day
                override val isTitleError: StateFlow<Boolean>
                    get() = TODO("Not yet implemented")

                private val _img = MutableStateFlow(_workout.value.day.toString())
                override val img: StateFlow<String> = _img.asStateFlow()

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save(callback: (id: Long) -> Unit) {
                    TODO("Not yet implemented")
                }

                override fun updateTitle(title: String) {
                    TODO("Not yet implemented")
                }

                override fun updateStartDate(date: Date) {
                    TODO("Not yet implemented")
                }

                override fun updateFinishDate(date: Date) {
                    TODO("Not yet implemented")
                }

                override fun updateComment(comment: String) {
                    TODO("Not yet implemented")
                }

                override fun updateDay(dayN: Day) {
                    _day.update { dayN }
                }

                override fun collectToSave(): Workout {
                    TODO("Not yet implemented")
                }

                override fun isBlankFields(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun updateId(id: Long) {
                    _id.update { id }
                }

                override fun updateParent(parentId: Long) {
                    TODO("Not yet implemented")
                }

                override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
                    TODO("Not yet implemented")
                }

                override fun updateEven(isEven: Boolean) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    private val _workout = MutableStateFlow(Workout())
    override val item: StateFlow<Workout> = _workout.asStateFlow()

    private val _id = MutableStateFlow(0L)
    override val id: StateFlow<Long> = _id.asStateFlow()

    private val _parentId = MutableStateFlow(0L)
    override val parentId: StateFlow<Long> = _parentId.asStateFlow()

    private val _title = MutableStateFlow("")
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _comment = MutableStateFlow("")
    override val comment: StateFlow<String> = _comment.asStateFlow()

    private val _startDate = MutableStateFlow("")
    override val startDate: StateFlow<String> = _startDate

    private val _finishDate = MutableStateFlow("")
    override val finishDate: StateFlow<String> = _finishDate

    private val _day = MutableStateFlow(Day.MONDAY)
    override val day: StateFlow<Day> = _day

    private val _isTitleError = MutableStateFlow(false)
    override val isTitleError: StateFlow<Boolean> = _isTitleError.asStateFlow()

    private val _img = MutableStateFlow("")
    override val img: StateFlow<String> = _img.asStateFlow()

    private val _isEven = MutableStateFlow(false)
    val isEven: StateFlow<Boolean> = _isEven.asStateFlow()

    override fun updateParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == 0L) {
                val workout = Workout()
                _workout.update { Workout() }
                with(workout) {
                    _title.update { title }
                    _id.update { id }
                    _isEven.update { isWeekEven }
                    _parentId.update { parentId }
                    _comment.update { comment }
                    _startDate.update { startStringFormatDate }
                    _finishDate.update { finishStringFormatDate }
                    _day.update { day }
                }

                return@launch
            }

            WorkoutRepo.get().getFullEntityBy(id).map {
                Workout.mapFromFullDbEntity(it)
            }.collect {
                with(it) {
                    _title.update { title }
                    _id.update { id }
                    _parentId.update { parentId }
                    _comment.update { comment }
                    _isEven.update { isWeekEven }
                    _startDate.update { startStringFormatDate }
                    _finishDate.update { finishStringFormatDate }
                    _day.update { day }
                }
            }
        }
    }

    override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val wC = collectToSave()
            val wE = Workout.mapToEntity(wC)
            wE.parent_id = parentId
            val save = WorkoutRepo.get().save(wE)
            launch(Dispatchers.Main) {
                callback(save._id)
            }
        }
    }

    override fun save(callback: (id: Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val wC = collectToSave()
            Log.d("paren", "${parentId.value} ${wC.parentId}")
            val wE = Workout.mapToEntity(wC)
            WorkoutRepo.get().save(wE)
        }
    }

    override fun collectToSave(): Workout {
        val collectW = Workout().apply {
            id = _id.value
            title = _title.value
            day = _day.value
            parentId = _parentId.value
            comment = _comment.value
            isWeekEven = _isEven.value
            setFinishDate(_finishDate.value)
            setStartDate(_startDate.value)
        }
        Log.d("col", collectW.toString())
        return collectW
    }

    override fun isBlankFields(): Boolean {
        val isBlank = _title.value.trim().isBlank()
        _isTitleError.update { isBlank }

        return isBlank
    }

    override fun updateTitle(title: String) {
        _title.update { title }
        _isTitleError.update { title.trim().isBlank() }
    }

    override fun updateStartDate(date: Date) {
        _startDate.update { Entity.formatDateToString(date) }
    }

    override fun updateFinishDate(date: Date) {
        _finishDate.update { Entity.formatDateToString(date) }
    }

    override fun updateComment(comment: String) {
        _comment.update { comment }
    }

    override fun updateDay(day: Day) {
        _day.update { day }
    }

    override fun updateEven(isEven: Boolean) {
        _isEven.update { isEven }
    }

    override fun updateId(id: Long) {
        _id.update { id }
    }
}
