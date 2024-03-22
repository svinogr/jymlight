package info.upump.web.model

class ExerciseRet (
        var typeMuscle: String = "CALVES",
        var isDefaultType: Boolean = false,
        var isTemplate: Boolean = false,
        var setsList: MutableList<SetsRet> = ArrayList(),
        var descriptionId: Long = 0,
        var exerciseDescription: ExerciseDescriptionRet? = null,

        ) : BaseModelRet()