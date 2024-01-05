package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.ExerciseDescription
import info.upump.jymlight.models.entity.Sets
import info.upump.jymlight.models.entity.TypeMuscle
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.SetsItemCard
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustomBottom
import info.upump.jymlight.ui.screens.screenscomponents.screen.TableHeader
import info.upump.jymlight.ui.theme.MyTextTitleLabel20

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListWorkoutForReview(
    list: List<Exercise>, modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.background)
    ) {
        list.groupBy { it.exerciseDescription!!.title }.forEach { title, list ->

            stickyHeader {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp, top = 0.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp),
                        style = MyTextTitleLabel20,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

            }
            item {
                TableHeader()
            }
            itemsIndexed(list, key = { index, item -> item.id }) { index, item ->
                item.setsList.forEachIndexed() { index, it ->
                    Column {
                        SetsItemCard(
                            sets = it,
                            number = index
                        ) {}
                        DividerCustomBottom()
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ListWorkoutForReviewPreview() {
    val list = listOf(
        Exercise().apply {
            id = 1
            title = "First"
            typeMuscle = TypeMuscle.ABS
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(), Sets(), Sets(), Sets(), Sets(), Sets()
            )
            descriptionId = 1
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        },
        Exercise().apply {
            id = 2
            title = "Second"
            typeMuscle = TypeMuscle.BACK
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(), Sets(), Sets()
            )
            descriptionId = 2
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        },
        Exercise().apply {
            id = 3
            title = "Thead"
            typeMuscle = TypeMuscle.CALVES
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets()
            )
            descriptionId = 3
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        }
    )
    ListWorkoutForReview(list)
}
