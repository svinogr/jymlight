package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.setsscreen

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import info.upump.jymlight.ui.screens.screenscomponents.NumberPickerWithStep
import info.upump.jymlight.ui.screens.viewmodel.db.sets.SetsVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetEditeScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val setVM: SetsVM = viewModel()
    val isLoad = setVM.isLoading.collectAsState()
    val titleModifier = Modifier.padding(start = 8.dp, top = 8.dp)
    val colModifier = Modifier.background(colorResource(id = R.color.colorBackgroundCardView))
    appBarTitle.value = stringResource(id = R.string.set_update_title)

    LaunchedEffect(key1 = true) {
        launch { setVM.getBy(id) }
    }

    Scaffold(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.picker_dialog_btn_save),
                isVisible = true,
                icon = R.drawable.ic_save_black
            ) {
                setVM.save()
                navHostController.navigateUp()
            }
        })
    { it ->
        Column(
            modifier = colModifier.background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
               .padding(top = it.calculateTopPadding())
        ) {
            Card(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(
                    modifier = colModifier.fillMaxHeight().background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        modifier = titleModifier.fillMaxWidth(),
                        text = stringResource(id = R.string.label_weight_set),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    NumberPickerWithStep(min = 0.0, max = 200, step = 1.25, initialState =  setVM.weight.collectAsState().value) {
                        setVM.updateWeight(it)
                    }
                }
            }

            Card(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(modifier = colModifier.fillMaxHeight().background(MaterialTheme.colorScheme.background)) {
                    Text(
                        modifier = titleModifier,
                        text = stringResource(id = R.string.label_reps_sets),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    NumberPicker(0, 100, setVM.reps.collectAsState().value) {
                        setVM.updateReps(it)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1.3f))
        }
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun ComPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>

    SetEditeScreen(
        id = 1,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}