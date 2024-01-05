package info.upump.jymlight.ui.screens.mainscreen


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListCycle
import info.upump.jymlight.ui.screens.viewmodel.cycle.CycleVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCycleScreen(
    navHost: NavHostController,
    paddingValues: PaddingValues,
) {
    val listState = rememberLazyListState()

    val cycleVM: CycleVM = viewModel()

    val listCycle = remember {
        mutableStateOf(cycleVM.cycleList)
    }

    LaunchedEffect(key1 = true) {
        cycleVM.getAllPersonal()
    }
    val list = remember {
        listCycle.value
    }

    Scaffold(modifier = Modifier.padding(paddingValues = paddingValues),
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.cycle_create_new),
                isVisible = listState.isScrollingUp(), icon = R.drawable.ic_add_black_24dp
            ) {
                navHost.navigate(NavigationItem.CreateEditeCycleNavigationItem.routeWith(0))
            }
        }, content = { it ->
            val del: (Context, String, Long) -> Unit = cycleVM::delete
            ListCycle(
                lazyListState = listState,
                list = list.collectAsState().value,
                navhost = navHost,
                deleteAction = del
            )
        })
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PrviewCycleScreen() {
    val m: MutableState<Boolean> =
        MutableStateFlow<Boolean>(true).asStateFlow().collectAsState() as MutableState<Boolean>
    MyCycleScreen(
        navHost = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(16.dp)
    )
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
