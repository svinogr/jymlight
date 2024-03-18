package info.upump.database.repo.db

import info.upump.database.DatabaseApp
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.SetsEntity
import kotlinx.coroutines.flow.Flow

class SetsRepo private constructor(db: RoomDB) :
    RepoActionsSpecific<SetsEntity, SetsEntity> {
    private val setsDao = DatabaseApp.db.setsDao()

    companion object {
        private var instance: SetsRepo? = null

        fun initialize(db: RoomDB) {
            instance = SetsRepo(db)

        }

        fun get(): RepoActionsSpecific<SetsEntity, SetsEntity> {
            return instance ?: throw IllegalStateException(" first need initialize repo")
        }
    }

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