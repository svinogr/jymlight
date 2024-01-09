package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.cyclescreens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import info.upump.jymlight.ui.screens.myworkoutsscreens.ActionState
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDescriptionWithEdit
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardTitle
import info.upump.jymlight.ui.screens.screenscomponents.screen.DateCardWithDatePicker
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageTitleImageTitle
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageWithPicker
import info.upump.jymlight.ui.screens.viewmodel.cycle.CycleVMCreateEdit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditeCycleScreen(
    id: Long, navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
    action: ActionState
) {
    val cycleVM: CycleVMCreateEdit = viewModel()

    val context = LocalContext.current
    val columnModifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .background(color = colorResource(id = R.color.colorBackgroundCardView))

    if (action == ActionState.CREATE) {
        appBarTitle.value = context.resources.getString(R.string.cycle_dialog_create_new)
    }
    if (action == ActionState.UPDATE) {
        appBarTitle.value = context.resources.getString(R.string.edit)
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.picker_dialog_btn_save),
                isVisible = true, icon = R.drawable.ic_save_black
            ) {
                if (!cycleVM.isBlankFields()) {
                    cycleVM.save(context) {
                        if (action == ActionState.CREATE) {
                            navHostController.popBackStack()
                            navHostController.navigate(
                                NavigationItem.DetailCycleNavigationItem.routeWithId(it)
                            )
                        } else {
                            navHostController.navigateUp()
                        }
                    }
                }
            }
        }
    ) {
        Column(modifier = columnModifier)
        {
            ImageTitleImageTitle(
                Modifier.height(200.dp),
                cycleVM.title.collectAsState().value,
                cycleVM.isTitleError.collectAsState().value,
                cycleVM::updateTitle,
            ) {
                ImageWithPicker(
                    cycleVM.img.collectAsState().value,
                    cycleVM.imgDefault.collectAsState().value,
                    cycleVM::updateImage
                )
            }
            CardTitle(
                text = cycleVM.title.collectAsState().value,
                isError = cycleVM.isTitleError.collectAsState().value,
                updateText = cycleVM::updateTitle,
            )
            DateCardWithDatePicker(
                cycleVM.startDate.collectAsState().value,
                cycleVM::updateStartDate,
                cycleVM.finishDate.collectAsState().value,
                cycleVM::updateFinishDate
            )
            CardDescriptionWithEdit(
                cycleVM.comment.collectAsState().value,
                cycleVM::updateComment,
                Modifier.weight(4f))
        }
    }

    LaunchedEffect(key1 = true) {
        cycleVM.getBy(id)
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateEditeCycleScreen() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    CreateEditeCycleScreen(
        id = 0L,
        navHostController = NavHostController(LocalContext.current),
        PaddingValues(20.dp),
        m,
        ActionState.CREATE
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateEditeCycleScreen2() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    CreateEditeCycleScreen(
        id = 1L,
        navHostController = NavHostController(LocalContext.current),
        PaddingValues(20.dp),
        m,
        ActionState.UPDATE
    )
}





