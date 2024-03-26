package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
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
import info.upump.jymlight.ui.screens.mainscreen.isScrollingUp
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.BottomSheet
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListExercise
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDate
import info.upump.jymlight.ui.screens.screenscomponents.screen.Chips
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageByDay
import info.upump.jymlight.ui.screens.screenscomponents.screen.RowChips
import info.upump.jymlight.ui.screens.screenscomponents.screen.SnackBar
import info.upump.jymlight.ui.screens.viewmodel.db.workout.WorkoutDetailVMDB
import info.upump.jymlight.ui.screens.viewmodel.web.workout.WorkoutDetailVMWEB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreenM3(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    //val workoutVM: WorkoutDetailVMDB = viewModel()
    val workoutVM: WorkoutDetailVMWEB = viewModel()
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        workoutVM.getBy(id)
    }

    if (id == 0L) {
        appBarTitle.value = context.resources.getString(R.string.workout_dialog_create_new)
    } else {
        appBarTitle.value = workoutVM.title.collectAsState().value.capitalize()
    }

    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val snackBarHostState = remember { SnackbarHostState() }

    val exercisesList = remember {
        mutableStateOf(workoutVM.subItems)
    }

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
                BottomSheet(text = workoutVM.comment.collectAsState().value)
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(top = 0.dp),
            floatingActionButton = {
                FloatButtonWithState(
                    stringResource(id = R.string.workout_dialog_create_new),
                    listState.isScrollingUp(), R.drawable.ic_add_black_24dp
                ) {
                    navHostController.navigate(
                        NavigationItem.ExerciseChooseScreenNavigationItem.routeWithId(
                            id
                        )
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(
                    snackBarHostState
                ) {
                    SnackBar(
                        text = stringResource(id = R.string.clean_workouts),
                        icon = R.drawable.ic_delete_24,
                        action = {
                            workoutVM.cleanItem()
                        },
                        data = it
                    )
                }
            }
        ) { it ->
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier.height(200.dp)) {
                    ImageByDay(day = workoutVM.day.collectAsState().value)
                    RowChips(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        Chips(
                            stringResource(id = R.string.chips_clear),
                            R.drawable.ic_delete_24,
                        ) {
                            coroutine.launch {
                                snackBarHostState.showSnackbar("")
                            }
                        },
                        Chips(
                            stringResource(id = R.string.to_view_workout),
                            R.drawable.ic_yey
                        ) {
                            coroutine.launch() {
                               navHostController.navigate(NavigationItem.ReviewWorkoutNavigationItem.routeWith(id))
                            }
                        },
                        Chips(
                            stringResource(id = R.string.chips_comment),
                            R.drawable.ic_info_black_24dp
                        ) {
                            coroutine.launch() {
                                bottomState.show()
                            }
                        },
                        Chips(
                            stringResource(id = R.string.chips_edite),
                            R.drawable.ic_edit_black_24dp,
                        ) {
                            navHostController.navigate(
                                NavigationItem.EditeWorkoutNavigationItem.routeWith(
                                    id
                                )
                            )
                        },
                    )
                }

                CardDate(
                    workoutVM.startDate.collectAsState().value,
                    workoutVM.finishDate.collectAsState().value,
                    false
                )
                val del: (Long) -> Unit = { workoutVM.deleteSub(it) }
                ListExercise(
                    list = exercisesList.value.collectAsState().value,
                    listState, navHost = navHostController,
                    Modifier.weight(4f),

                    ) { del(it) }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AlterCycleDetailScreenPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    WorkoutDetailScreenM3(
        0L,
        NavHostController(LocalContext.current),
        PaddingValues(),
        m
    )
}
