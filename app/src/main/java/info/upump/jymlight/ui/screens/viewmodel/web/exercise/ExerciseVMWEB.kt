package info.upump.jymlight.ui.screens.viewmodel.web.exercise

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.ExerciseRepo
import info.upump.database.repo.db.SetsRepo
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.Sets
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.db.exercise.ExerciseVMInterface
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.ExerciseRet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseVMWEB : BaseVMWithStateLoad(),
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

        val call = RetrofitServiceWEB.getExerciseService().getExerciseFullById(id)
        call.enqueue(object : Callback<ExerciseRet> {
            override fun onResponse(call: Call<ExerciseRet>, response: Response<ExerciseRet>) {
                Log.d("id", "get ex by $id resp")
                val eR = response.body()
                val exercise = Exercise.mapFromFullRetEntity(eR!!)
                _listSets.update { exercise.setsList }
                Log.d("id", "get by $exercise resp")
                _imageDescription.update { exercise.exerciseDescription!!.img }
                _imageDescriptionDefault.update { exercise.exerciseDescription!!.defaultImg }
                _stateLoading.value = false
            }

            override fun onFailure(call: Call<ExerciseRet>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }
        )
    }

    override fun deleteSub(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val setsService = RetrofitServiceWEB.getSetService().deleteById(id)
            setsService.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        val index = _listSets.value.indexOfFirst { it.id == id }
                        val list = _listSets.value.toMutableList()
                        list.removeAt(index)
                        _listSets.update { list }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun cleanItem() {
        viewModelScope.launch(Dispatchers.IO) {
            val setsService = RetrofitServiceWEB.getExerciseService().clean(parentId)
            setsService.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        _listSets.update { mutableListOf<Sets>() }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
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