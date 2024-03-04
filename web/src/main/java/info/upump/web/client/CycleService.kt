package info.upump.web.client

import info.upump.web.model.CycleRet
import retrofit2.Call
import retrofit2.http.GET

interface CycleService {
    @GET("api/cycle/templates")
    fun getAllTemplateCycle(): Call<MutableList<CycleRet>>
}