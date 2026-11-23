package info.upump.jymlight.models.entity

class CycleCheck(val cycle: Cycle, var isCheck: Boolean = false) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CycleCheck

        if (cycle != other.cycle) return false
        return isCheck == other.isCheck
    }

    override fun hashCode(): Int {
        var result = cycle.hashCode()
        result = 31 * result + isCheck.hashCode()
        return result
    }

    override fun toString(): String {
        return "CycleCheck(cycle=${cycle.id}, isCheck=$isCheck)"
    }

}