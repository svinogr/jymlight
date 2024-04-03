package info.upump.web.client

import info.upump.web.model.CycleRet
import info.upump.web.model.ExerciseRet
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

const val API_PATH_TEMPLATE = "api/templates"
interface TemplateRetrofitClient {
    @GET("$API_PATH_TEMPLATE/cycle/all")
    fun getAllTemplateCycle(): Call<List<CycleRet>>

    @POST("$API_PATH_TEMPLATE/cycle/{id}/copy")
    fun copyToPersonal(@Path("id") id: Long): Call<ResponseBody>

    @GET("$API_PATH_TEMPLATE/exercise")
    fun getAllTemplateExercise(): Call<List<ExerciseRet>>
}