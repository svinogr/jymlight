package info.upump.jymlight.ui.screens.mainscreen


import android.graphics.Camera
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListItemDefaultsCycle
import info.upump.jymlight.ui.screens.viewmodel.cycle.CycleVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultStartScreen(
    navHost: NavHostController,
    paddingValues: PaddingValues,
) {
    val listState = rememberLazyListState()

    val cycleVM: CycleVM = viewModel()

    val listCycle = remember {
        mutableStateOf(cycleVM.cycleList)
    }

    LaunchedEffect(key1 = true) {
        Log.d("LaunchedEffect", "LaunchedEffect $")
        cycleVM.getAllDefault()
    }

    Scaffold(modifier = Modifier.padding(paddingValues = paddingValues),
        floatingActionButton = {
        }, content = { it ->
            ListItemDefaultsCycle(
                lazyListState = listState,
                list = listCycle.value.collectAsState().value,
                navhost = navHost,
            )
        })
}
