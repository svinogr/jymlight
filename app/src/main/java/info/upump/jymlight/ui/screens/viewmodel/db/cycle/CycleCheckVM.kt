package info.upump.jymlight.ui.screens.viewmodel.db.cycle

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.CycleRepoDB
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.CycleCheck
import info.upump.jymlight.ui.screens.mainscreen.NAME_RESTORE_FILE
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.utils.ReadToBackupRestorable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class CycleCheckVM() : BaseVMWithStateLoad() {
    private val cycleRepoDB = CycleRepoDB.get()
    private val _cycleList = MutableStateFlow<List<CycleCheck>>(mutableListOf())
    val cycleList: StateFlow<List<CycleCheck>> = _cycleList.asStateFlow()
    suspend fun getAllPersonalFromDB() {
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val listEn = cycleRepoDB.getAllFullEntityPersonal()
            listEn.map {
                it.map { c ->
                    val cycle = Cycle.mapFullFromDbEntity(c)
                    val cycleCheck = CycleCheck(cycle = cycle).apply {
                        isCheck = false
                    }
                    return@map cycleCheck
                }
            }.collect { list ->
                _cycleList.update { list }
                _stateLoading.value = false
            }
        }
    }

    suspend fun getAllPersonalFromFile(
        context: Context,
        restorable: ReadToBackupRestorable
    ) {
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val file = File(context.filesDir, NAME_RESTORE_FILE)
            val list = restorable.restoreFromUri(file.toUri(), context).map { c ->
                CycleCheck(c)
            }
            file.delete()

            _cycleList.update { list }
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