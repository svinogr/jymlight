package info.upump.jymlight.ui.screens.viewmodel.web.cycle

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.CycleVMInterface
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.CycleRet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CycleVMWEB : BaseVMWithStateLoad(), CycleVMInterface {
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())
    private val cycleService = RetrofitServiceWEB.getCycleService()
    override val cycleList: StateFlow<List<Cycle>>
        get() = _cycleList.asStateFlow()


    override suspend fun getAllPersonal() {
        TODO("Not yet implemented")
    }

    override fun getAllDefault() {
        Log.d("init", "retro req")
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            Log.d("init", "send")
            val allTemplateCycle = cycleService.getAllTemplateCycle()
            allTemplateCycle.enqueue(object : Callback<MutableList<CycleRet>> {
                override fun onResponse(
                    call: Call<MutableList<CycleRet>>,
                    response: Response<MutableList<CycleRet>>
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

                override fun onFailure(call: Call<MutableList<CycleRet>>, t: Throwable) {
                    Log.d("init", "faulire")
                    _cycleList.update { listOf() }
                }
            })
        }
    }

    override fun delete(context: Context, image: String, id: Long) {
        TODO("Not yet implemented")
    }
}