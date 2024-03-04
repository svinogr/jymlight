package info.upump.web.model

    data class Sets(
        var weight: Double = 0.0,
        var reps: Int = 0,
        var weightPast: Double = 0.0
    ) : BaseModelRet()