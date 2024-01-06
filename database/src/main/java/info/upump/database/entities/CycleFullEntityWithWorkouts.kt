package info.upump.database.entities

import androidx.room.Embedded
import androidx.room.Relation


data class CycleFullEntityWithWorkouts(
    @Embedded
    var cycleEntity: CycleEntity,
    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = WorkoutEntity::class)
    var listWorkoutEntity: List<WorkoutEntity>
) {

    constructor() : this(CycleEntity(), listOf())

    override fun toString(): String {
        return "CycleFullEntityWithWorkouts(cycleEntity=$cycleEntity, listWorkoutEntity=$listWorkoutEntity)"
    }
}

data class  CycleFullEntity(
    @Embedded
    var cycleEntity: CycleEntity,
    @Relation(parentColumn = "_id", entityColumn = "parent_id", entity = WorkoutEntity::class)
    var listWorkoutEntity: List<WorkoutFullEntity>

) {
    override fun toString(): String {
        return "CycleFullEntity(cycleEntity=$cycleEntity, listWorkoutEntity=$listWorkoutEntity)"
    }
}



