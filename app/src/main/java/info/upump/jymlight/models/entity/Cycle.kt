package info.upump.jymlight.models.entity

import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts
import info.upump.jymlight.models.entity.Workout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Cycle(
    var workoutList: List<Workout> = ArrayList(),
    var isDefaultType: Boolean = false,
    var image: String = "",
    var imageDefault: String = "",
    var id: Long = 0,
    var title: String = "",
    var startDate: Date = Date(),
    var finishDate: Date = Date(),
    var comment: String = "",
    var parentId: Long = 0,
) {
    private val daysBetweenDates: Int
        get() = 0

    override fun toString(): String {
        return "Cycle{" +
                "id= " + id +
                ", coment= " + comment +
                ", title= " + title +
                ", startDate=" + startStringFormatDate +
                ", finishDate=" + finishStringFormatDate +
                ", userList=" + workoutList.size +
                ", image=" + image +
                ", defaultImg=" + imageDefault +
                '}'
    }





    /*    override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + if (image != null) image.hashCode() else 0
            return result
        }*/


    companion object {
        fun mapFromDbEntity(entity: CycleEntity): Cycle {
            val cycle = Cycle(
                workoutList = listOf(),
                isDefaultType = entity.default_type == 1,

                imageDefault = entity.default_img ?: ""
            )
            cycle.title = entity.title
            cycle.id = entity._id
            cycle.setStartDate(entity.start_date)
            cycle.setFinishDate(entity.finish_date)
            cycle.comment = entity.comment!!
            cycle.image = entity.img ?: ""
            cycle.imageDefault = entity.default_img ?: ""

            return cycle
        }

        fun mapToEntity(cycle: Cycle): CycleEntity {
            val cycleEntity = CycleEntity(cycle.id)
            cycleEntity.title = cycle.title.orEmpty()
            cycleEntity.start_date = cycle.startStringFormatDate
            cycleEntity.finish_date = cycle.finishStringFormatDate
            cycleEntity.comment = cycle.comment
            if (cycle.image.isBlank()) {
                cycleEntity.img = null
            } else {
                cycleEntity.img = cycle.image
            }

            if (cycle.imageDefault.isBlank()) {
                cycleEntity.default_img = null
            } else {
                cycleEntity.default_img = cycle.imageDefault
            }

            return cycleEntity
        }

        fun copy(cycle: Cycle): Cycle {
            return Cycle().apply {
                id = cycle.id
                title = cycle.title
                isDefaultType = cycle.isDefaultType
                image = cycle.image
                startDate = cycle.startDate
                finishDate = cycle.finishDate
                comment = cycle.comment
                parentId = cycle.parentId
            }
        }

        fun mapFullFromDbEntity(entity: CycleFullEntityWithWorkouts): Cycle {
            val listEntityWorkout = entity.listWorkoutEntity
            val listWorkout = mutableListOf<Workout>()

            listEntityWorkout.forEach() {
                listWorkout.add(Workout.mapFromDbEntity(it))
            }


            val cycle = Cycle(
                workoutList = listWorkout,
                isDefaultType = entity.cycleEntity.default_type == 1,

                imageDefault = entity.cycleEntity.default_img ?: ""
            )

            cycle.title = entity.cycleEntity.title
            cycle.id = entity.cycleEntity._id
            cycle.setStartDate(entity.cycleEntity.start_date)
            cycle.setFinishDate(entity.cycleEntity.finish_date)
            cycle.comment = entity.cycleEntity.comment!!
            cycle.image = entity.cycleEntity.img ?: ""
            cycle.imageDefault = entity.cycleEntity.default_img ?: ""

            return cycle
        }

        fun formatDateToString(date: Date): String {
            val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            return simpleDateFormat.format(date)
        }

        fun formatStringToDate(stringDate: String): Date {
            val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            var date = Date()
            try {
                date = simpleDateFormat.parse(stringDate)!!
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }
    }

    val startStringFormatDate: String
        get() {
            return Entity.formatDateToString(startDate)
        }

    fun setStartDate(stringDate: String) {
        startDate = Entity.formatStringToDate(stringDate)
    }

    val finishStringFormatDate: String
        get() {
            return Entity.formatDateToString(finishDate)
        }

    fun setFinishDate(stringDate: String) {
        finishDate = Entity.formatStringToDate(stringDate)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cycle

        if (isDefaultType != other.isDefaultType) return false
        if (image != other.image) return false
        if (imageDefault != other.imageDefault) return false
        if (id != other.id) return false
        if (title != other.title) return false
        if (startDate != other.startDate) return false
        if (finishDate != other.finishDate) return false
        if (comment != other.comment) return false
        if (parentId != other.parentId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isDefaultType.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + imageDefault.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + finishDate.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + parentId.hashCode()
        return result
    }
}

