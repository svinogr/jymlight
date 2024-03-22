package info.upump.jymlight.ui.screens.defaultscreen.screen.exercise

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.IS_LOCALDB
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListDefaultSets
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageForDetailScreen
import info.upump.jymlight.ui.screens.screenscomponents.screen.SnackBar
import info.upump.jymlight.ui.screens.screenscomponents.screen.TableHeader
import info.upump.jymlight.ui.screens.screenscomponents.screen.web.ImageForDetailScreenWEB
import info.upump.jymlight.ui.screens.viewmodel.db.exercise.ExerciseVM
import info.upump.jymlight.ui.screens.viewmodel.web.exercise.ExerciseVMWEB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultExerciseDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    //val exerciseVM: ExerciseVM = viewModel()
    val exerciseVM: ExerciseVMWEB = viewModel()
    val load = exerciseVM.isLoading.collectAsState()
    val lazyListState = LazyListState()

    val coroutine = rememberCoroutineScope()

    appBarTitle.value = stringResource(id = R.string.exercise_title_sets)
    Log.d("saveItem", "$id")
    val l = remember {
        mutableStateOf(exerciseVM.subItems)
    }
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        exerciseVM.getBy(id)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        snackbarHost = {
            SnackbarHost(

                snackBarHostState
            ) {
                SnackBar(
                    text =  stringResource(id = R.string.clean_workouts),
                    icon = R.drawable.ic_delete_24,
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
                if(IS_LOCALDB) {
                    ImageForDetailScreen(
                        image = exerciseVM.imageDescription.collectAsState().value,
                        defaultImage = exerciseVM.imageDescriptionDefault.collectAsState().value
                    )
                }else {
                    ImageForDetailScreenWEB(
                        image = exerciseVM.imageDescription.collectAsState().value,
                        defaultImage = exerciseVM.imageDescriptionDefault.collectAsState().value
                    )
                }
            }
            TableHeader()
            val deleteSets: (Long) -> Unit = { exerciseVM.deleteSub(it) }

            ListDefaultSets(
                modifier = Modifier.weight(4.5f),
                navHost = navHostController,
                list = l.value.collectAsState().value,
                listState = lazyListState,
                deleteSets,
            )
        }
    }
}


