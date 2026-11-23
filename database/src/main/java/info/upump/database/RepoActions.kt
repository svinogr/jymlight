package info.upump.database

import kotlinx.coroutines.flow.Flow

interface RepoActions<T> {
    fun save(item: T): T
    fun update(item: T): T
    fun delete(id: Long)
    fun deleteByParent(parentId: Long)
    fun deleteChilds(parentId: Long)
}

