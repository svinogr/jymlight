package info.upump.database.repo.db

import android.annotation.SuppressLint
import android.util.Log
import androidx.room.Transaction
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.WorkoutEntity
import info.upump.database.entities.WorkoutFullEntity
import kotlinx.coroutines.flow.Flow

class WorkoutRepo private constructor(db: RoomDB) :
    RepoActionsSpecific<WorkoutEntity, WorkoutFullEntity> {
    private val workoutDao = db.workoutDao()
    private val exerciseRepo = ExerciseRepo.get()

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: WorkoutRepo? = null

        fun initialize( db: RoomDB) {
            instance = WorkoutRepo(db)
        }

        fun get(): RepoActionsSpecific<WorkoutEntity, WorkoutFullEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

    @Transaction
    override fun getFullEntityBy(id: Long): Flow<WorkoutFullEntity> {
        return workoutDao.getById(id)
    }

    @Transaction
    override fun getAllFullEntityByParent(parentId: Long): Flow<List<WorkoutFullEntity>> {
        return workoutDao.getAllByParent(parentId)
    }

    @Transaction
    override fun getAllFullEntity(): Flow<List<WorkoutFullEntity>> {
        TODO("Not yet implemented")
    }

    @Transaction
    override fun getAllFullEntityTemplate(): Flow<List<WorkoutFullEntity>> {
        TODO("Not yet implemented")
    }

    @Transaction
    override fun getAllFullEntityPersonal(): Flow<List<WorkoutFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityDefault(): Flow<List<WorkoutFullEntity>> {
        TODO("Not yet implemented")
    }

    override fun update(item: WorkoutEntity): WorkoutEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: WorkoutEntity): WorkoutEntity {
        if (item._id == 0L) {
            val id = workoutDao.save(item)
            item._id = id
        } else {
            Log.d("save", "${item._id}")
            Log.d("save", "${item.parent_id}")
            workoutDao.update(item)
        }

        return item
    }

    @Transaction
    override fun delete(id: Long) {
        workoutDao.delete(id)
        exerciseRepo.deleteByParent(id)
    }

    override fun deleteByParent(parentId: Long) {
        val listParentIdForNext = workoutDao.getListIdForNextByParent(parentId)
        workoutDao.deleteBy(parentId)
        listParentIdForNext.forEach {
            exerciseRepo.deleteByParent(it)
        }
    }

    override fun deleteChilds(parentId: Long) {
        TODO("Not yet implemented")
    }
}