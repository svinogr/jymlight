package info.upump.database.repo

import android.content.Context
import info.upump.database.RepoActions
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseDescriptionEntity
import kotlinx.coroutines.flow.Flow

class ExerciseDescriptionRepo private constructor(db: RoomDB) :
    RepoActionsSpecific<ExerciseDescriptionEntity, ExerciseDescriptionEntity> {
    private val exerciseDescriptionDao = db.exerciseDescriptionDao()

    companion object {
        private var instance: ExerciseDescriptionRepo? = null

        fun initialize(db: RoomDB) {
            instance = ExerciseDescriptionRepo(db)
        }

        fun get(): RepoActions<ExerciseDescriptionEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }


    override fun getFullEntityBy(id: Long): Flow<ExerciseDescriptionEntity> {
        return exerciseDescriptionDao.getBy(id)
    }

    override fun getAllFullEntityByParent(parentId: Long): Flow<List<ExerciseDescriptionEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntity(): Flow<List<ExerciseDescriptionEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityTemplate(): Flow<List<ExerciseDescriptionEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityPersonal(): Flow<List<ExerciseDescriptionEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityDefault(): Flow<List<ExerciseDescriptionEntity>> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteByParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteChilds(parentId: Long) {
        TODO("Not yet implemented")
    }


    override fun update(setsGet: ExerciseDescriptionEntity): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }

    fun getByParent(id: Long): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }

    override fun save(item: ExerciseDescriptionEntity): ExerciseDescriptionEntity {
        TODO("Not yet implemented")
    }
}