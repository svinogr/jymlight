package info.upump.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleDao {
    @Query("select * from cycles")
    fun getAllCycles(): List<CycleEntity>

    @Insert()
    fun save(item: CycleEntity): Long

    @Update()
    fun update(item: CycleEntity): Int

    @Transaction
    @Query("select * from cycles where default_type = 0")
    fun getAllPersonalCycles(): Flow<List<CycleFullEntityWithWorkouts>>

    @Transaction
    @Query("select * from cycles where default_type = 0")
    fun getAllFullPersonalCycles(): Flow<List<CycleFullEntity>>


    @Query("select * from cycles where default_type = 0")
    fun getAllFullPersonalCyclesNotFlow(): List<CycleFullEntity>

    @Transaction
    @Query("select * from cycles where _id = :id")
    fun getFullCycle(id: Long): Flow<CycleFullEntity>

    @Transaction
    @Query("select * from cycles where default_type = 1")
    fun getAllTemplate(): Flow<List<CycleFullEntityWithWorkouts>>

    /*    @Query("select * from cycles where _id=:id")
        fun getBy(id: Long): Flow<CycleFullEntity>*/
    @Transaction
    @Query("select * from cycles where _id=:id")
    fun getWithWorkouts(id: Long): Flow<CycleFullEntityWithWorkouts>

    @Query("delete from cycles where _id=:id")
    fun delete(id: Long)

    @Transaction
    @Query("select * from cycles where _id=:id")
    fun exp(id: Long): Flow<CycleFullEntityWithWorkouts>
}

