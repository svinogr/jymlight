package info.upump.jymlight.ui.screens.mainscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.navigation.botomnavigation.MyBottomNavigation
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavGraph
import info.upump.jymlight.ui.screens.screenscomponents.screen.SnackBar
import info.upump.jymlight.ui.theme.MyOutlineTextTitleLabel20Text
import info.upump.jymlight.ui.theme.MyTextTitleLabel20
import kotlinx.coroutines.launch

const val WHITE_STYLE = 1
const val DEFAULT_STYLE = 0

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val appBarTitle = remember {
        mutableStateOf("")
    }

    val topBarState = remember {
        mutableStateOf(true)
    }

    val bottomBarStat = remember {
        mutableStateOf(true)
    }

    val appBarStyle = remember {
        mutableIntStateOf(DEFAULT_STYLE)
    }

    val appBarActions = remember {
        mutableStateOf(listOf<AppBarAction>())
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    val density = LocalDensity.current

    Scaffold(
        bottomBar =
        {
            AnimatedVisibility(modifier = Modifier.fillMaxWidth(),
                visible = bottomBarStat.value,
                enter = slideInVertically() {
                    with(density) { 60.dp.roundToPx() }
                } + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() { with(density) { 60.dp.roundToPx() } }
            ) {
                MyBottomNavigation(navController)
            }
        },
        snackbarHost = {
            SnackbarHost(
                snackBarHostState
            ) {
                SnackBar(stringResource(id = R.string.snack_exit_app), R.drawable.ic_exit) {
                    activity.finish()
                }
            }
        },

        topBar = {
            AnimatedVisibility(modifier = Modifier.fillMaxWidth(),
                visible = true,
                enter = slideInVertically() {
                    with(density) { -60.dp.roundToPx() }
                } + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() {
                    with(density) { -60.dp.roundToPx() }
                }
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = if (appBarStyle.intValue == WHITE_STYLE) {
                            Color.Transparent
                        } else MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text(
                            text = appBarTitle.value,
                            style = if (appBarStyle.intValue == WHITE_STYLE)
                                MyOutlineTextTitleLabel20Text
                            else
                                MyTextTitleLabel20,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },

                    actions = {
                       // Log.d("click", "click2 ${appBarActions.value.size}")
                        for (i in appBarActions.value) {
                            val coroutineScope = rememberCoroutineScope()
                            IconButton(onClick = {
                        //        Log.d("click", "click")
                                coroutineScope.launch() {
                                    i.action()
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = i.icon),
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                )
            }
        },
    ) { padding ->
        NavGraph(
            navController,
            appBarTitle,
            appBarStyle,
            padding,
            topBarState,
            bottomBarStat,
            appBarActions
        )
    }

    BackHandler {
        coroutine.launch {
            snackBarHostState.showSnackbar("")
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}

class AppBarAction(val icon: Int, val action: suspend () -> Unit) {

}

