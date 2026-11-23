package info.upump.jymlight.ui.screens.screenscomponents

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import info.upump.jymlight.R


/* Color of divider  defined in @style night and light theme
<style name="PickerTheme" parent="JymTheme">
<item name="colorControlNormal">@color/cardview_dark_background</item>
</style>*/
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@SuppressLint("InflateParams")
fun NumberPicker(
    min: Int,
    max: Int,
    initialState: Int,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    value: (Int) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.picker, null)
            val picker = view.findViewById<NumberPicker>(R.id.numberPicker)
            picker.textColor = textColor.toArgb()
            picker.minValue = min
            picker.maxValue = max
            picker.descendantFocusability = NumberPicker.FOCUS_BEFORE_DESCENDANTS

            val valuesForDisplay = Array<String>(size = max + 1) {
                (min + (it)).toString()
            }

            picker.displayedValues = valuesForDisplay
            picker.setOnValueChangedListener { p0, p1, p2 -> value(p2) }

            return@AndroidView picker
        },
        update = {
            val i = it.displayedValues.indexOf(initialState.toString())
            it.value = i
        }
    )
}
