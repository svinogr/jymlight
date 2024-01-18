package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import android.util.Log
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.CycleCheck
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.CycleItemCardWithCheck
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemCycleWithCheck(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    list: List<CycleCheck>,
    action: (Long) -> Unit
) {
    val state = remember {
        mutableStateOf(false)
    }

    /*    val stateList = remember {
            mutableStateOf(list)
        }*/

    SideEffect {
        Log.d("check", "side ListItemCycleWithCheck ")
    }
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.background
            ),
        state = lazyListState
    ) {
        item() {
            EmptyItem(size = 2.dp)
        }
        itemsIndexed(list, key = { index, item -> item.cycle.id })
        { _, cycle ->
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
                        CycleItemCardWithCheck(cycle = cycle) { action(cycle.cycle.id) }
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ListItemCycleWithCheckPreview() {
    ListItemCycleWithCheck(
        lazyListState = LazyListState(),
        list = listOf(
            CycleCheck(cycle = Cycle().apply {
                id = 1
                title = "Program1"
                image = ""
                imageDefault = "uk1"
            }, isCheck = true),
            CycleCheck(cycle = Cycle().apply {
                id = 1
                title = "Program1"
                image = ""
                imageDefault = "uk1"
            }, isCheck = false),
            CycleCheck(
                cycle = Cycle().apply {
                    id = 1
                    title = "Program1"
                    image = ""
                    imageDefault = "uk1"
                }, isCheck = true
            )
        )
    ) {}
}