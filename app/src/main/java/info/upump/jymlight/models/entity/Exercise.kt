package info.upump.jymlight.models.entity

import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity

class Exercise(
    var typeMuscle: TypeMuscle = TypeMuscle.CALVES,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var setsList: MutableList<Sets> = ArrayList(),
    var descriptionId: Long = 0,
    var exerciseDescription: ExerciseDescription? = null,

    ) : Entity() {

    override fun toString(): String {
        return "Exercise{" +
                "template" + isTemplate +
                ", desc " + descriptionId +
                ", typeMuscle=" + typeMuscle +
                ", defaultType=" + isDefaultType +
                ", setsList=" + setsList.size +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId + "descr" + exerciseDescription.toString()
        '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Exercise

        if (typeMuscle != other.typeMuscle) return false
        if (isDefaultType != other.isDefaultType) return false
        if (isTemplate != other.isTemplate) return false
        if (descriptionId != other.descriptionId) return false
        if (exerciseDescription != other.exerciseDescription) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + typeMuscle.hashCode()
        result = 31 * result + isDefaultType.hashCode()
        result = 31 * result + isTemplate.hashCode()
        result = 31 * result + descriptionId.hashCode()
        result = 31 * result + (exerciseDescription?.hashCode() ?: 0)
        return result
    }


    companion object {
        fun mapFromDbEntity(entity: ExerciseEntity): Exercise {
            val exercise = Exercise()
            exercise.id = entity._id
            exercise.parentId = entity.parent_id!!
            exercise.descriptionId = entity.description_id!!
            exercise.typeMuscle = TypeMuscle.valueOf(entity.type_exercise!!)
            exercise.isTemplate = entity.template == 1

            return exercise
        }

        fun mapToEntity(exercise: Exercise): ExerciseEntity {
            val exerciseEntity = ExerciseEntity()
            exerciseEntity._id = exercise.id
            exerciseEntity.parent_id = exercise.parentId
            exerciseEntity.description_id = exercise.descriptionId
            exerciseEntity.comment = exercise.comment
            exerciseEntity.type_exercise = exercise.typeMuscle.toString()
            exerciseEntity.default_type = if(exercise.isDefaultType) 1 else 0
            exerciseEntity.template = if(exercise.isTemplate) 1 else 0
            exerciseEntity.start_date = exercise.startStringFormatDate
            exerciseEntity.finish_date = exercise.finishStringFormatDate

            return exerciseEntity
        }

        fun mapFromFullDbEntity(entity: ExerciseFullEntity): Exercise {
            val exercise = mapFromDbEntity(entity.exerciseEntity)
            val exerciseDescription =
                ExerciseDescription.mapFromDbEntity(entity.exerciseDescriptionEntity)
            val listSets = mutableListOf<Sets>()

            entity.listSetsEntity.forEach {
                val set = Sets.mapFromDbEntity(it)
                listSets.add(set)
            }
            exercise.exerciseDescription = exerciseDescription
            exercise.comment = entity.exerciseEntity.comment!!
            exercise.setsList = listSets

            return exercise
        }
    }
}