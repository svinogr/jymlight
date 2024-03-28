package info.upump.web.client

import info.upump.web.model.CycleRet
import info.upump.web.model.ExerciseRet
import retrofit2.Call
import retrofit2.http.GET

const val API_TEMPLATE = "api/templates"
interface TemplateRetrofitClient {
    @GET("$API_TEMPLATE/cycle")
    fun getAllTemplateCycle(): Call<List<CycleRet>>

    @GET("$API_TEMPLATE/exercise")
    fun getAllTemplateExercise(): Call<List<ExerciseRet>>
}