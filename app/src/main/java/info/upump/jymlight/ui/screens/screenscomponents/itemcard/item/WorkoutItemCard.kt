package info.upump.jymlight.ui.screens.screenscomponents.itemcard.item

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Workout
import info.upump.jymlight.ui.theme.MyTextLabel12
import info.upump.jymlight.ui.theme.MyTextTitleLabel16


@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun WorkoutItemCard(
    workout: Workout,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    SideEffect {
        Log.d("recom", "$workout")
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                action()
            },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background)
        ) {
            val filterColor = context.getColor(workout.day.getColor())

            Image( modifier = Modifier
                .padding(8.dp)
                .height(50.dp)
                .width(50.dp)
                .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.day_background),
                contentDescription = "",

                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color(filterColor), BlendMode.Multiply)
            )
            Column(modifier = modifier.fillMaxWidth()) {
                val modifierCol = Modifier.padding(end = 8.dp)
                Text(
                    text = workout.title.capitalize(),
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.fillMaxWidth()
                )
                Box(
                    modifier = modifierCol
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = context.getString(workout.day.title()),
                        style = MyTextLabel12
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewWorkoutCard() {
    val workout = Workout(
        isWeekEven = false, isDefaultType = false,
        isTemplate = false, day = Day.FRIDAY, exercises = listOf()
    ).apply { title = "Новая" }
    val context = LocalContext.current
    WorkoutItemCard(workout = workout, ::println)
}

