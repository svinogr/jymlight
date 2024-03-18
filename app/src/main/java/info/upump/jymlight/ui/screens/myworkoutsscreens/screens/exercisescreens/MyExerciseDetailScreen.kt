package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.exercisescreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListSets
import info.upump.jymlight.ui.screens.screenscomponents.screen.Chips
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageForDetailScreen
import info.upump.jymlight.ui.screens.screenscomponents.screen.RowChips
import info.upump.jymlight.ui.screens.screenscomponents.screen.SnackBar
import info.upump.jymlight.ui.screens.screenscomponents.screen.TableHeader
import info.upump.jymlight.ui.screens.viewmodel.db.exercise.ExerciseVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExerciseDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val exerciseVM: ExerciseVM = viewModel()
    val load = exerciseVM.isLoading.collectAsState()
    val lazyListState = LazyListState()

    val coroutine = rememberCoroutineScope()

    appBarTitle.value = stringResource(id = R.string.exercise_title_sets)
    val l = remember{
        mutableStateOf(exerciseVM.subItems)}
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        exerciseVM.getBy(id)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.set_create_),
                isVisible = lazyListState.isScrollingUp(),
                icon = R.drawable.ic_add_black_24dp
            ) {
                navHostController.navigate(NavigationItem.CreateSetsNavigationItem.routeWithId(id))
            }
        },
                snackbarHost = {
            SnackbarHost(

                snackBarHostState
            ) {
                SnackBar(
                    text =
                    stringResource(id = R.string.clean_exercise),
                    icon =
                    R.drawable.ic_delete_24,
                    action = {
                        exerciseVM.cleanItem()
                    },
                    data = it
                )
            }
        }
    ) { it ->
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            Box(modifier = Modifier.height(200.dp)) {
                ImageForDetailScreen(
                    image = exerciseVM.imageDescription.collectAsState().value,
                    defaultImage = exerciseVM.imageDescriptionDefault.collectAsState().value
                )
                RowChips(modifier = Modifier.align(Alignment.BottomEnd),
                    Chips(
                        stringResource(id = R.string.snack_exersice_delete_sets),
                        R.drawable.ic_delete_24
                    ) {
                        coroutine.launch {
                            snackBarHostState.showSnackbar("")
                        }
                    }
                )
            }
            TableHeader()
            val deleteSets: (Long) -> Unit = { exerciseVM.deleteSub(it) }

            ListSets(
                modifier = Modifier.weight(4.5f),
                navHost = navHostController,
                list = l.value.collectAsState().value,
                listState = lazyListState,
                deleteSets,
                )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMyExerciseDetailScreen() {
    val id = 1L
    val m: MutableState<String> =
        MutableStateFlow(" ").asStateFlow().collectAsState() as MutableState<String>

    MyExerciseDetailScreen(
        id = id,
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(20.dp),
        appBarTitle = m
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMyExerciseDetailScreen2() {
    val id = 1L
    val m: MutableState<String> =
        MutableStateFlow(" ").asStateFlow().collectAsState() as MutableState<String>
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        TableHeader()
        ListSets(
            navHost = NavHostController(LocalContext.current),
            list = ExerciseVM.vmOnlyForPreview.subItems.collectAsState().value,
            listState = LazyListState()) {
                  println(it)
            }

    }
}


