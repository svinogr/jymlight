package info.upump.web.model

import java.util.Date

class WorkoutRet(
    var isWeekEven: Boolean = false,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var day: String = "MONDAY",
    var exercises: MutableList<ExerciseRet> = ArrayList(),
    var id: Long = 0,
    var title: String = "",
    var startDate: Date = Date(),
    var finishDate: Date = Date(),
    var comment: String = "",
    var parentId: Long = 0
)