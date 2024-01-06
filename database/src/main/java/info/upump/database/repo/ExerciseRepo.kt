package info.upump.database.repo

import android.content.Context
import android.util.Log
import androidx.room.Transaction
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepo private constructor(db: RoomDB) :
    RepoActionsSpecific<ExerciseEntity, ExerciseFullEntity> {
    private val exerciseDao = db.exerciseDao()
    private val setsRepo = SetsRepo.get()

    companion object {
        private var instance: ExerciseRepo? = null

        fun initialize(db: RoomDB) {

            instance = ExerciseRepo(db)

        }

        fun get(): RepoActionsSpecific<ExerciseEntity, ExerciseFullEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }


    override fun getAllFullEntityByParent(parentId: Long): Flow<List<ExerciseFullEntity>> {
        return exerciseDao.getAllByParent(parentId)
    }

    @Transaction
    override fun delete(id: Long) {
        exerciseDao.deleteBy(id)
    }

    @Transaction
    override fun deleteByParent(parentId: Long) {
        val listParentIdForNext = exerciseDao.getListIdForNextByParent(parentId)
        exerciseDao.deleteByParent(parentId)
        listParentIdForNext.forEach {
            setsRepo.deleteByParent(it)
        }
    }

    override fun deleteChilds(parentId: Long) {
        TODO("Not yet implemented")
    }


    override fun update(item: ExerciseEntity): ExerciseEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: ExerciseEntity): ExerciseEntity {
        Log.d("Save exercise", "save ExerciseEntity")
        val id = exerciseDao.save(item)
        item._id = id

        return item
    }

    override fun getFullEntityBy(id: Long): Flow<ExerciseFullEntity> {
        return exerciseDao.getFullEntityBy(id)
    }


    override fun getAllFullEntity(): Flow<List<ExerciseFullEntity>> {
        return exerciseDao.getAllFullEntities()
    }

    override fun getAllFullEntityTemplate(): Flow<List<ExerciseFullEntity>> {
        return exerciseDao.getAllFullTemplateEntity()

    }

    override fun getAllFullEntityPersonal(): Flow<List<ExerciseFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityDefault(): Flow<List<ExerciseFullEntity>> {
        TODO("Not yet implemented")
    }
}
