package info.upump.web.client

import info.upump.web.model.WorkoutRet
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

const val API_PATH_WORKOUT = "api/workout"

interface WorkoutRetrofitClient {

    @GET("$API_PATH_WORKOUT/full/{id}")
    fun getWorkoutFullById(@Path("id") id: Long): Call<WorkoutRet>

    @DELETE("$API_PATH_WORKOUT/{id}")
     fun deleteById(@Path("id") id: Long): Call<ResponseBody>

    @DELETE("$API_PATH_WORKOUT/{id}/clean")
     fun clean(@Path("id") id: Long): Call<ResponseBody>
}