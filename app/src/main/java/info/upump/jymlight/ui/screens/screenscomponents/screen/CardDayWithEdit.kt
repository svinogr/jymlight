package info.upump.jymlight.ui.screens.screenscomponents.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Day

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDaydWorkoutEdit(
    day: Day,
    updateDay: (Day) -> Unit,
    isEven: Boolean,
    updateEven: (Boolean) -> Unit, focus: FocusManager,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val widhtOne = remember {
        mutableStateOf(0)
    }

    val widhtMenu = remember {
        mutableStateOf(0)
    }

    val offset = remember {
        mutableStateOf(0)
    }


    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
            // containerColor = colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        )
        {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                    focus.clearFocus()
                    val o = widhtOne.value - widhtMenu.value
                    offset.value = o / 2
                    Log.d("size3 ", "${offset.value}")
                }
            ) {

                AssistChip(
                    modifier = Modifier
                        .menuAnchor()
                        .onSizeChanged {
                            Log.d("size ", "${it.width}")
                            widhtOne.value = it.width
                        },
                    onClick = {
                    },
                    label = {
                        Text(
                            text = stringResource(id = day.title()),
                            color =  MaterialTheme.colorScheme.onBackground
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.background
                        //containerColor = colorResource(id = R.color.colorBackgroundChips)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            " ",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )

                DropdownMenu(
                    modifier = Modifier
                        .onSizeChanged {
                            Log.d("size2 ", "${it.width}")
                            widhtMenu.value = it.width
                        }
                        .background(
                            //colorResource(id = R.color.colorBackgroundCardView)),
                            MaterialTheme.colorScheme.background
                        ),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }) {
                    Day.values().forEach {
                        DropdownMenuItem(modifier = Modifier,
                            contentPadding = PaddingValues(4.dp),
                            onClick = {
                                updateDay(it)
                                expanded = false
                            }) {
                            AssistChip(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    updateDay(it)
                                    expanded = false
                                },
                                label = {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = stringResource(id = it.title()),
                                            modifier = Modifier.align(Alignment.Center),
                                            color =  MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    //  containerColor = colorResource(id = R.color.colorBackgroundChips)
                                    containerColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }
            }
            AssistChip(
                modifier = Modifier
                    .padding(end = 8.dp),

                onClick = { updateEven(!isEven) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                label = {
                    if (isEven) {
                        Text(
                            text = stringResource(id = R.string.week_even),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.week_not_even),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardWorkoutEditPreview() {

    CarDaydWorkoutEdit(
        Day.TUESDAY,

        ::println,
        true,
        ::println,
        focus = LocalFocusManager.current
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardWorkoutEditPreview1() {
    CarDaydWorkoutEdit(
        Day.FRIDAY,

        ::println,
        true,
        ::println,
        focus = LocalFocusManager.current
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DayCardWorkoutEditPreview2() {

    CarDaydWorkoutEdit(
        Day.SATURDAY,

        ::println,
        false,
        ::println,
        focus = LocalFocusManager.current
    )
}


fun Day.next(): Day {
    val values = Day.values()
    val next = (ordinal + 1) / values.size
    return values[next]
}