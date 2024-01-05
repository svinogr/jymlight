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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.CycleItemCard
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemDefaultsCycle(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    list: List<Cycle>,
    navhost: NavHostController,
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
            val action: () -> Unit = {
                state.value = true
                navhost.navigate(NavigationItem.DefaultDetailCycleNavigationItem.routeWithId(it.id))
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
                        CycleItemCard(cycle = it, action = action)
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ListItemDefaultsCyclePreview() {

    ListItemDefaultsCycle(
        lazyListState = LazyListState(),
        list = listOf(
            Cycle().apply {
                id = 1
                title = "Program1"
                image = ""
                imageDefault = "uk1"
            },
            Cycle().apply {
                id = 12
                title = "Program2"
                image = ""
                imageDefault = "uk2"
            },
            Cycle().apply {
                id = 13
                title = "Program3"
                image = ""
                imageDefault = "uk1"
            }),
        navhost = NavHostController(LocalContext.current)
    )
}