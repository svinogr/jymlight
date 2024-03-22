package info.upump.jymlight.ui.screens.screenscomponents.itemcard.item

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import info.upump.jymlight.IS_LOCALDB
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.ExerciseDescription
import info.upump.jymlight.models.entity.Sets
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ItemImage
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.web.ItemImageWeb
import info.upump.jymlight.ui.theme.MyTextLabel12
import info.upump.jymlight.ui.theme.MyTextTitleLabel16

const val DEFAULT_IMAGE = "drew"

@Composable
fun ExerciseItemCard(
    exercise: Exercise,
    navHost: NavController,
    modifier: Modifier = Modifier,
    actionNav: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp).clickable { actionNav(exercise.id) },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                    MaterialTheme.colorScheme.background)
        )
        {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
            ) {
                if(IS_LOCALDB) {
                    ItemImage(
                        image = exercise.exerciseDescription!!.img,
                        defaultImage = exercise.exerciseDescription!!.defaultImg
                    ) {
                        Log.d("click", "click")
                        navHost.navigate(
                            NavigationItem.ReviewExerciseScreenNavigationItem.routeWithId(
                                exercise.id
                            )
                        )
                    }

                } else{
                    Log.d("id", "image ${exercise.exerciseDescription!!.defaultImg}")
                    ItemImageWeb(
                        image = exercise.exerciseDescription!!.img,
                        defaultImage = exercise.exerciseDescription!!.defaultImg
                    ) {
                        Log.d("click", "click")
                        navHost.navigate(
                            NavigationItem.ReviewExerciseScreenNavigationItem.routeWithId(
                                exercise.id
                            )
                        )
                    }
                }
            }
            Column(modifier = modifier.fillMaxWidth()) {
                val modifierCol = Modifier
                    .padding(end = 8.dp)
                Text(
                    text = exercise.exerciseDescription!!.title!!.capitalize(),
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.fillMaxWidth()
                )

                if (!exercise.isTemplate) {
                    Box(
                        modifier = modifierCol
                            .align(Alignment.End)
                            .padding(top = 4.dp)
                    ) {
                        Text(
                            text = getExerciseString(exercise),
                            style = MyTextLabel12,

                            )
                    }
                }
            }
        }
    }
}

fun getExerciseString(exercise: Exercise): String {
    val textResult = StringBuilder()
    val setList = exercise.setsList
    if (setList.size > 1) {
        setList.sortedWith { o1, o2 ->
            if (o1.reps < o2.reps) {
                return@sortedWith -1
            } else if (o1.reps == o2.reps) {
                return@sortedWith 0
            } else return@sortedWith 1
        }
        if (setList[0].reps == setList[setList.size - 1].reps) {
            textResult.append(setList.size).append(" x ").append(setList[0].reps)
        } else textResult.append(setList.size).append(" x ").append(setList[0].reps).append(" - ")
            .append(setList[setList.size - 1].reps)
    } else if (setList.size == 1) {
        textResult.append("1 x ").append(setList[0].reps)

    } else textResult.append(0)

    return textResult.toString()
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewExerciseItemCard() {
    val exerdescription =
        ExerciseDescription(img = "", defaultImg = "uk1", title = "Новое упраж")

    val listSets = listOf(
        Sets(20.0, 10, 15.0),
        Sets(20.0, 10, 15.0),
        Sets(20.0, 10, 15.0)
    )

    val exercise = Exercise(
        isDefaultType = true,
        isTemplate = true, exerciseDescription = exerdescription
    ).apply { title = "Новое упраж" }

    exercise.setsList = mutableListOf()
    ExerciseItemCard(exercise = exercise, navHost = NavController(LocalContext.current)) {}

}