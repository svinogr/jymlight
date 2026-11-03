package info.upump.database


import android.content.Context
import androidx.room.Room
import info.upump.database.repo.CycleRepo
import info.upump.database.repo.ExerciseDescriptionRepo
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.SetsRepo
import info.upump.database.repo.WorkoutRepo

open class DatabaseApp(val context: Context) {
    companion object {
        lateinit var db: RoomDB

        fun initializeDb(context: Context, name: String = RoomDB.BASE_NAME) {
            db = Room.databaseBuilder(context, RoomDB::class.java, name)
                .addMigrations(RoomDB.MIGRATION_1to2)
                .build()

            SetsRepo.initialize(db)
            ExerciseRepo.initialize(db)
            WorkoutRepo.initialize(db)
            CycleRepo.initialize(db)
            ExerciseDescriptionRepo.initialize(db)
        }
    }
}
