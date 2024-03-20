package info.upump.jymlight.models.entity

import info.upump.database.entities.WorkoutEntity
import info.upump.database.entities.WorkoutFullEntity
import info.upump.web.model.WorkoutRet
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Workout(
    var isWeekEven: Boolean = false,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var day: Day = Day.MONDAY,
    var exercises: List<Exercise> = ArrayList(),
    var id: Long = 0,
    var title: String = "",
    var startDate: Date = Date(),
    var finishDate: Date = Date(),
    var comment: String = "",
    var parentId: Long = 0,
) {
    companion object {
        fun mapFromDbEntity(workoutEntity: WorkoutEntity): Workout {
            val workout = Workout(
                isWeekEven = workoutEntity.week_even == 1,
                isDefaultType = workoutEntity.default_type == 1,
                isTemplate = workoutEntity.default_type == 1,
                day = Day.valueOf(workoutEntity.day!!),
                exercises = mutableListOf<Exercise>()
            )
            workout.title = workoutEntity.title
            workout.parentId = workoutEntity.parent_id!!
            workout.id = workoutEntity._id
            workout.comment = workoutEntity.comment!!
            workout.setStartDate(workoutEntity.start_date)
            workout.setFinishDate(workoutEntity.finish_date)

            return workout
        }

        fun mapToEntity(workout: Workout): WorkoutEntity {
            val workoutEntity = WorkoutEntity(workout.id)
            workoutEntity.title = workout.title
            workoutEntity.start_date = workout.startStringFormatDate
            workoutEntity.finish_date = workout.finishStringFormatDate
            workoutEntity.comment = workout.comment
            workoutEntity.day = workout.day.toString()
            workoutEntity.default_type = if (workout.isDefaultType) 1 else 0
            workoutEntity.week_even = if (workout.isWeekEven) 1 else 0
            workoutEntity.template = if (workout.isTemplate) 1 else 0
            workoutEntity.template = 0
            workoutEntity.parent_id = workout.parentId

            return workoutEntity
        }

        fun mapFromFullDbEntity(entity: WorkoutFullEntity): Workout {
            val listEntityWorkout = entity.listExerciseEntity
            val listExercises = mutableListOf<Exercise>()

            listEntityWorkout.forEach() {
                listExercises.add(Exercise.mapFromFullDbEntity(it))
            }

            val workout = Workout(
                isWeekEven = entity.workoutEntity.week_even == 1,
                isDefaultType = entity.workoutEntity.default_type == 1,
                isTemplate = entity.workoutEntity.default_type == 1,
                day = Day.valueOf(entity.workoutEntity.day!!),
                exercises = listExercises
            )
            workout.title = entity.workoutEntity.title
            workout.parentId = entity.workoutEntity.parent_id!!
            workout.id = entity.workoutEntity._id
            workout.comment = entity.workoutEntity.comment!!
            workout.setStartDate(entity.workoutEntity.start_date)
            workout.setFinishDate(entity.workoutEntity.finish_date)

            return workout
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

        fun mapFromRetEntity(workoutRet: WorkoutRet): Workout {
            val workout = Workout(
                isWeekEven = workoutRet.isWeekEven,
                isDefaultType = workoutRet.isDefaultType,
                isTemplate = workoutRet.isTemplate,
                day = Day.valueOf(workoutRet.day),
                exercises = mutableListOf<Exercise>()
            )
            workout.title = workoutRet.title
            workout.parentId = workoutRet.parentId
            workout.id = workoutRet.id
            workout.comment = workoutRet.comment
            workout.setStartDate(Entity.formatDateToString(workoutRet.startDate))
            workout.setFinishDate(Entity.formatDateToString(workoutRet.finishDate))

            return workout
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

    override fun toString(): String {
        return "Workout($title  --  $id  -- $parentId  isWeekEven=$isWeekEven, isDefaultType=$isDefaultType, isTemplate=$isTemplate, day=$day, exercises=$exercises) "
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Workout

        if (isWeekEven != other.isWeekEven) return false
        if (isDefaultType != other.isDefaultType) return false
        if (isTemplate != other.isTemplate) return false
        if (day != other.day) return false
        if (id != other.id) return false
        if (title != other.title) return false
        if (startDate != other.startDate) return false
        if (finishDate != other.finishDate) return false
        if (comment != other.comment) return false
        if (parentId != other.parentId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isWeekEven.hashCode()
        result = 31 * result + isDefaultType.hashCode()
        result = 31 * result + isTemplate.hashCode()
        result = 31 * result + day.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + finishDate.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + parentId.hashCode()
        return result
    }
}