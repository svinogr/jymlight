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
import info.upump.jymlight.ui.screens.defaultscreen.screen.cyclescreen.DefaultCycleDetailScreenM3
import info.upump.jymlight.ui.screens.defaultscreen.screen.exercise.DefaultExerciseDetailScreen
import info.upump.jymlight.ui.screens.defaultscreen.screen.workoutscreens.DefaultWorkoutDetailScreenM3
import info.upump.jymlight.ui.screens.mainscreen.DEFAULT_STYLE
import info.upump.jymlight.ui.screens.mainscreen.TemplateStartScreen
import info.upump.jymlight.ui.screens.mainscreen.WHITE_STYLE

const val DEFAULT_CYCLE_ROOT_ROUTE = "default_cycle_root_route"

fun NavGraphBuilder.defaultCycleGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    appBarStyle: MutableState<Int>,
    context: Context,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = NavigationItem.DefaultCycleNavigationItem.route,
        route = DEFAULT_CYCLE_ROOT_ROUTE
    )
    {
        composable(
            route = NavigationItem.DefaultCycleNavigationItem.route
        ) {
            appBarTitle.value =
                context.resources.getString(NavigationItem.DefaultCycleNavigationItem.title)
            topBarState.value = true
            bottomBarState.value = true
            appBarStyle.value = DEFAULT_STYLE
            TemplateStartScreen(navHostController, paddingValues)
        }

        composable(
            route = NavigationItem.DefaultDetailCycleNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            Log.d("ddw", "dwdw")
            topBarState.value = false
            bottomBarState.value = false
            appBarTitle.value =
                context.resources.getString(NavigationItem.DefaultCycleNavigationItem.title)
            appBarStyle.value = WHITE_STYLE
            DefaultCycleDetailScreenM3(id = id!!, navHostController, paddingValues, appBarTitle)
        }

        composable(
            route = NavigationItem.DefaultDetailWorkoutNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            //topBarState.value = false он уже должен был быть убран
            Log.d("TAG", "id = $id")

            appBarStyle.value = WHITE_STYLE
            DefaultWorkoutDetailScreenM3(id = id!!, navHostController, paddingValues, appBarTitle)

        }

        composable(
            route = NavigationItem.DefaultDetailExerciseNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            //topBarState.value = false он уже должен был быть убран
            Log.d("TAG", "id = $id")

            appBarStyle.value = WHITE_STYLE
            DefaultExerciseDetailScreen(id = id!!, navHostController, paddingValues, appBarTitle)

        }
    }
}