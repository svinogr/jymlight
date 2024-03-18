package info.upump.database.repo.db

import android.util.Log
import androidx.room.Transaction
import info.upump.database.RepoActionsSpecific
import info.upump.database.RepoInterface
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class CycleRepoDB private constructor(db: RoomDB) :
    RepoInterface<CycleEntity, CycleFullEntityWithWorkouts> {
    private val cycleDao = db.cycleDao()
    private val workoutRepo = WorkoutRepo.get()

    companion object {
        private var instance: CycleRepoDB? = null

        fun initialize(db: RoomDB) {
            instance = CycleRepoDB(db)
        }

        fun get(): RepoActionsSpecific<CycleEntity, CycleFullEntityWithWorkouts> {
            return instance ?: throw IllegalStateException("first need initialize repo")
        }
    }

    override fun getFullEntityBy(id: Long): Flow<CycleFullEntityWithWorkouts> {
        return cycleDao.getWithWorkouts(id)
    }

    override fun getAllFullEntity(): Flow<List<CycleFullEntityWithWorkouts>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<CycleFullEntityWithWorkouts>> {
        TODO("Not yet implemented")
    }


    override fun getAllFullEntityTemplate(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllTemplate()
    }

    override fun save(item: CycleEntity): CycleEntity {
        Log.d("save", "id = ${item._id}")
        if (item._id == 0L) {
            val newId = cycleDao.save(item)
            item.apply { _id = newId }
        } else cycleDao.update(item)

        return item
    }

    override fun getAllFullEntityPersonal(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllPersonalCycles()
    }

    fun getAllFullestEntityPersonal(): List<CycleFullEntity> {
        return cycleDao.getAllFullPersonalCyclesNotFlow()
    }

    override fun getAllFullEntityDefault(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllTemplate()
    }

    suspend fun copyToPersonal(id: Long, today: String) {
        cycleDao.getFullCycle(id)
            .take(1)
            .onEach {
                Log.d("copyToPersonal", "copyToPersonal")
                it.cycleEntity.default_type = 0
                it.listWorkoutEntity.forEach { w ->
                    w.workoutEntity.default_type = 0
                    w.workoutEntity.template = 0
                    w.listExerciseEntity.forEach() { e ->
                        e.exerciseEntity.default_type = 0
                        e.exerciseEntity.template = 0
                    }
                }

            }
            .collect { c ->
                copy(listOf(c), today)
            }
    }

    @Transaction
    override fun delete(id: Long) {
        cycleDao.delete(id)
        workoutRepo.deleteByParent(id)
    }

    override fun deleteByParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteChilds(parentId: Long) {
        Log.d("clean", "$parentId")
        workoutRepo.deleteByParent(parentId)
    }

    override fun update(item: CycleEntity): CycleEntity {
        cycleDao.update(item)
        return item
    }

    private suspend fun copy(list: List<CycleFullEntity>, today: String) {
        val listForDbWrite = mutableListOf<Deferred<CycleEntity>>()
        val workoutsRepo = WorkoutRepo.get()
        coroutineScope {
            for (cycle in list) {
                val cycleE =
                    async {
                        val c = cycle.cycleEntity
                        c._id = 0
                        if (today.isNotBlank()) {
                            c.start_date = today
                            c.finish_date = today
                        }

                        val id = cycleDao.save(c)

                        for (workout in cycle.listWorkoutEntity) {
                            val repoW = workoutsRepo

                            workout.workoutEntity._id = 0
                            workout.workoutEntity.parent_id = id

                            if (today.isNotBlank()) {
                                workout.workoutEntity.start_date = today
                                workout.workoutEntity.finish_date = today
                            }

                            val idW = repoW.save(workout.workoutEntity)._id

                            val repoE = ExerciseRepo.get()

                            for (exercise in workout.listExerciseEntity) {

                                exercise.exerciseEntity._id = 0
                                exercise.exerciseEntity.parent_id = idW
                                val idE = repoE.save(exercise.exerciseEntity)._id

                                val repoS = SetsRepo.get()

                                for (sets in exercise.listSetsEntity) {

                                    sets._id = 0
                                    sets.parent_id = idE
                                    repoS.save(sets)
                                }
                            }
                        }

                        return@async c
                    }
                listForDbWrite.add(cycleE)
            }
        }

        val f = System.currentTimeMillis()
        listForDbWrite.awaitAll()
    }

    suspend fun saveFullEntitiesOnlyFromOtherDB(list: List<CycleFullEntity>) {
        copy(list, "")
    }

}