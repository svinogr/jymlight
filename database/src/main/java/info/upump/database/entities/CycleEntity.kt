package info.upump.database.entities

import androidx.annotation.TransitionRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cycles")
data class CycleEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var comment: String? = "",

    @ColumnInfo
    var default_type: Int = 0,

    @ColumnInfo
    var img: String? = "",

    @ColumnInfo
    var start_date: String = "",

    @ColumnInfo
    var finish_date: String = "",

    @ColumnInfo
    var default_img: String? = "")
