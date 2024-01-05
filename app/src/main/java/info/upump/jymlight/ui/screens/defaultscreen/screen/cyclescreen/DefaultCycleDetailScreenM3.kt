package info.upump.jymlight.ui.screens.defaultscreen.screen.cyclescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.screenscomponents.BottomSheet
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListItemDefaultsWorkouts
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDate
import info.upump.jymlight.ui.screens.screenscomponents.screen.Chips
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageForDetailScreen
import info.upump.jymlight.ui.screens.screenscomponents.screen.RowChips
import info.upump.jymlight.ui.screens.screenscomponents.screen.SnackBar
import info.upump.jymlight.ui.screens.viewmodel.cycle.CycleDetailVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DefaultCycleDetailScreenM3(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val cycleVM: CycleDetailVM = viewModel()
    val listState = rememberLazyListState()

    val coroutine = rememberCoroutineScope()

    appBarTitle.value = cycleVM.title.collectAsState().value

    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val list by remember {
        mutableStateOf(cycleVM.subItems)
    }

    LaunchedEffect(key1 = true) {
        cycleVM.getBy(id)
    }
    val snackBarHostState = remember { SnackbarHostState() }

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                BottomSheet(text = cycleVM.comment.collectAsState().value)
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(top = 0.dp),
            snackbarHost = {
                SnackbarHost(
                    snackBarHostState
                ) {
                    SnackBar(stringResource(id = R.string.chips_copy), R.drawable.ic_copy) {
                        cycleVM.copyToPersonal(id)
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier.height(200.dp)) {
                    ImageForDetailScreen(
                        image = cycleVM.img.collectAsState().value,
                        defaultImage = cycleVM.imgDefault.collectAsState().value,
                    )

                    RowChips(
                        modifier = Modifier.align(Alignment.BottomEnd),

                        Chips(
                            stringResource(id = R.string.chips_copy),
                            R.drawable.ic_copy,
                        ) {
                            coroutine.launch {
                                snackBarHostState.showSnackbar("")
                            }
                        },
                        Chips(
                            stringResource(id = R.string.chips_comment),
                            R.drawable.ic_info_black_24dp
                        ) {
                            coroutine.launch() {
                                bottomState.show()
                            }
                        }
                    )
                }

                CardDate(
                    cycleVM.startDate.collectAsState().value,
                    cycleVM.finishDate.collectAsState().value,
                    false
                )

                ListItemDefaultsWorkouts(
                    list = list.collectAsState().value,
                    listState,
                    navHost = navHostController,
                    Modifier.weight(4f),
                )
            }
        }
    }
}


