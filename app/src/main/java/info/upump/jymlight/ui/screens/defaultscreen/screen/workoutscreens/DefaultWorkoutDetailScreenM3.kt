package info.upump.jymlight.ui.screens.defaultscreen.screen.workoutscreens

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.screenscomponents.BottomSheet
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListDefaultExercise
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDate
import info.upump.jymlight.ui.screens.screenscomponents.screen.Chips
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageByDay
import info.upump.jymlight.ui.screens.screenscomponents.screen.RowChips
import info.upump.jymlight.ui.screens.viewmodel.web.workout.WorkoutDetailVMWEB
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DefaultWorkoutDetailScreenM3(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    //val workoutVM: WorkoutDetailVM = viewModel()
    val workoutVM: WorkoutDetailVMWEB = viewModel()

    val context = LocalContext.current
    val listState = rememberLazyListState()

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        workoutVM.getBy(id)
        Log.d("size", "$")
    }

    if (id == 0L) {
        appBarTitle.value = context.resources.getString(R.string.workout_dialog_create_new)
    } else {
        appBarTitle.value = workoutVM.title.collectAsState().value
    }

    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

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

            ) { it ->
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier.height(200.dp)) {
                    ImageByDay(day = workoutVM.day.collectAsState().value)

                    RowChips(
                        modifier = Modifier.align(Alignment.BottomEnd),

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
                    workoutVM.startDate.collectAsState().value,
                    workoutVM.finishDate.collectAsState().value,
                    false
                )
                val del: (Long) -> Unit = { workoutVM.deleteSub(it) }
                ListDefaultExercise(
                    list = exercisesList.value.collectAsState().value,
                    listState, navHost = navHostController,
                    Modifier.weight(4f),
                    ) { del(it) }
            }
        }
    }
}
