package info.upump.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_description")
data class ExerciseDescriptionEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long=0,
    @ColumnInfo
    var default_img: String? = "",

    @ColumnInfo
    var img: String? = "",

    @ColumnInfo
    var title: String? = "")

