package info.upump.database.repo

import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.SetsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetsRepo @Inject constructor(db: RoomDB) :
    RepoActionsSpecific<SetsEntity, SetsEntity> {
    private val setsDao = db.setsDao()

    override fun getFullEntityBy(id: Long): Flow<SetsEntity> {
        return setsDao.getBy(id)
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<SetsEntity>> {
        return setsDao.getByParent(id)
    }

    override fun getAllFullEntity(): Flow<List<SetsEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityTemplate(): Flow<List<SetsEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityPersonal(): Flow<List<SetsEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityDefault(): Flow<List<SetsEntity>> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        setsDao.deleteById(id)
    }

    override fun deleteByParent(parentId: Long) {
        setsDao.deleteByParentId(parentId)
    }

    override fun deleteChilds(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun update(item: SetsEntity): SetsEntity {
        setsDao.update(item)
        return item
    }

    override fun save(item: SetsEntity): SetsEntity {
        val id = setsDao.save(item)
        return item.apply { _id = id }
    }
}