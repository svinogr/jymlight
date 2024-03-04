package info.upump.jymlight.ui.screens.viewmodel.cycle

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.web.client.CycleService
import info.upump.web.client.RetrofitClient
import info.upump.web.model.CycleRet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// для списка всех cycles
class CycleVM : BaseVMWithStateLoad() {
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())
    val cycleList: StateFlow<List<Cycle>> = _cycleList.asStateFlow()
    private val cycleRepo = CycleRepo.get()

    suspend fun getAllPersonal() {
        Log.d("getAllPersonal", "getAllPersonal")
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
                Log.d("update","${list.size}")
                _cycleList.update{ list}
                _stateLoading.value = false
            }
        }
    }

    fun getAllDefault() {
        Log.d("getAllPersonal", "getAllPersonal")
        _stateLoading.value = true

        /*viewModelScope.launch(
            Dispatchers.IO
        ) {
            val listEn = cycleRepo.getAllFullEntityDefault()
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
        }*/


        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val c = RetrofitClient.getClient().create(CycleService::class.java)
            val allTemplateCycle = c.getAllTemplateCycle()
            val execute = allTemplateCycle.enqueue(object : Callback<MutableList<CycleRet>> {
                override fun onResponse(
                    call: Call<MutableList<CycleRet>>,
                    response: Response<MutableList<CycleRet>>
                ) {
                    Log.d("Init app", "${response.body()}")
                   val list =  response.body()!!.map {cR ->
                        val cycle = Cycle().apply {
                            id = cR.id
                            title = cR.title
                            comment = cR.comment
                            image = cR.image
                            imageDefault = cR.imageDefault
                            startDate = Cycle.formatStringToDate(cR.startDate)
                            finishDate = Cycle.formatStringToDate(cR.finishDate)
                            parentId = cR.parentId
                            isDefaultType = cR.isDefaultType
                            workoutList = listOf()
                        }

                       return@map cycle
                    }

                    _cycleList.update{ list}

                }

                override fun onFailure(call: Call<MutableList<CycleRet>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


      /*      val listEn = cycleRepo.getAllFullEntityDefault()
            listEn.map {
                it.map { c ->
                    Cycle.mapFullFromDbEntity(c)
                }
            }.collect {list ->
                Log.d("update","${list.size}")
                _cycleList.update{ list}
                // _cycleList.value = it
                _stateLoading.value = false
            }*/
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
            Log.d("delete", "image is not add")
            Log.d("delete", "${e.message}")
        }
    }
}