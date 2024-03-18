package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.exercisescreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Day
import info.upump.jymlight.models.entity.Exercise
import info.upump.jymlight.models.entity.ExerciseDescription
import info.upump.jymlight.models.entity.TypeMuscle
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListChooseExercise
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListExercise
import info.upump.jymlight.ui.screens.screenscomponents.screen.CheckChips
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageByDay
import info.upump.jymlight.ui.screens.screenscomponents.screen.RowChooseChips
import info.upump.jymlight.ui.screens.viewmodel.db.exercise.ExerciseChooseVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseChooseScreen(
    parentId: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
    modifier: Modifier = Modifier
) {
    appBarTitle.value = stringResource(id = R.string.exercise_choose_title_add_exercise)
    val exerciseVM: ExerciseChooseVM = viewModel()
    val listState = rememberLazyListState()

    val valuesMuscle = TypeMuscle.values()
    val list = mutableListOf<CheckChips>()
    val filter = remember {
        mutableStateOf(TypeMuscle.NECK)
    }
    valuesMuscle.forEach {
        list.add(
            CheckChips(
                title = stringResource(id = it.title),
                icon = R.drawable.ic_done
            ) { filter.value = it })
        list[0].let { it.check = true }
    }

    Scaffold(
    ) { it ->
        Column {
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth()
            ) {
                ImageByDay(
                    day = exerciseVM.day.collectAsState().value
                )

                RowChooseChips(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    *list.toTypedArray()
                )
            }
            val action: (Long) -> Unit = {
                exerciseVM.saveForParentChosen(it) {id ->
                    navHostController.popBackStack()
                    navHostController.navigate(
                        NavigationItem.DetailExerciseNavigationItem.routeWithId(
                            id
                        )
                    )
                }
            }

            ListChooseExercise(
                list = exerciseVM.subItems.collectAsState().value.filter {
                    it.typeMuscle == filter.value
                },
                lazyListState = listState,
                navHost = navHostController,
                modifier = Modifier.weight(4.5f)
            ) {
                action(it)
            }

        }
    }

    LaunchedEffect(key1 = true) {
        exerciseVM.getAllExerciseForParent(parentId)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExerciseChooseScreenPreview() {

    val valuesMuscle = TypeMuscle.values()
    val list = mutableListOf<CheckChips>()
    valuesMuscle.forEach {
        list.add(
            CheckChips(
                check = false,
                title = stringResource(id = it.title),
                R.drawable.ic_done
            ) { })
    }

    Column {
        Box(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth()
        ) {
            ImageByDay(
                day = Day.TUESDAY
            )

            RowChooseChips(
                modifier = Modifier.align(Alignment.BottomCenter),
                *list.toTypedArray()
            )
        }
        val exD = ExerciseDescription()
        exD.title = "smth sporty"

        val navHostController = NavHostController(LocalContext.current)
        val listExer = listOf(
            Exercise().apply { exerciseDescription = exD },
            Exercise().apply { exerciseDescription = exD },
            Exercise().apply { exerciseDescription = exD },
            Exercise().apply { exerciseDescription = exD },
        )
        ListExercise(
            list = listExer,
            lazyListState = LazyListState(),
            navHost = navHostController,
            modifier = Modifier.weight(4.5f)
        ) {}

    }
}
