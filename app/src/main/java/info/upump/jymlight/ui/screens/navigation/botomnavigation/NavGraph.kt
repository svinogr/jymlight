package info.upump.jymlight.ui.screens.navigation.botomnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import info.upump.jymlight.ui.screens.mainscreen.AppBarAction

const val NAV_GRAPH_ROOT_ROUTE = "navgraphRootRoute"

@Composable
fun NavGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    appBarStyle: MutableState<Int>,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>,
    appBarActions: MutableState<List<AppBarAction>>
) {
    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = LOGIN_ROOT_ROUTE,
        route = NAV_GRAPH_ROOT_ROUTE
    ) {
        myCycleGraph(navHostController, appBarTitle, appBarStyle,  context, paddingValues, topBarState, bottomBarState, appBarActions)

        defaultCycleGraph(navHostController, appBarTitle,appBarStyle,  context, paddingValues, topBarState, bottomBarState)

        profileNavGraph(navHostController,appBarTitle,appBarStyle,  context, paddingValues, topBarState, bottomBarState)

        loginGraph(navHostController,appBarTitle,appBarStyle,  context, paddingValues, topBarState, bottomBarState, appBarActions)
    }
}
