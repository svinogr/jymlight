package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert
    fun save(exercise: ExerciseEntity): Long

    @Query("select * from exercises")
    fun getAll(): List<ExerciseEntity>

    @Query("select * from exercises where parent_id= :parentId")
    fun getAllByParent(parentId: Long): Flow<List<ExerciseFullEntity>>

    @Transaction
    @Query("select * from exercises where parent_id=:id")
    fun getFullEntityByParent(id: Long): Flow<List<ExerciseFullEntity>>

    @Transaction
    @Query("select * from exercises where _id= :id")
    fun getFullEntityBy(id: Long): Flow<ExerciseFullEntity>

    @Transaction
    @Query("select * from exercises where template = 1")
    fun getAllFullEntities(): Flow<List<ExerciseFullEntity>>

    @Query("delete from exercises where _id = :id")
    fun deleteBy(id: Long)

    @Query("delete from exercises where parent_id = :parentId")
    fun deleteByParent(parentId: Long)

    @Query("select _id from exercises where parent_id = :parentId")
    fun getListIdForNextByParent(parentId: Long): List<Long>

    @Query("select * from exercises where template = 1")
    fun getAllFullTemplateEntity(): Flow<List<ExerciseFullEntity>>
}