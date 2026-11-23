package info.upump.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long=0,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var comment: String? = "",

    @ColumnInfo
    var week_even: Int = 0,

    @ColumnInfo
    var default_type: Int = 0,

    @ColumnInfo
    var template: Int = 0,

    @ColumnInfo
    var day: String? = "",

    @ColumnInfo
    var start_date: String = "",

    @ColumnInfo
    var finish_date: String = "",

    @ColumnInfo
    var parent_id: Long? = 0)