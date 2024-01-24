package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.NumberPicker
import info.upump.jymlight.ui.screens.viewmodel.workout.SoundTimerEditVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSoundTimerScreen(
    navHostController: NavHostController, paddingValues: PaddingValues
) {
    val colModifier = Modifier.background(colorResource(id = R.color.colorBackgroundCardView))
    val titleModifier = Modifier.padding(start = 8.dp, top = 8.dp)
    val context = LocalContext.current
    val soundTimerErrorActionEditVM : SoundTimerEditVM = viewModel()

    val start = remember {
        mutableStateOf(soundTimerErrorActionEditVM.start)
    }

    val finish = remember {
        mutableStateOf(soundTimerErrorActionEditVM.finis)
    }

    Scaffold(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.picker_dialog_btn_save),
                isVisible = true,
                icon = R.drawable.ic_save_black
            ) {
               // setVM.save()
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
                        text = stringResource(id = R.string.label_reps_sets),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    NumberPicker(0, 100, start.value.collectAsState().value) {
                        soundTimerErrorActionEditVM.setStart(it)
                       // soundTimerErrorActionEditVM.setStart(it)
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
                        text = stringResource(id = R.string.label_sets),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    NumberPicker(0, 200, finish.value.collectAsState().value) {
                        Log.d("q", it.toString())
                        soundTimerErrorActionEditVM.setFinish(it)

                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.3f))
        }
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

@Preview
@Composable
fun PreviewEditSoundTimer() {
    EditSoundTimerScreen(NavHostController(LocalContext.current), PaddingValues())
}