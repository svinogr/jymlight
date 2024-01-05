package info.upump.jymlight.models.entity

import info.upump.database.entities.ExerciseDescriptionEntity

class ExerciseDescription(
    var id: Long = 0,
    var img: String = "",
    var title: String = "",
    var defaultImg: String = ""
) {

    override fun toString(): String {
        return "ExerciseDescription{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", title='" + title + " $defaultImg}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExerciseDescription

        if (id != other.id) return false
        if (img != other.img) return false
        if (title != other.title) return false
        if (defaultImg != other.defaultImg) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + img.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + defaultImg.hashCode()
        return result
    }

    companion object {
        fun mapFromDbEntity(entity: ExerciseDescriptionEntity): ExerciseDescription {
            val exerciseDescription = ExerciseDescription(entity._id)
            exerciseDescription.img = entity.img ?: ""
            exerciseDescription.defaultImg = entity.default_img ?: ""
            exerciseDescription.title = entity.title!!

            return exerciseDescription
        }
    }

}