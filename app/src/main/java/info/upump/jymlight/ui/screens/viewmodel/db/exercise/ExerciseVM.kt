package info.upump.jymlight.ui.screens.viewmodel.db.exercise

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.ExerciseRepo
import info.upump.database.repo.db.SetsRepo
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.Sets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ExerciseVM : info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad(),
    ExerciseVMInterface {
    private val _imageDescription = MutableStateFlow("")
    val imageDescription = _imageDescription.asStateFlow()

    private var parentId = 0L

    private val _imageDescriptionDefault = MutableStateFlow("")
    val imageDescriptionDefault = _imageDescriptionDefault.asStateFlow()

    private val _listSets = MutableStateFlow<MutableList<Sets>>(mutableListOf())

    override
    val subItems: StateFlow<List<Sets>> = _listSets

    override fun getBy(id: Long) {
        _stateLoading.value = true
        parentId = id
        viewModelScope.launch(Dispatchers.IO) {
            ExerciseRepo.get().getFullEntityBy(id).map { entity ->
                Exercise.mapFromFullDbEntity(entity)
            }.collect() {

                //  _listSets.value = mutableListOf()
                _listSets.value = it.setsList
                _imageDescription.value = it.exerciseDescription!!.img
                _imageDescriptionDefault.value = it.exerciseDescription!!.defaultImg
            }
            Log.d("get", "get")
            _stateLoading.value = false
        }
    }

    override fun deleteSub(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val sets = Sets.mapToEntity(Sets().apply { this.id = id })
            val repo = SetsRepo.get().delete(id)

            Log.d("delete", "${_listSets.value.size}")
            _listSets.value.removeIf {
                it.id == id
            }

        }
    }

    override fun cleanItem() {
        viewModelScope.launch(Dispatchers.IO) {
            SetsRepo.get().deleteByParent(parentId)
        }
    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : ExerciseVMInterface {
                private val _listSets = MutableStateFlow<List<Sets>>(
                    mutableListOf(Sets(), Sets(), Sets())
                )
                override val subItems: StateFlow<List<Sets>> = _listSets.asStateFlow()
                override fun getBy(id: Long) {
                }

                override fun deleteSub(id: Long) {
                    Log.d("delete", "$id")
                }

                override fun cleanItem() {
                    TODO("Not yet implemented")
                }

            }
        }
    }
}