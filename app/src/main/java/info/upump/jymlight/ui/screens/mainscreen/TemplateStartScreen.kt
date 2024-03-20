package info.upump.jymlight.ui.screens.mainscreen


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
import info.upump.jymlight.IS_LOCALDB
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListItemDefaultsCycle
import info.upump.jymlight.ui.screens.viewmodel.CycleVMInterface
import info.upump.jymlight.ui.screens.viewmodel.db.cycle.CycleVMDB
import info.upump.jymlight.ui.screens.viewmodel.web.cycle.CycleVMWEB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateStartScreen(
    navHost: NavHostController,
    paddingValues: PaddingValues,
) {
    val listState = rememberLazyListState()


    val cycleVMDB = if (IS_LOCALDB) {
        val v: CycleVMDB = viewModel()
        v
    } else {
        val v: CycleVMWEB = viewModel()
        v
    }

    val listCycle = remember {
        mutableStateOf(cycleVMDB.cycleList)
    }

    LaunchedEffect(key1 = true) {
        Log.d("LaunchedEffect", "LaunchedEffect $")
        cycleVMDB.getAllDefault()
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
