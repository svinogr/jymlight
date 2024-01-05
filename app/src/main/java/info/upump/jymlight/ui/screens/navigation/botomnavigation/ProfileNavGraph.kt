package info.upump.jymlight.ui.screens.navigation.botomnavigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.upump.jymlight.ui.screens.mainscreen.ProfileScreen

const val PROFILE_ROOT_ROUT = "profileRootRout"

fun NavGraphBuilder.profileNavGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    appBarStyle: MutableState<Int>,
    context: Context,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = NavigationItem.ProfileNavigationItem.route,
        route = PROFILE_ROOT_ROUT
    ) {
        composable(route = NavigationItem.ProfileNavigationItem.route) {
            appBarTitle.value =
                context.resources.getString(NavigationItem.ProfileNavigationItem.title)
            ProfileScreen(navHostController, paddingValues)
        }
    }
}