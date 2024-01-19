package info.upump.jymlight.ui.screens.mainscreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ItemSwipeBackgroundIcon
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.ItemButton
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDescriptionVariableTitle
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navHostController: NavHostController, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    val launch = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            navHostController.navigate(NavigationItem.ChooseBackupProfileNavigation.route)
        }
    }


    Scaffold(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) { it ->
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                item {
                    CardDescriptionVariableTitle(title = stringResource(id = R.string.action_with_db))
                }
                item {
                    val state = remember {
                        mutableStateOf(false)
                    }
                    val dismissState = rememberDismissState(confirmStateChange = { value ->
                        true
                    })

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(),
                        background = {

                            ItemSwipeBackgroundIcon(
                                dismissState = dismissState,
                                state = state.value,
                                actionDelete = {}
                            )
                        },
                        dismissContent = {
                            Column(modifier = Modifier) {
                                ItemButton(
                                    action = {
                                        navHostController.navigate(NavigationItem.ChooseSendProfileNavigation.route)
                                    },
                                    icon = R.drawable.ic_send_to_email,
                                    title = stringResource(id = R.string.pref_title_write_to_email)
                                )
                                DividerCustom(dismissState, state = state.value)
                            }
                        }
                    )
                }
                item {
                    val state = remember {
                        mutableStateOf(false)
                    }
                    val dismissState = rememberDismissState(confirmStateChange = { value ->
                        true
                    })

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(),
                        background = {

                            ItemSwipeBackgroundIcon(
                                dismissState = dismissState,
                                state = state.value,
                                actionDelete = {}
                            )
                        },
                        dismissContent = {
                            Column(modifier = Modifier) {
                                ItemButton(
                                    action = {
                                        state.value = true
                                        launch.launch("*/*")
                                        state.value = false
                                    },
                                    icon = R.drawable.ic_down_to_db,
                                    title = stringResource(id = R.string.pref_title_read_from_db)
                                )
                                DividerCustom(dismissState, state = state.value)
                            }
                        }
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(NavHostController(LocalContext.current), PaddingValues())
}

