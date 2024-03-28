package info.upump.web

import info.upump.web.client.CycleRetrofitClient
import info.upump.web.client.ExerciseDescriptionRetrofitClient
import info.upump.web.client.ExerciseRetrofitClient
import info.upump.web.client.RetrofitClient
import info.upump.web.client.SetsRetrofitClient
import info.upump.web.client.TemplateRetrofitClient
import info.upump.web.client.WorkoutRetrofitClient
import info.upump.web.model.ExerciseDescriptionRet

class RetrofitServiceWEB {
    companion object {
        fun getSetService(): SetsRetrofitClient {
            return RetrofitClient.getClient().create(SetsRetrofitClient::class.java)
        }

        fun getCycleService(): CycleRetrofitClient {
            return RetrofitClient.getClient().create(CycleRetrofitClient::class.java)
        }

        fun getWorkoutService(): WorkoutRetrofitClient {
            return RetrofitClient.getClient().create(WorkoutRetrofitClient::class.java)
        }

        fun getExerciseService(): ExerciseRetrofitClient {
            return RetrofitClient.getClient().create(ExerciseRetrofitClient::class.java)
        }

        fun getExerciseDescriptionService(): ExerciseDescriptionRetrofitClient {
            return RetrofitClient.getClient().create(ExerciseDescriptionRetrofitClient::class.java)
        }

        fun getTemplateService(): TemplateRetrofitClient {
            return RetrofitClient.getClient().create(TemplateRetrofitClient::class.java)
        }
    }
}
