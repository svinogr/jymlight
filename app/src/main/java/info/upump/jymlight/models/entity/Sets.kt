package info.upump.jymlight.models.entity

import info.upump.database.entities.SetsEntity
import info.upump.web.model.SetsRet

class Sets(
    var weight: Double = 0.0,
    var reps: Int = 0,
    var weightPast: Double = 0.0
) : Entity() {

    override fun toString(): String {
        return "Sets{" +
                "weight=" + weight +
                ", reps=" + reps +
                ", formatDate='" + '\'' +
                ", id=" + id +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Sets

        if (weight != other.weight) return false
        if (reps != other.reps) return false
        if (weightPast != other.weightPast) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + reps
        result = 31 * result + weightPast.hashCode()
        return result
    }

    companion object {
        fun mapFromDbEntity(entity: SetsEntity): Sets {
            val sets = Sets(
                weight = entity.weight!!,
                reps = entity.reps!!,
                weightPast = entity.past_set!!
            )
            sets.id = entity._id
            sets.parentId = entity.parent_id!!

            return sets
        }

        fun mapToEntity(newSets: Sets): SetsEntity {
            return SetsEntity().apply {
                _id = newSets.id
                parent_id = newSets.parentId
                weight = newSets.weight
                reps = newSets.reps
                past_set = newSets.weightPast
            }
        }

        fun mapFromFullRetEntity(entity: SetsRet): Sets {
            return Sets().apply {
                id = entity.id
                weight = entity.weight
                weightPast = entity.weightPast
                reps = entity.reps
            }
        }

        fun mapToRetSets(sets: Sets): SetsRet {
            return SetsRet().apply {
                id = sets.id
                weight = sets.weight
                weightPast = sets.weightPast
                reps = sets.reps
            }
        }
    }
}