package info.upump.web.client

import info.upump.web.model.SetsRet
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

const val API_PATH_SETS = "api/sets"

interface SetsRetrofitClient {
    @DELETE("$API_PATH_SETS/{id}")
    fun deleteById(@Path("id") id: Long): Call<ResponseBody>

    @GET("$API_PATH_SETS/{id}")
    fun getById(@Path("id") id: Long): Call<SetsRet>

    @PUT(API_PATH_SETS)
    fun update(@Body sets: SetsRet): Call<ResponseBody>

    @PUT(API_PATH_SETS)
    fun save(@Body listSets: List<SetsRet>): Call<ResponseBody>
}