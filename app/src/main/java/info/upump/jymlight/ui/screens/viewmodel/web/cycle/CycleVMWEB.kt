package info.upump.jymlight.ui.screens.viewmodel.web.cycle

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.jymlight.dataStore
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.CycleVMInterface
import info.upump.jymlight.utils.KeysForDataStore
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.CycleRet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class CycleVMWEB : BaseVMWithStateLoad(), CycleVMInterface {
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())
    override val cycleList: StateFlow<List<Cycle>>
        get() = _cycleList.asStateFlow()


    override suspend fun getAllPersonal(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = context.dataStore.data.map {
                it[KeysForDataStore.ID_USER] ?: 0
            }.first()

            val service = RetrofitServiceWEB.getCycleService().getAllByParentId(userId)
            service.enqueue(object : Callback<List<CycleRet>> {
                override fun onResponse(
                    call: Call<List<CycleRet>>,
                    response: Response<List<CycleRet>>
                ) {
                    val list = response.body()!!.map { cR ->
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

                    _cycleList.update { list }
                }

                override fun onFailure(call: Call<List<CycleRet>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun getAllDefault() {
        Log.d("init", "retro req")
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            Log.d("init", "send")
            val cycleService = RetrofitServiceWEB.getTemplateService()
            val allTemplateCycle = cycleService.getAllTemplateCycle()
            allTemplateCycle.enqueue(object : Callback<List<CycleRet>> {
                override fun onResponse(
                    call: Call<List<CycleRet>>,
                    response: Response<List<CycleRet>>
                ) {
                    Log.d("init", "succ")

                    val list = response.body()!!.map { cR ->
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

                    _cycleList.update { list }
                }

                override fun onFailure(call: Call<List<CycleRet>>, t: Throwable) {
                    Log.d("init", "faulire")
                    _cycleList.update { listOf() }
                }
            })
        }
    }

    override fun delete(context: Context, image: String, id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceWEB.getCycleService().deleteById(id)
            service.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        val index = _cycleList.value.indexOfFirst { it.id == id }
                        val list = _cycleList.value.toMutableList()
                        list.removeAt(index)

                        _cycleList.update { list }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }
            })

        }
    }
}