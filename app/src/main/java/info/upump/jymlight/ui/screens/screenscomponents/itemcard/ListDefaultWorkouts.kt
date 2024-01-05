package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Workout
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.WorkoutItemCard
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemDefaultsWorkouts(
    list: List<Workout>,
    lazyListState: LazyListState,
    navHost: NavHostController,
    modifier: Modifier = Modifier,
) {
    var isHasEvenWeek = 0
    list.forEach {
        if (it.isWeekEven) {
            isHasEvenWeek += 1
        }
    }
    if (isHasEvenWeek != list.size && isHasEvenWeek != 0) listDefaultWithGroup(
        list,
        modifier,
        lazyListState,
        navHost
    ) else simpleDefaultList(list, modifier, lazyListState, navHost)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun simpleDefaultList(
    list: List<Workout>,
    modifier: Modifier,
    lazyListState: LazyListState,
    navhost: NavHostController
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.background),
        state = lazyListState
    ) {
        item() {
            EmptyItem(size = 2.dp)
        }
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            val state = remember {
                mutableStateOf(false)
            }
            val action: () -> Unit =
                {
                    state.value = true
                    navhost.navigate(
                        NavigationItem.DefaultDetailWorkoutNavigationItem.routeWithId(
                            it.id
                        )
                    )
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
                        WorkoutItemCard(it, action)
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }
    }


}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun listDefaultWithGroup(
    list: List<Workout>,
    modifier: Modifier,
    lazyListState: LazyListState,
    navhost: NavHostController
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.background),
        state = lazyListState
    ) {
        item() {
            EmptyItem(size = 2.dp)
        }
        list.groupBy { it.isWeekEven }.toSortedMap().forEach { (isWeekEven, list) ->
            stickyHeader {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp, top = 4.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = if (isWeekEven) stringResource(id = R.string.week_even) else stringResource(
                            id = R.string.week_not_even
                        ),
                        style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSecondary) ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    )
                }
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
                            val action: () -> Unit =
                                {
                                    state.value = true
                                    navhost.navigate(
                                        NavigationItem.DefaultDetailWorkoutNavigationItem.routeWithId(
                                            it.id
                                        )

                                    )
                                }

                            WorkoutItemCard(workout = it, action)
                            DividerCustom(dismissState, state = state.value)
                        }
                    },
                    dismissThresholds = { FractionalThreshold(0.5f) }
                )
            }
        }
    }
}