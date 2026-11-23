package info.upump.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import info.upump.database.dao.CycleDao
import info.upump.database.dao.ExerciseDao
import info.upump.database.dao.ExerciseDescriptionDao
import info.upump.database.dao.SetsDao
import info.upump.database.dao.WorkoutDao
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.SetsEntity
import info.upump.database.entities.WorkoutEntity

@Database(
    version = 3, entities = [
        CycleEntity::class,
        WorkoutEntity::class,
        ExerciseEntity::class,
        ExerciseDescriptionEntity::class,
        SetsEntity::class]
)
abstract class RoomDB : RoomDatabase() {
    abstract fun cycleDao(): CycleDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseDescriptionDao(): ExerciseDescriptionDao
    abstract fun setsDao(): SetsDao

    companion object {
        const val BASE_NAME = "jym.db"
        const val BASE_NAME_RESTORE = "jymrestore.db"
        // добавляем новый столбец и не забываем увеличить версию в аннотации выше
        const val MIGRATION_1_TO_2_SQL = "ALTER TABLE sets ADD COLUMN past_set REAL"
        val MIGRATION_1to2: Migration = Migration(1, 2) {
            it.execSQL(MIGRATION_1_TO_2_SQL)
        }

        // старые строки получили past_set = NULL при миграции 1->2 (ALTER TABLE без DEFAULT)
        const val MIGRATION_2_TO_3_SQL = "UPDATE sets SET past_set = 0.0 WHERE past_set IS NULL"
        val MIGRATION_2to3: Migration = Migration(2, 3) {
            it.execSQL(MIGRATION_2_TO_3_SQL)
        }
    }


}