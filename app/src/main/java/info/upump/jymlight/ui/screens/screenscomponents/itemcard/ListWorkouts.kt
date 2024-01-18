package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import android.annotation.SuppressLint
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
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Workout
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.WorkoutItemCard
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom

@Composable
fun ListWorkouts(
    list: List<Workout>,
    lazyListState: LazyListState,
    navHost: NavHostController,
    modifier: Modifier = Modifier,
    deleteAction: (Long) -> Unit,
) {
    var isHasEvenWeek = 0
    list.forEach {
        if (it.isWeekEven) {
            isHasEvenWeek += 1
        }
    }
    if (isHasEvenWeek != list.size && isHasEvenWeek != 0) listWithGroup(
        list,
        modifier,
        lazyListState,
        deleteAction,
        navHost
    ) else simpleList(list, modifier, lazyListState, deleteAction, navHost)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun simpleList(
    list: List<Workout>,
    modifier: Modifier,
    lazyListState: LazyListState,
    deleteAction: (Long) -> Unit,
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

        val sortedList = list.sortedBy {
            it.day
        }
        itemsIndexed(sortedList, key = { index, item -> item.id }) { index, it ->
            val state = remember {
                mutableStateOf(false)
            }
            val dismissState = rememberDismissState(confirmStateChange = { value ->
                if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
                  /*  deleteAction(it.id)*/
                    true
                } else {
                    false
                }
            })

            val actionDelete = {deleteAction(it.id)}
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                background = {
                    ItemSwipeBackgroundIcon(
                        dismissState = dismissState,
                        state = state.value,
                        actionDelete = actionDelete
                    )
                },
                dismissContent = {
                    Column(modifier = Modifier) {
                        val action: () -> Unit =
                            {
                                state.value = true
                                navhost.navigate(
                                    NavigationItem.DetailWorkoutNavigationItem.routeWithId(
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
        item() {
            EmptyItem()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun listWithGroup(
    list: List<Workout>,
    modifier: Modifier,
    lazyListState: LazyListState,
    deleteAction: (Long) -> Unit,
    navhost: NavHostController
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
        val sortedList = list.sortedBy {
            it.day
        }
        sortedList.groupBy { it.isWeekEven }.toSortedMap().forEach { (isWeekEven, list) ->
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
                    if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
                     //   deleteAction(it.id)
                        true
                    } else {
                        false
                    }
                })
               val actionDelete =  {deleteAction(it.id)}
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                    background = {
                        ItemSwipeBackgroundIcon(
                            dismissState = dismissState,
                            state = state.value,
                            actionDelete = actionDelete)
                    },
                    dismissContent = {
                        Column(modifier = Modifier) {
                            val action: () -> Unit =
                                {
                                    state.value = true
                                    navhost.navigate(
                                        NavigationItem.DetailWorkoutNavigationItem.routeWithId(
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

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListWorkoutsPreview() {
    val nav = NavHostController(LocalContext.current)
    ListWorkouts(
        info.upump.jymlight.ui.screens.viewmodel.cycle.CycleDetailVM.vmOnlyForPreview.subItems.collectAsState().value,
        LazyListState(),
        nav
    ) {}
}