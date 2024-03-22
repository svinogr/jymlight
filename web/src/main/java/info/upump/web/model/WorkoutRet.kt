package info.upump.web.model

class WorkoutRet(
    var isWeekEven: Boolean = false,
    var isDefaultType: Boolean = false,
    var isTemplate: Boolean = false,
    var day: String = "MONDAY",
    var id: Long = 0,
    var title: String = "",
    var startDate: String = "",
    var finishDate: String ="",
    var comment: String = "",
    var parentId: Long = 0,
    var exercises: MutableList<ExerciseRet> = ArrayList(),
)