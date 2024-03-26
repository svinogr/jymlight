package info.upump.web.client

import info.upump.web.model.CycleRet
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

const val API_PATH_CYCLE = "api/cycle"

interface CycleRetrofitClient {
    @GET("$API_PATH_CYCLE/templates")
     fun getAllTemplateCycle(): Call<MutableList<CycleRet>>

    @GET("$API_PATH_CYCLE/all/{id}")
     fun getAllByParentId(@Path("id") id: Long): Call<List<CycleRet>>

    // названия file и переменная cycle должны совпадать с отправлеными данными в клиенте
    @POST(API_PATH_CYCLE)
    @Multipart
     fun saveCycle(
        @Part("cycle") cycle: CycleRet,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>

    @GET("$API_PATH_CYCLE/{id}")
     fun getCycleById(@Path("id") id: Long): Call<CycleRet>

    @GET("$API_PATH_CYCLE/full/{id}")
     fun getCycleFullById(@Path("id") id: Long): Call<CycleRet>

    @DELETE("$API_PATH_CYCLE/{id}")
    fun deleteById(@Path("id") id: Long): Call<ResponseBody>
}