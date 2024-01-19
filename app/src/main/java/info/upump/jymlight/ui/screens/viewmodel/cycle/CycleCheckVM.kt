package info.upump.jymlight.ui.screens.viewmodel.cycle

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.CycleCheck
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CycleCheckVM() : BaseVMWithStateLoad() {
    private val cycleRepo = CycleRepo.get()
    private val _cycleList = MutableStateFlow<List<CycleCheck>>(mutableListOf())
    val cycleList: StateFlow<List<CycleCheck>> = _cycleList.asStateFlow()
    suspend fun getAllPersonal() {
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val listEn = cycleRepo.getAllFullEntityPersonal()
            listEn.map {
                it.map { c ->
                    val cycle = Cycle.mapFullFromDbEntity(c)
                    val cycleCheck = CycleCheck(cycle = cycle).apply {
                        isCheck = false
                    }
                    return@map cycleCheck
                }
            }.collect { list ->
                Log.d("update", "${list.size}")
                _cycleList.update { list }
                _stateLoading.value = false
            }
        }
    }

    fun getCheckedCycle(): List<Cycle> {
        return cycleList.value.filter { it.isCheck }.map { it.cycle }
    }

    fun checkItem(id: Long) {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val list = _cycleList.value
            list.forEach { it ->
                if (id == it.cycle.id) it.isCheck = !it.isCheck
            }

            _cycleList.update { list }
        }
    }
}