package info.upump.web.model

    data class SetsRet(
        var id: Long = 0,
        var weight: Double = 0.0,
        var reps: Int = 0,
        var weightPast: Double = 0.0,
        var parentId: Long = 0
    )