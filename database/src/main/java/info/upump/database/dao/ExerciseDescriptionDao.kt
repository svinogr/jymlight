package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Query
import info.upump.database.entities.ExerciseDescriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDescriptionDao {
    @Query("select * from exercise_description")
    fun getAll(): List<ExerciseDescriptionEntity>

    @Query("select * from exercise_description where _id= :id")
    fun getBy(id: Long): Flow<ExerciseDescriptionEntity>

/*    @Query("select * from exercise_description where description_id= :id")
    fun getByParent(id: Long): ExerciseDescriptionEntity*/

}