package info.upump.jymlight.models.entity

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class User {
    private val formatDate = "yyyy-MM-dd"
    var id: Long = 0
    var name: String? = null
    var weight = 0.0
    var fat = 0.0
    var height = 0.0
    var neck = 0.0
    var pectoral = 0.0
    var shoulder = 0.0
    var leftBiceps = 0.0
    var rightBiceps = 0.0
    var abs = 0.0
    var leftLeg = 0.0
    var rightLeg = 0.0
    var leftCalves = 0.0
    var rightCalves = 0.0
    var date: Date? = null
    val stringFormatDate: String
        get() {
            val simpleDateFormat = SimpleDateFormat(formatDate, Locale.getDefault())
            return simpleDateFormat.format(date)
        }

    fun setDate(stringDate: String?) {
        val simpleDateFormat = SimpleDateFormat(formatDate, Locale.getDefault())
        try {
            date = simpleDateFormat.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return "User{" +
                "formatDate='" + formatDate + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", fat=" + fat +
                ", height=" + height +
                ", neck=" + neck +
                ", pectoral=" + pectoral +
                ", shoulder=" + shoulder +
                ", leftBiceps=" + leftBiceps +
                ", rightBiceps=" + rightBiceps +
                ", abs=" + abs +
                ", leftLeg=" + leftLeg +
                ", rightLeg=" + rightLeg +
                ", leftCalves=" + leftCalves +
                ", rightCalves=" + rightCalves +
                ", date=" + date +
                '}'
    }
}