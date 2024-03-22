package info.upump.web.client

import info.upump.web.model.WorkoutRet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val API_PATH_WORKOUT = "api/workout"

interface WorkoutRetrofitClient {

    @GET("$API_PATH_WORKOUT/full/{id}")
    fun getWorkoutFullById(@Path("id") id: Long): Call<WorkoutRet>


}