package info.upump.jymlight.models.entity

import info.upump.jymlight.R


enum class TypeMuscle {
    NECK {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.neck
    },
    SHOULDER {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.shoulder
    },
    TRAPS {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.traps
    },
    PECTORAL {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.pectoral
    },
    TRICEPS {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.triceps
    },
    BICEPS {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.biceps
    },
    BACK {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.back
    },
    GLUTES {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.glutes
    },
    QUADS {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.quades
    },
    HAMSTRINGS {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.hamstring
    },
    CALVES {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.calves
    },
    ABS {
        override var img: Int = R.drawable.ic_menu_camera
        override var title: Int = R.string.abs
    };

    abstract val img: Int
    abstract  val title: Int
}