package info.upump.jymlight.ui.screens.screenscomponents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import info.upump.jymlight.R

@SuppressLint("InflateParams")
@Composable
fun NumberPickerWithStep(
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    min: Double,
    max: Int,
    step: Double,
    initialState: Double,
    weightFun: (Double) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.picker, null)
            val picker = view.findViewById<NumberPicker>(R.id.numberPicker)

            val iStepsArray = 800;
            val valuesForDisplay = Array<String>(size = iStepsArray) {
                (min + (it * step)).toString()
            }
            picker.textColor = textColor.toArgb()
            picker.minValue = 0
            picker.maxValue = 200
            picker.displayedValues = valuesForDisplay
            picker.descendantFocusability = NumberPicker.FOCUS_BEFORE_DESCENDANTS
            picker.setOnValueChangedListener {p0, p1, p2 ->

                weightFun(picker.displayedValues[p2].toDouble()) }
            
            return@AndroidView picker
        },
        update = {
            val i = it.displayedValues.indexOf(initialState.toString())
            it.value = i
        }
    )
}
