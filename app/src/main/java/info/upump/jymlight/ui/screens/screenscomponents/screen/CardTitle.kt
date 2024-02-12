package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.Workout
import info.upump.jymlight.ui.theme.MyTextTitleLabelWithColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardTitle(
    text: String,
    isError: Boolean,
    updateText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
       //     colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .drawCustomIndicatorLine(
                BorderStroke(
                    DividerDefaults.Thickness,
                    MaterialTheme.colorScheme.background
                //    colorResource(R.color.colorBackgroundChips)
                ), 8.dp
            )
            .focusRequester(focusRequester),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
              //  focusedIndicatorColor = colorResource(R.color.colorBackgroundCardView),
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                //unfocusedIndicatorColor = colorResource(R.color.colorBackgroundCardView),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
               // containerColor = colorResource(R.color.colorBackgroundCardView),
                containerColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground
            ),
            value = text,
            onValueChange = {
                updateText(it)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.label_title_common),
                    style = MyTextTitleLabelWithColor
                )
            },
            //    isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = stringResource(id = R.string.error_tips_empty_field),
                        color = MaterialTheme.colorScheme.onError )
                }
            },
            placeholder = {
                Text(text = stringResource(id = R.string.label_title_common))
            }

        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardTitlePreviewCycleError() {
    val cycle = Cycle().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(cycle.title, true, f)
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreviewCycle() {
    val cycle = Cycle().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(cycle.title, false, f)
}


@Preview(showBackground = true)
@Composable
fun CardTitlePreviewWorkout() {
    val workout = Workout().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(workout.title, false, f)
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreviewWorkoutError() {
    val workout = Workout().apply {
        title = "its a Title"
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(workout.title, true, f)
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreviewWorkoutNotitle() {
    val workout = Workout().apply {
        title = ""
    }
    val f: (String) -> Unit = { println(it) }

    CardTitle(workout.title, false, f)
}

fun Modifier.drawCustomIndicatorLine(
    indicatorBorder: BorderStroke,
    indicatorPadding: Dp = 0.dp
): Modifier {

    val strokeWidthDp = indicatorBorder.width
    return drawWithContent {
        drawContent()
        if (strokeWidthDp == Dp.Hairline) return@drawWithContent
        val strokeWidth = strokeWidthDp.value * density
        val y = size.height - strokeWidth / 2
        drawLine(
            indicatorBorder.brush,
            Offset((indicatorPadding).toPx(), y),
            Offset(size.width - indicatorPadding.toPx(), y),
            strokeWidth
        )
    }
}