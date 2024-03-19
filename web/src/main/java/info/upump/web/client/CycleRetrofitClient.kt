package info.upump.web.client

import info.upump.web.model.CycleRet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

const val API_PATH_CYCLE = "api/cycle"

interface CycleRetrofitClient {
    @GET("$API_PATH_CYCLE/templates")
   suspend fun getAllTemplateCycle(): Call<MutableList<CycleRet>>



    // названия file и переменная cycle должны совпадать с отправлеными данными в клиенте
    @POST(API_PATH_CYCLE)
    @Multipart
    suspend fun saveCycle(@Part("cycle") cycle: CycleRet, @Part file : MultipartBody.Part): Response<ResponseBody>
}