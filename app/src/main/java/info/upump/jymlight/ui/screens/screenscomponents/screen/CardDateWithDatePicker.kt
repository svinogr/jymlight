package info.upump.jymlight.ui.screens.screenscomponents.screen

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Entity
import info.upump.jymlight.ui.screens.viewmodel.cycle.CycleVMCreateEdit
import info.upump.jymlight.ui.theme.MyTextTitleLabelWithColor
import java.util.Date

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarDatedWithDatePickerPreview() {
    val cycle = CycleVMCreateEdit.vmOnlyForPreview
    DateCardWithDatePicker("2023-08-28", ::print, "2023-08-29", ::println)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCardWithDatePicker(
    startDate: String,
    updateStartString: (Date) -> Unit,
    finishDate: String,
    updateFinishString: (Date) -> Unit,
    modifier: Modifier = Modifier,
    readonly: Boolean = true,
    context: Context = LocalContext.current
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
            // colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
           //     .padding(start = 4.dp, end = 4.dp)
        ) {
            Row(modifier = Modifier) {
                val modifierValue = Modifier.padding(top = 4.dp, bottom = 4.dp)
                TextField(modifier = modifierValue.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        containerColor = MaterialTheme.colorScheme.background,
                        textColor = MaterialTheme.colorScheme.onBackground
                    ),
                    readOnly = readonly,
                    value = startDate,
                    onValueChange = {
                    },
                    label = {
                        Text(
                            modifier = Modifier.clickable {
                                datePickerDialog(startDate, context) { date ->
                                    updateStartString(date)
                                }.show()
                            },
                            text = stringResource(id = R.string.label_start_cycle),
                            style = MyTextTitleLabelWithColor
                        )
                    }
                )

                TextField(modifier = modifierValue
                    .weight(1f)
                    .clickable {

                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        containerColor = MaterialTheme.colorScheme.background,
                        textColor = MaterialTheme.colorScheme.onBackground
                    ),
                    readOnly = true,
                    value = finishDate,
                    onValueChange = {
                    },
                    label = {
                        Text(
                            modifier = Modifier.clickable {
                                datePickerDialog(finishDate, context) { date ->
                                    updateFinishString(date)
                                }.show()
                            },
                            text = stringResource(id = R.string.label_finish_cycle),
                            style = MyTextTitleLabelWithColor,
                        )
                    }
                )
            }
        }
    }
}

fun datePickerDialog(date: String, context: Context, updateText: (Date) -> Unit): DatePickerDialog {
    val c = Calendar.getInstance()
    c.time = Entity.formatStringToDate(date)
    val y = c.get(Calendar.YEAR)
    val m = c.get(Calendar.MONTH)
    val d = c.get(Calendar.DAY_OF_MONTH)

    val di = DatePickerDialog(
        context,
        { _: DatePicker, mY: Int, mM: Int, mD: Int ->
            c.set(mY, mM, mD)
            updateText(c.time)
        }, y, m, d
    )

    return di
}