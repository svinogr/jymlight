package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
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
import info.upump.jymlight.models.entity.Sets
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.SetsItemCard
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSets(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    list: List<Sets>,
    listState: LazyListState,
    deleteAction: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background),
        state = listState
    ) {  item() {
        EmptyItem(size = 2.dp)
    }
        itemsIndexed(list,  key = { index, item -> item.id }) { index, it ->
            val state = remember {
                mutableStateOf(false)
            }
            val dismissState = rememberDismissState(confirmStateChange = {value ->
                if(value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
                    //deleteAction(it.id)
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
                        SetsItemCard(it, index){
                            state.value = true
                            navHost.navigate(NavigationItem.EditSetsNavigationItem.routeWithId(it.id))
                        }
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }
        item(){
            EmptyItem()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListSetsPreview() {
    val listSets = listOf<Sets>(
        Sets().apply {
            id = 1
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            id = 11
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            id = 12
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
            id = 15
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
            id = 16
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
            id = 18
        }
    )

    ListSets(
        navHost = NavHostController(LocalContext.current),
        list = listSets,
        listState = LazyListState(),
        deleteAction = ::println,
    )
}


