package info.upump.web.model

data class CycleRet(
    var workoutList: MutableList<WorkoutRet> = ArrayList(),
    var isDefaultType: Boolean = false,
    var image: String = "",
    var imageDefault: String = "",
    var id: Long = 0,
    var title: String = "",
    var startDate: String = "",
    var finishDate: String = "",
    var comment: String = "",
    var parentId: Long = 0,
)
