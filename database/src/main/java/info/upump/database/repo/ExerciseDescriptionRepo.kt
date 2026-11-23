package info.upump.database.repo

import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.ExerciseDescriptionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseDescriptionRepo @Inject constructor(db: RoomDB) :
    RepoActionsSpecific<ExerciseDescriptionEntity, ExerciseDescriptionEntity> {
    private val exerciseDescriptionDao = db.exerciseDescriptionDao()


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