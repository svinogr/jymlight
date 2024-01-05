package info.upump.jymlight.ui.screens.mainscreen

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ItemSwipeBackgroundIcon
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.ItemButton
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDescriptionVariableTitle
import info.upump.jymlight.ui.screens.screenscomponents.screen.DividerCustom
import info.upump.jymlight.ui.screens.viewmodel.profile.ProfileVM
import info.upump.jymlight.utils.DBRestoreBackup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navHostController: NavHostController, paddingValues: PaddingValues) {
    val context = LocalContext.current

    val coroutine = rememberCoroutineScope()

    val profileVM: ProfileVM = viewModel()
    val loadState = profileVM.isLoading.collectAsState()

    val launch = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            val checkFile = File(it.path!!)
            Log.d("exe", "extension = ${checkFile.extension}")
            if (checkFile.extension == "db") {
                coroutine.launch(Dispatchers.IO) {
                    profileVM.load(it, context)
                }
            }
        }
    }

    val isLoad = remember {
        mutableStateOf(loadState.value)
    }

    Scaffold(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) { it ->
        val context = LocalContext.current

        Box(modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)) {
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
                                        state.value = true
                                        profileVM.send(context)
                                        state.value = false
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

            if (isLoad.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(64.dp),
                )
            }
        }
    }
}


private fun sendToDb(context: Context) {
    val backupDab = DBRestoreBackup()
    val intent = backupDab.getSendIntent(context)
    context.startActivity(intent)
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(NavHostController(LocalContext.current), PaddingValues())
}

