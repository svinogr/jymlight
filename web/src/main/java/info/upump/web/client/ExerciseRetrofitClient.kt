package info.upump.web.client

import info.upump.web.model.ExerciseRet
import info.upump.web.model.WorkoutRet
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

const val API_PATH_EXERCISE = "api/exercise"

interface ExerciseRetrofitClient {
    @GET("$API_PATH_EXERCISE/{id}")
    fun getExerciseFullById(@Path("id") id: Long): Call<ExerciseRet>

    @DELETE("$API_PATH_EXERCISE/{id}/clean")
    fun clean(@Path("id") id: Long): Call<ResponseBody>

    @POST("$API_PATH_EXERCISE/{id}/copy")
    fun copy(@Path("id") id: Long, @Body workoutRet: WorkoutRet): Call<ResponseBody>
    @DELETE("$API_PATH_EXERCISE/{id}")
    fun deleteById(@Path("id") id: Long): Call<ResponseBody>
}