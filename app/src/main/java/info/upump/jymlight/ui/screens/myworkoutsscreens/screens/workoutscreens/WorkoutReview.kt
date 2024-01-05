package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.ExerciseDescription
import info.upump.jymlight.models.entity.Sets
import info.upump.jymlight.models.entity.TypeMuscle
import info.upump.jymlight.ui.screens.mainscreen.AppBarAction
import info.upump.jymlight.ui.screens.screenscomponents.BottomSheet
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListWorkoutForReview
import info.upump.jymlight.ui.screens.screenscomponents.screen.SnackBar
import info.upump.jymlight.ui.screens.screenscomponents.screen.StopWatch
import info.upump.jymlight.ui.screens.viewmodel.workout.StopWatchVM
import info.upump.jymlight.ui.screens.viewmodel.workout.WorkoutDetailVM
import info.upump.jymlight.ui.theme.MyOutlineTextTitleLabel20Text
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WorkoutReview(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
    appBarActions: MutableState<List<AppBarAction>>
) {
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val workoutVM: WorkoutDetailVM = viewModel()
    val stopwatchVM: StopWatchVM = viewModel()

  /*  val workout by remember {
        mutableStateOf(workoutVM.item)
    }*/

    val exercise by remember {
        mutableStateOf(workoutVM.subItems)
    }

    val status by remember {
        mutableStateOf(stopwatchVM.status)
    }
    val time by remember {
        mutableStateOf(stopwatchVM.formatedTime)
    }

    val coroutine = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        val commentAction =
            AppBarAction(icon = R.drawable.ic_info_black_24dp) { bottomState.show() }
        workoutVM.getBy(id)
        val list = mutableListOf<AppBarAction>()
        list.add(commentAction)
        appBarActions.value = list
    }

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            BottomSheet(text = workoutVM.comment.collectAsState().value)
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            snackbarHost = {
                SnackbarHost(
                    snackBarHostState
                ) {
                    SnackBar(stringResource(id = R.string.snack_exit_workout), R.drawable.ic_exit) {
                        appBarActions.value = listOf()
                        navHostController.popBackStack()
                    }
                }
            }

        ) { it ->
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            ) {

                ListWorkoutForReview(
                    exercise.collectAsState().value,
                    Modifier.weight(4f)
                )
                StopWatch(
                    time.collectAsState().value,
                    status.collectAsState().value,
                    start = stopwatchVM::start,
                    stop = stopwatchVM::stop,
                    pause = stopwatchVM::pause,
                    resume = stopwatchVM::resume
                )
            }
        }
    }

    BackHandler {
        coroutine.launch {
            snackBarHostState.showSnackbar("")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkoutReviewPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val snackBarHostState = remember { SnackbarHostState() }

    val list = listOf(
        Exercise().apply {
            id = 1
            title = "First"
            typeMuscle = TypeMuscle.ABS
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(), Sets(), Sets(), Sets(), Sets(), Sets()
            )
            descriptionId = 1
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        },
        Exercise().apply {
            id = 2
            title = "Second"
            typeMuscle = TypeMuscle.BACK
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(), Sets(), Sets()
            )
            descriptionId = 2
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        },
        Exercise().apply {
            id = 3
            title = "Thead"
            typeMuscle = TypeMuscle.CALVES
            isDefaultType = true
            isTemplate = true
            setsList = mutableListOf(
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets(),
                Sets()
            )
            descriptionId = 3
            exerciseDescription = ExerciseDescription().apply {
                img = "nach1"
                defaultImg = "nach1"
                title = "Новое упраж"
                id = descriptionId
            }
        }
    )

    val workout = WorkoutDetailVM.vmOnlyForPreview.item.collectAsState()
    val coroutine = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            BottomSheet(text = workout.value.comment)
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(top = 0.dp),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text(
                            text = "упражнение",
                            style =
                            MyOutlineTextTitleLabel20Text,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            coroutine.launch() {
                                bottomState.show()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info_black_24dp),
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
            }

        ) { it ->
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
            ) {
                /*   Box(modifier = Modifier.weight(1.5f)) {
                       ImageByDay(day = workout.value.day)
                       RowChips(
                           modifier = Modifier.align(Alignment.BottomEnd),
                           Chips(
                               stringResource(id = R.string.chips_comment),
                               R.drawable.ic_info_black_24dp,
                           ) {
                               coroutine.launch() {
                                   bottomState.show()
                               }
                           }
                       )
                   }*/
                ListWorkoutForReview(list, modifier = Modifier.weight(3f))
                Divider(
                    thickness = 1.dp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

            }
        }
    }
}
