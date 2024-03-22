package info.upump.web.client

import info.upump.web.model.ExerciseRet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val API_PATH_EXERCISE = "api/exercise"

interface ExerciseRetrofitClient {
    @GET("$API_PATH_EXERCISE/{id}")
    fun getExerciseFullById(@Path("id") id: Long): Call<ExerciseRet>
}