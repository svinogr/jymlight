package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.mainscreen.AppBarAction
import info.upump.jymlight.ui.screens.screenscomponents.BottomSheet
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.NumberPicker
import info.upump.jymlight.ui.screens.viewmodel.workout.SoundTimerEditVM

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun EditSoundTimerScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
    appBarActions: MutableState<List<AppBarAction>>
) {
    val colModifier = Modifier.background(colorResource(id = R.color.colorBackgroundCardView))
    val titleModifier = Modifier.padding(start = 8.dp, top = 8.dp)
    val context = LocalContext.current
    val soundTimerErrorActionEditVM: SoundTimerEditVM = viewModel()

    appBarTitle.value = stringResource(id = R.string.label_edit_sound_timer_screen)
    val start = remember {
        mutableStateOf(soundTimerErrorActionEditVM.start)
    }

    val finish = remember {
        mutableStateOf(soundTimerErrorActionEditVM.finis)
    }
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    
    LaunchedEffect(key1 = true){
        soundTimerErrorActionEditVM.init(context)
        val infoAction =
            AppBarAction(icon = R.drawable.ic_info_black_24dp) {
                bottomState.show() }
        val list = mutableListOf<AppBarAction>()
        list.add(infoAction)
        appBarActions.value = list
    }

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            BottomSheet(text = stringResource(id = R.string.label_edit_sound_timer_screen_info))
        }
    ) {
        Scaffold(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            floatingActionButton = {
                FloatButtonWithState(
                    text = stringResource(id = R.string.picker_dialog_btn_save),
                    isVisible = true,
                    icon = R.drawable.ic_save_black
                ) {
                    soundTimerErrorActionEditVM.save(context)
                    navHostController.navigateUp()
                }
            })
        { it ->
            Column(
                modifier = colModifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(top = it.calculateTopPadding())
            ) {

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 8.dp, end = 8.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Column(
                        modifier = colModifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxHeight()
                    ) {
                        Text(
                            modifier = titleModifier,
                            text = stringResource(id = R.string.label_sound_timer_start),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        NumberPicker(0, 100, start.value.collectAsState().value) {
                            soundTimerErrorActionEditVM.setStart(it)
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 8.dp, end = 8.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Column(
                        modifier = colModifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxHeight()
                    ) {
                        Text(
                            modifier = titleModifier,
                            text = stringResource(id = R.string.label_sound_timer_finish),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        NumberPicker(0, 200, finish.value.collectAsState().value) {
                            soundTimerErrorActionEditVM.setFinish(it)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(0.3f))
            }
        }
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

@Preview
@Composable
fun PreviewEditSoundTimer() {
    val m = remember {
        mutableStateOf("mutableStateOf(\"stringResource(id = R.string.label_sound_timer_start)\"")
    }
    val infoAction =
        AppBarAction(icon = R.drawable.ic_info_black_24dp) {}
    val list = listOf(infoAction)
    val stateAction = remember {
        mutableStateOf(list)
    }

    EditSoundTimerScreen(NavHostController(LocalContext.current), PaddingValues(), m, stateAction)
}