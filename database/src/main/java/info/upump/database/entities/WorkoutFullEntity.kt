package info.upump.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutFullEntity(
    @Embedded
    var workoutEntity: WorkoutEntity,
    @Relation(
        parentColumn = "_id", entityColumn = "parent_id", entity = ExerciseEntity::class
    )
    var listExerciseEntity: List<ExerciseFullEntity>
) {

    constructor() : this(WorkoutEntity(), listOf())

    override fun toString(): String {
        return "WorkoutFullEntity(workoutEntity=$workoutEntity, listExerciseEntity=$listExerciseEntity)"
    }
}

