package info.upump.jymlight.ui.screens.navigation.botomnavigation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import info.upump.jymlight.ui.screens.mainscreen.AppBarAction
import info.upump.jymlight.ui.screens.mainscreen.DEFAULT_STYLE
import info.upump.jymlight.ui.screens.mainscreen.MyCycleScreen
import info.upump.jymlight.ui.screens.mainscreen.WHITE_STYLE
import info.upump.jymlight.ui.screens.myworkoutsscreens.ActionState
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.cyclescreens.AlterCycleDetailScreenM3
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.cyclescreens.CreateEditeCycleScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.exercisescreens.ExerciseChooseScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.exercisescreens.ExerciseReviewScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.exercisescreens.MyExerciseDetailScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.setsscreen.SetEditeScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.setsscreen.SetsCreateScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens.CreateWorkoutScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens.EditSoundTimerScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens.EditeWorkoutScreen
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens.WorkoutDetailScreenM3
import info.upump.jymlight.ui.screens.myworkoutsscreens.screens.workoutscreens.WorkoutReview

const val MY_CYCLE_ROOT_ROUTE = "myCycleRootRoute"

fun NavGraphBuilder.myCycleGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    appBarStyle: MutableState<Int>,
    context: Context,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>,
    appBarActions: MutableState<List<AppBarAction>>
) {
    navigation(
        startDestination = NavigationItem.MyCycleNavigationItem.route,
        route = MY_CYCLE_ROOT_ROUTE
    ) {

        composable(route = NavigationItem.MyCycleNavigationItem.route) {
            appBarTitle.value =
                context.resources.getString(NavigationItem.MyCycleNavigationItem.title)
            topBarState.value = true
            bottomBarState.value = true
            appBarStyle.value = DEFAULT_STYLE
            MyCycleScreen(navHostController, paddingValues)
        }

        composable(
            route = NavigationItem.DetailCycleNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            topBarState.value = false
            bottomBarState.value = false
            appBarStyle.value = WHITE_STYLE
            AlterCycleDetailScreenM3(
                id = id!!,
                navHostController = navHostController,
                paddingValues = paddingValues,
                appBarTitle = appBarTitle
            )

        }

        // exercise start
        //exercise detail
        composable(
            route = NavigationItem.DetailExerciseNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            appBarStyle.value = WHITE_STYLE
            MyExerciseDetailScreen(id = id!!, navHostController, paddingValues, appBarTitle)
        }

        composable(
            route = NavigationItem.ExerciseChooseScreenNavigationItem.route,
            arguments = listOf(navArgument("parentId") {
                type = NavType.LongType
            })
        ) {
            val parentId = it.arguments?.getLong("parentId")
            //topBarState.value = false он уже должен был быть убран
            Log.d("TAG", "parentId = $id")
            appBarStyle.value = WHITE_STYLE
            ExerciseChooseScreen(
                parentId = parentId!!,
                navHostController,
                paddingValues,
                appBarTitle
            )
        }

        composable(
            route = NavigationItem.ReviewExerciseScreenNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            //topBarState.value = false он уже должен был быть убран
            appBarStyle.value = WHITE_STYLE
            ExerciseReviewScreen(
                id = id!!,
                navHostController,
                paddingValues,
                appBarTitle
            )
        }


        // exercise end

        // Sets start
        // создать новый подход
        composable(
            route = NavigationItem.CreateSetsNavigationItem.route,
            arguments = listOf(navArgument("parentId") {
                type = NavType.LongType
            }),
        ) {
            //topBarState.value = false он уже должен был быть убран
            val parentId = it.arguments?.getLong("parentId")
            appBarStyle.value = DEFAULT_STYLE
            SetsCreateScreen(parentId!!, navHostController, paddingValues, appBarTitle)
        }

        // создать новый подход
        composable(
            route = NavigationItem.EditSetsNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            }),
        ) {
            //topBarState.value = false он уже должен был быть убран
            val id = it.arguments?.getLong("id")
            appBarStyle.value = DEFAULT_STYLE
            SetEditeScreen(id!!, navHostController, paddingValues, appBarTitle)
        }

        // Sets end

        // Workout start
        // workout detail
        composable(
            route = NavigationItem.DetailWorkoutNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            //topBarState.value = false он уже должен был быть убран
            Log.d("TAG", "id = $id")

            appBarStyle.value = WHITE_STYLE
            WorkoutDetailScreenM3(id = id!!, navHostController, paddingValues, appBarTitle)

        }


        composable(
            route = NavigationItem.CreateEditeCycleNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            }),
        ) {

            //topBarState.value = false он уже должен был быть убран
            val id = it.arguments?.getLong("id")
            Log.d("TAG", "id = $id")
            bottomBarState.value = false
            val action = if (id == 0L) {
                ActionState.CREATE
            } else {
                ActionState.UPDATE
            }
            appBarStyle.value = WHITE_STYLE
            CreateEditeCycleScreen(id!!, navHostController, paddingValues, appBarTitle, action)
        }

        //создание новой тренировки
        composable(
            route = NavigationItem.CreateWorkoutNavigationItem.route,
            arguments = listOf(navArgument("parentId") {
                type = NavType.LongType
            }),
        ) {
            //topBarState.value = false он уже должен был быть убран
            val parentId = it.arguments?.getLong("parentId")

            //  bottomBarState.value = false и так отключен ранее
            appBarStyle.value = WHITE_STYLE
            CreateWorkoutScreen(parentId!!, navHostController, paddingValues, appBarTitle)
        }

        composable(
            route = NavigationItem.EditeWorkoutNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            }),
        ) {
            //topBarState.value = false он уже должен был быть убран
            val id = it.arguments?.getLong("id")
            appBarStyle.value = WHITE_STYLE
            EditeWorkoutScreen(id!!, navHostController, paddingValues, appBarTitle)
        }

       // превью тренировки
        composable(
            route = NavigationItem.ReviewWorkoutNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            }),
        ) {
            //topBarState.value = false он уже должен был быть убран
            val id = it.arguments?.getLong("id")
            appBarStyle.value = DEFAULT_STYLE
            WorkoutReview(id!!, navHostController, paddingValues, appBarTitle, appBarActions)
        }

        composable(
            route = NavigationItem.EditSoundTimerWorkoutItem.route,

            ) {
            appBarStyle.value = WHITE_STYLE
            EditSoundTimerScreen(navHostController, paddingValues,  appBarTitle, appBarActions)
        }
    }
}

