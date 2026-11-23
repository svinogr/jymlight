package info.upump.jymlight.ui.screens.viewmodel.cycle

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.upump.database.repo.CycleRepo
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// для списка всех cycles
@HiltViewModel
class CycleVM @Inject constructor(
    private val cycleRepo: CycleRepo,
) : BaseVMWithStateLoad() {
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())
    val cycleList: StateFlow<List<Cycle>> = _cycleList.asStateFlow()

    suspend fun getAllPersonal() {
        // kimi по запросу закоментить Log.d("getAllPersonal", "getAllPersonal")
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val listEn = cycleRepo.getAllFullEntityPersonal()
            listEn.map {
                it.map { c ->
                    Cycle.mapFullFromDbEntity(c)
                }
            }.collect {list ->
                // kimi по запросу закоментить Log.d("update","${list.size}")
                _cycleList.update{ list}
                _stateLoading.value = false
            }
        }
    }

    fun getAllDefault() {
        // kimi по запросу закоментить Log.d("getAllPersonal", "getAllPersonal")
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val listEn = cycleRepo.getAllFullEntityDefault()
            listEn.map {
                it.map { c ->
                    Cycle.mapFullFromDbEntity(c)
                }
            }.collect {list ->
                // kimi по запросу закоментить Log.d("update","${list.size}")
                _cycleList.update{ list}
                // _cycleList.value = it
                _stateLoading.value = false
            }
        }
    }

    fun delete(context: Context, image: String,  id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTempImg( image, context)
            cycleRepo.delete(id)
        }
    }

    private fun deleteTempImg(tempImage: String, context: Context) {
        if (tempImage.isBlank()) return
        try {
            val file = Uri.parse(tempImage).toFile()
            if (file.exists()) {
                file.delete()
            }
        }catch (e: IllegalArgumentException) {
            // kimi по запросу закоментить Log.d("delete", "image is not add")
            // kimi по запросу закоментить Log.d("delete", "${e.message}")
        }
    }
}