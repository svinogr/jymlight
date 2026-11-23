package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.ExerciseItemCard
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListDefaultExercise(
    list: List<Exercise>,
    lazyListState: LazyListState,
    navHost: NavHostController,
    modifier: Modifier = Modifier,
    actionDel: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        state = lazyListState
    ) {
        item() {
            EmptyItem(size = 2.dp)
        }
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            val state = remember {
                mutableStateOf(false)
            }

            val dismissState = rememberDismissState(confirmStateChange = { value ->
                true
            })
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(),
                background = {

                    ItemSwipeBackgroundIcon(
                        dismissState = dismissState,
                        state = state.value,
                        actionDelete = {}
                    )
                },
                dismissContent = {

                    Column(modifier = Modifier) {
                        ExerciseItemCard(exercise = it, navHost = navHost) {
                            state.value = true
                            navHost.navigate(
                                NavigationItem.DefaultDetailExerciseNavigationItem.routeWithId(
                                    it
                                )
                            )
                        }
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }
        item() {
            EmptyItem()
        }
    }
}
