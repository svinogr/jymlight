package info.upump.jymlight.ui.screens.viewmodel.web.sets

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.SetsRepo
import info.upump.jymlight.models.entity.Sets
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.db.sets.SetsVMInterface
import info.upump.web.RetrofitServiceWEB
import info.upump.web.model.SetsRet
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

class SetsVMWEB : BaseVMWithStateLoad(), SetsVMInterface {
    private val _sets = MutableStateFlow<Sets>(Sets())
    override val item: StateFlow<Sets> = _sets.asStateFlow()

    private val _id = MutableStateFlow(0L)
    override val id: StateFlow<Long> = _id.asStateFlow()

    private val _parentId = MutableStateFlow(0L)
    override val parentId: StateFlow<Long> = _parentId.asStateFlow()

    private val _weight = MutableStateFlow(0.0)
    override val weight: StateFlow<Double> = _weight.asStateFlow()

    private val _reps = MutableStateFlow(0)
    override val reps: StateFlow<Int> = _reps.asStateFlow()

    private val _weightPast = MutableStateFlow(0.0)
    override val weightPast: StateFlow<Double> = _weightPast.asStateFlow()
    private var tempWeight: Double = 0.0

    private val _quantity = MutableStateFlow(0)
    override val quantity: StateFlow<Int> = _quantity.asStateFlow()
    override fun updateReps(reps: Int) {
        _reps.update { reps }
    }

    override fun updateWeight(weight: Double) {
        //  if (tempWeight !=)
        //  _weightPast.update { _weight.value }
        _weight.update { weight }
    }

    override fun updatePastWeight(weight: Double) {
        _weightPast.update { weight }
    }

    override fun updateQuantity(quantity: Int) {
        _quantity.update { quantity }
    }

    override fun updateParentId(parentId: Long) {
        _parentId.update { parentId }
    }

    override fun getByParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun getBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val setsService = RetrofitServiceWEB.getSetService().getById(id)
            setsService.enqueue(object : Callback<SetsRet> {
                override fun onResponse(call: Call<SetsRet>, response: Response<SetsRet>) {
                    if (response.code() == 200) {
                        with(Sets.mapFromFullRetEntity(response.body()!!)) {
                            _id.update { id }
                            tempWeight = weight
                            _reps.update { reps }
                            _weight.update { weight }
                            _weightPast.update { weightPast }
                            _parentId.update { parentId }
                        }

                    }
                }

                override fun onFailure(call: Call<SetsRet>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun save(backFunction: () -> Unit) {
        if (quantity.value == 0 && id.value == 0L) return

        val sets = Sets().apply {
            id = this@SetsVMWEB.id.value
            parentId = this@SetsVMWEB.parentId.value
            reps = this@SetsVMWEB.reps.value
            weight = this@SetsVMWEB.weight.value
            weightPast = this@SetsVMWEB._weightPast.value
        }
        sets.weightPast = tempWeight

        if (quantity.value != 0) {
            val listSets = mutableListOf<SetsRet>()
            for (i in 0..<quantity.value) {
                listSets.add(Sets.mapToRetSets(sets))
            }

            viewModelScope.launch(Dispatchers.IO) {
                val setsService = RetrofitServiceWEB.getSetService().save(listSets)
                setsService.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.code() == 200) {
                            backFunction()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        //TODO("Not yet implemented")
                    }
                })
            }

        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val setsService = RetrofitServiceWEB.getSetService().update(Sets.mapToRetSets(sets))
                setsService.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.d("code", "${response.code()}")
                        if (response.code() == 204) {
                            backFunction()
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
            }

        }
    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : SetsVMInterface {
                private val _sets = MutableStateFlow<Sets>(Sets())
                override val item: StateFlow<Sets> = _sets.asStateFlow()

                private val _id = MutableStateFlow(0L)
                override val id: StateFlow<Long> = _id.asStateFlow()

                private val _parentId = MutableStateFlow(0L)
                override val parentId: StateFlow<Long> = _parentId.asStateFlow()

                private val _weight = MutableStateFlow(0.0)
                override val weight: StateFlow<Double> = _weight.asStateFlow()

                private val _reps = MutableStateFlow(0)
                override val reps: StateFlow<Int> = _reps.asStateFlow()

                private val _weightPast = MutableStateFlow(0.0)
                override val weightPast: StateFlow<Double> = _weightPast.asStateFlow()

                private val _quantity = MutableStateFlow(0)
                override val quantity: StateFlow<Int> = _quantity.asStateFlow()

                override fun updateReps(reps: Int) {
                    TODO("Not yet implemented")
                }

                override fun updateWeight(weight: Double) {
                    TODO("Not yet implemented")
                }

                override fun updatePastWeight(weight: Double) {
                    TODO("Not yet implemented")
                }

                override fun updateQuantity(quantity: Int) {
                    TODO("Not yet implemented")
                }

                override fun updateParentId(parentId: Long) {
                    TODO("Not yet implemented")
                }

                override fun getByParent(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save(backFunction: () -> Unit) {
                    TODO("Not yet implemented")
                }

            }
        }
    }
}