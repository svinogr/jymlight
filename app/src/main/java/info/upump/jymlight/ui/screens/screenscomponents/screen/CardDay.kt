package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDay(day: Day, isEven: Boolean, modifier: Modifier = Modifier) {

    var expanded by remember {
        mutableStateOf(false)
    }
    val colorDay = colorResource(day.getColor())

    val brush = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to Color.White,
            0.5F to colorDay
        ), 0.10f, Float.POSITIVE_INFINITY, TileMode.Clamp
    )

    val colorForTextField = TextFieldDefaults.textFieldColors(
        disabledTextColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        containerColor = Color.Transparent
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
        )
    ) {
        Box(
            modifier = Modifier              // для фона
                .fillMaxHeight()
                .fillMaxWidth()
                .background(brush, alpha = 0.30f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.Top) {
                    val modifierValue = Modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp, top = 4.dp, end = 0.dp, bottom = 4.dp)
                    TextField(modifier = modifierValue.weight(1f),
                        colors = colorForTextField,
                        readOnly = true,
                        value = stringResource(id = day.title()),
                        onValueChange = {
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.label_spinner_day),
                                style = MyTextTitleLabelWithColor
                            )
                        }
                    )

                    TextField(
                        modifier = modifierValue
                            .weight(1f),
                        colors = colorForTextField,
                        readOnly = true,
                        value = if (isEven) {
                            stringResource(id = R.string.week_not_even)
                        } else {
                            stringResource(id = R.string.week_not_even)
                        },
                        onValueChange = {
                        },
                        label = {
                            Text(
                                text = "Неделя",
                                style = MyTextTitleLabelWithColor,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardPreview() {
    CardDay(Day.SATURDAY, true)
}
