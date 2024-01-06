package info.upump.database

import kotlinx.coroutines.flow.Flow

interface RepoActionsSpecific<T, R> : RepoActions<T> {
    fun getFullEntityBy(id: Long): Flow<R>
    fun getAllFullEntityByParent(parentId: Long): Flow<List<R>>
    fun getAllFullEntity(): Flow<List<R>>
    fun getAllFullEntityTemplate(): Flow<List<R>>
    fun getAllFullEntityPersonal(): Flow<List<R>>
    fun getAllFullEntityDefault(): Flow<List<R>>
}