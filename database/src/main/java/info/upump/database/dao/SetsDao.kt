package info.upump.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import info.upump.database.entities.SetsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetsDao {
    @Query("select * from sets where parent_id= :parenId")
    fun getByParent(parenId: Long): Flow<List<SetsEntity>>

    @Query("select * from sets where _id= :id")
    fun getBy(id: Long): Flow<SetsEntity>

    @Insert()
    fun save(sets: SetsEntity): Long

    @Update
    fun update(item: SetsEntity)
    @Query("delete from sets where _id=:id")
     fun deleteById(id: Long)
     @Query("delete from sets where parent_id=:parenId")
     fun deleteByParentId(parenId: Long)
}