package info.upump.jymlight.ui.screens.profilescreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.CycleCheck
import info.upump.jymlight.ui.screens.mainscreen.isScrollingUp
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListItemCycleWithCheck
import info.upump.jymlight.ui.screens.viewmodel.cycle.CycleCheckVM
import info.upump.jymlight.ui.screens.viewmodel.cycle.StateCheckBoxes
import info.upump.jymlight.ui.screens.viewmodel.profile.ProfileVM
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileChooseScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val listState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val cycleVM: CycleCheckVM = viewModel()
    val listCycles = cycleVM.cycleList

    LaunchedEffect(key1 = true) {
        cycleVM.getAllPersonal()
    }

    val list = remember {
        mutableStateOf(listCycles)
    }

    val action: (Long) -> Unit = cycleVM::checkItem

    Scaffold(modifier = Modifier.padding(paddingValues = paddingValues),
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.save_Capitalizw),
                isVisible = listState.isScrollingUp(), icon = R.drawable.ic_send_to_email
            ) {
                coroutine.launch {
                    Log.d("check", "${cycleVM.getCheckedCycle().size}")

                    // profileVM.send(context, cycleVM.getCheckedCycle())
                }
            }
        }
    ) { it ->
        Column {
            ListItemCycleWithCheck(
                lazyListState = listState,
                list = list.value.collectAsState().value,
                action = action
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileChoose() {
    val listState = rememberLazyListState()

    val list = listOf(
        CycleCheck(Cycle(title = "1", id = 1)),
        CycleCheck(Cycle(title = "2", id = 2)),
        CycleCheck(Cycle(title = "3", id = 3))
    )

    Scaffold(
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.save_Capitalizw),
                isVisible = listState.isScrollingUp(), icon = R.drawable.ic_send_to_email
            ) {

            }
        }
    ) { it ->
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier.padding(start = 8.dp),
                    checked = true,
                    onCheckedChange = {}
                )
                Text(text = "ВСЕ")
            }
            ListItemCycleWithCheck(
                lazyListState = listState,
                list = list,
            ) {}
        }
    }
}