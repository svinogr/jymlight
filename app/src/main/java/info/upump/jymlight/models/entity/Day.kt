package info.upump.jymlight.models.entity

import info.upump.jymlight.R


enum class Day {
    MONDAY {
        override fun getColor(): Int {
            return R.color.monday
        }

        override fun title(): Int {
            return R.string.day_monday
        }
    },
    TUESDAY {
        override fun getColor(): Int {
            return R.color.tuesday
        }

        override fun title(): Int {
            return R.string.day_tuesday
        }
    },
    WEDNESDAY {
        override fun getColor(): Int {
            return R.color.wednesday
        }

        override fun title(): Int {
            return R.string.day_wednesday
        }
    },
    THURSDAY {
        override fun getColor(): Int {
            return R.color.thursday
        }

        override fun title(): Int {
            return R.string.day_thursday
        }
    },
    FRIDAY {
        override fun getColor(): Int {
            return R.color.friday
        }

        override fun title(): Int {
            return R.string.day_friday
        }
    },
    SATURDAY {
        override fun getColor(): Int {
            return R.color.saturday
        }

        override fun title(): Int {
            return R.string.day_saturday
        }
    },
    SUNDAY {
        override fun getColor(): Int {
            return R.color.sunday
        }

        override fun title(): Int {
            return R.string.day_sunday
        }
    };

    abstract fun getColor(): Int
    abstract fun title(): Int
}