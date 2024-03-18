package info.upump.database


import android.content.Context
import android.util.Log
import androidx.room.Room
import info.upump.database.repo.db.CycleRepoDB
import info.upump.database.repo.db.ExerciseDescriptionRepo
import info.upump.database.repo.db.ExerciseRepo
import info.upump.database.repo.db.SetsRepo
import info.upump.database.repo.db.WorkoutRepo

import java.io.File

open class DatabaseApp (val context: Context) {
    companion object {
        lateinit var db: RoomDB

        fun initilizeDb(context: Context, name: String, path: String, isRestoring: Boolean = false ) {

            if(isRestoring){
                val file = File("$path/$name")
                Log.d("jhj", "initilizeDb: ${file.usableSpace} ${file.path} ")
                db =   Room
                    .databaseBuilder(context, RoomDB::class.java, name)
                    .createFromFile(file)
                    .addMigrations(RoomDB.MIGRATION_1to2)
                    .build()
            }else{
                // val file = File(path)
                val file = File("$path/$name")
                Log.d("jhj 2", "initilizeDb: ${file.path}")
                db = if (!file.exists()) {

                    Log.d("DatabaseApp", "file isnt exist")

                    Room.databaseBuilder(context, RoomDB::class.java, name)
                        .addMigrations(RoomDB.MIGRATION_1to2)
                        .createFromAsset(name)
                        .build()
                } else {
                    Room.databaseBuilder(context, RoomDB::class.java, name)
                        .addMigrations(RoomDB.MIGRATION_1to2)
                        .build()
                }
            }


            SetsRepo.initialize(db)
            ExerciseRepo.initialize(db)
            WorkoutRepo.initialize(db)
            CycleRepoDB.initialize(db)
            ExerciseDescriptionRepo.initialize(db)
        }
    }
}