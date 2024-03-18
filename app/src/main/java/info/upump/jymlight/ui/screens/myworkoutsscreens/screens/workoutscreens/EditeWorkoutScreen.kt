package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.screen.CarDaydWorkoutEdit
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDescriptionWithEdit
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardTitle
import info.upump.jymlight.ui.screens.screenscomponents.screen.DateCardWithDatePicker
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageByDay
import info.upump.jymlight.ui.screens.screenscomponents.screen.LabelTitleForImage
import info.upump.jymlight.ui.screens.viewmodel.db.workout.WorkoutVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditeWorkoutScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
) {
    val workoutVM: WorkoutVM = viewModel()
    val isLoad by workoutVM.isLoading.collectAsState()

    appBarTitle.value = stringResource(id = R.string.edit)

    LaunchedEffect(key1 = true) {
        workoutVM.getBy(id)
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatButtonWithState(
                stringResource(id = R.string.picker_dialog_btn_save),
                true, R.drawable.ic_save_black
            ) {
                if (!workoutVM.isBlankFields()) {
                    workoutVM.save {
                    }
                    navHostController.popBackStack()
                    navHostController.navigateUp()
                }
            }

        }) { it ->
        Column(modifier = Modifier.padding(top = it.calculateTopPadding()
          /*  modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout)*/),
        ) {
            Box(
                modifier = Modifier.height(200.dp)

            ) {
                ImageByDay(day = workoutVM.day.collectAsState().value)
                LabelTitleForImage(
                    title = workoutVM.title.collectAsState().value, modifier = Modifier.align(
                        Alignment.BottomStart
                    )
                )
            }

            CarDaydWorkoutEdit(
                workoutVM.day.collectAsState().value,
                workoutVM::updateDay,
                workoutVM.isEven.collectAsState().value,
                workoutVM::updateEven,
                LocalFocusManager.current,
                Modifier
            )

            DateCardWithDatePicker(
                workoutVM.startDate.collectAsState().value,
                workoutVM::updateStartDate,
                workoutVM.finishDate.collectAsState().value,
                workoutVM::updateFinishDate
            )

            CardTitle(
                text = workoutVM.title.collectAsState().value,
                isError = workoutVM.isTitleError.collectAsState().value,
                updateText = workoutVM::updateTitle,
            )

            CardDescriptionWithEdit(
                workoutVM.comment.collectAsState().value,
                workoutVM::updateComment,
                Modifier.weight(3F).padding(bottom = 16.dp)
            )
        }
    }
    LaunchedEffect(key1 = true) {
        workoutVM.getBy(0)
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditeWorkoutScreenPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    EditeWorkoutScreen(
        id = 1L,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}
