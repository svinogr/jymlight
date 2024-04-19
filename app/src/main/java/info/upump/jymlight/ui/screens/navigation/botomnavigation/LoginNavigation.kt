package info.upump.jymlight.ui.screens.navigation.botomnavigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.upump.jymlight.ui.screens.loginscreens.LoginScreen
import info.upump.jymlight.ui.screens.loginscreens.SignupScreen
import info.upump.jymlight.ui.screens.mainscreen.AppBarAction
import info.upump.jymlight.ui.screens.mainscreen.DEFAULT_STYLE
import info.upump.jymlight.ui.screens.mainscreen.TemplateStartScreen

const val LOGIN_ROOT_ROUTE = "loginRootRout"

fun NavGraphBuilder.loginGraph(
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
        startDestination = NavigationItem.LoginScreenNavigationItem.route,
        route = LOGIN_ROOT_ROUTE
    ) {
        composable(route = NavigationItem.SignupScreenNavigationItem.route) {
            topBarState.value = false
            bottomBarState.value = false
            appBarStyle.value = DEFAULT_STYLE
            SignupScreen(navHostController, paddingValues)
        }
        composable(route = NavigationItem.LoginScreenNavigationItem.route) {
            topBarState.value = false
            bottomBarState.value = false
            appBarStyle.value = DEFAULT_STYLE
            LoginScreen(navHostController, paddingValues)
        }
    }
}
