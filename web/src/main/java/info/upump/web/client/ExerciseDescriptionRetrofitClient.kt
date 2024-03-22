package info.upump.web.client

import info.upump.web.model.ExerciseDescriptionRet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val API_PATH_EXERCISE_DESCRIPTION = "api/exercise_description"
interface ExerciseDescriptionRetrofitClient {

    @GET("API_PATH_EXERCISE_DESCRIPTION/{id}")
    fun getExerciseDescriptionById(@Path("id") id: Long): Call<ExerciseDescriptionRet>
}