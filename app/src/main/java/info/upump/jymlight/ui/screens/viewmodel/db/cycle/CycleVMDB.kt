package info.upump.jymlight.ui.screens.viewmodel.db.cycle

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.CycleRepoDB
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.CycleVMInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// для списка всех cycles
class CycleVMDB : BaseVMWithStateLoad(), CycleVMInterface {
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())

    override val cycleList: StateFlow<List<Cycle>>
        get() = _cycleList.asStateFlow()

    private val cycleRepoDB = CycleRepoDB.get()

    override suspend fun getAllPersonal(context: Context) {
        Log.d("getAllPersonal", "getAllPersonal")
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val listEn = cycleRepoDB.getAllFullEntityPersonal()
            listEn.map {
                it.map { c ->
                    Cycle.mapFullFromDbEntity(c)
                }
            }.collect { list ->
                Log.d("update", "${list.size}")
                _cycleList.update { list }
                _stateLoading.value = false
            }
        }
    }

    override fun getAllDefault() {
        Log.d("getAllPersonal", "getAllPersonal")
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val listEn = cycleRepoDB
                .getAllFullEntityDefault()
            listEn.map {
                it.map { c ->
                    Cycle.mapFullFromDbEntity(c)
                }
            }.collect {list ->
                Log.d("update","${list.size}")
                _cycleList.update{ list}
                // _cycleList.value = it
                _stateLoading.value = false
            }
        }
    }


    override fun delete(context: Context, image: String, id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTempImg(image, context)
            cycleRepoDB.delete(id)
        }
    }

    private fun deleteTempImg(tempImage: String, context: Context) {
        if (tempImage.isBlank()) return
        try {
            val file = Uri.parse(tempImage).toFile()
            if (file.exists()) {
                file.delete()
            }
        } catch (e: IllegalArgumentException) {
            Log.d("delete", "image is not add")
            Log.d("delete", "${e.message}")
        }
    }
}