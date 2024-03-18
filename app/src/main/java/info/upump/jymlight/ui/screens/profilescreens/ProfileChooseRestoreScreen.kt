package info.upump.jymlight.ui.screens.profilescreens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import info.upump.database.entities.SetsEntity
import info.upump.database.entities.WorkoutEntity
import info.upump.database.entities.WorkoutFullEntity
import info.upump.database.repo.db.CycleRepoDB
import info.upump.jym.utils.JSONRestoreBackup
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.CycleCheck
import info.upump.jymlight.ui.screens.mainscreen.isScrollingUp
import info.upump.jymlight.ui.screens.screenscomponents.FloatButtonWithState
import info.upump.jymlight.ui.screens.screenscomponents.itemcard.ListItemCycleWithCheck
import info.upump.jymlight.ui.screens.viewmodel.db.cycle.CycleCheckVM
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileChooseRestoreScreen(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val listState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val cycleVM: CycleCheckVM = viewModel()
    val listCycles = cycleVM.cycleList
    LaunchedEffect(key1 = true) {
      cycleVM.getAllPersonalFromFile(context, JSONRestoreBackup())
    }
    val list = remember {
        mutableStateOf(listCycles)
    }

    val action: (Long) -> Unit = cycleVM::checkItem

    Scaffold(modifier = Modifier.padding(paddingValues = paddingValues),
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.save_Capitalizw),
                isVisible = listState.isScrollingUp(), icon = R.drawable.ic_down_to_db
            ) {
                coroutine.launch {
                    writeToDb(context, cycleVM.getCheckedCycle()) {
                        navController.popBackStack()
                    }
                }
            }
        }
    ) { it ->
        Column {
            ListItemCycleWithCheck(
                lazyListState = listState,
                list = list.value.collectAsState().value,
                action = action
            )
        }
    }
}

suspend fun writeToDb(context: Context, list: List<Cycle>, function: () -> Unit) {
    withContext(Dispatchers.IO)
    {
        val listFullEntities = mutableListOf<Deferred<CycleFullEntity>>()
        // val start = System.currentTimeMillis()

        for (cE in list) {

            val cycleFE = async {

                val cycleE = CycleEntity().apply {
                    _id = cE.id
                    title = cE.title
                    comment = cE.comment
                    default_type = if (cE.isDefaultType) 1 else 0
                    img = null
                    start_date = cE.startStringFormatDate
                    finish_date = cE.finishStringFormatDate
                    default_img = cE.imageDefault
                }

                val listWFE = mutableListOf<WorkoutFullEntity>()

                for (w in cE.workoutList) {
                    //* async {*//*
                    val wE = WorkoutEntity().apply {
                        _id = 0
                        title = w.title
                        comment = w.comment
                        week_even = if (w.isWeekEven) 0 else 1
                        default_type = if (w.isDefaultType) 1 else 0
                        template = if (w.isTemplate) 0 else 1
                        day = w.day.name
                        start_date = w.startStringFormatDate
                        finish_date = w.finishStringFormatDate
                        parent_id = cE.id
                    }

                    val listEFE = mutableListOf<ExerciseFullEntity>()

                    for (e in w.exercises) {

                        val eE = ExerciseEntity().apply {
                            _id = e.id
                            comment = e.comment
                            description_id = e.descriptionId
                            type_exercise = e.typeMuscle.name
                            default_type = if (e.isDefaultType) 1 else 0
                            template = if (e.isTemplate) 1 else 0
                            start_date = e.startStringFormatDate
                            finish_date = e.finishStringFormatDate
                            parent_id = e.parentId
                        }

                        val listSFE = mutableListOf<SetsEntity>()

                        for (s in e.setsList) {
                            val sE = SetsEntity().apply {
                                _id = s.id
                                comment = s.comment
                                weight = s.weight
                                reps = s.reps
                                start_date = s.startStringFormatDate
                                finish_date = s.finishStringFormatDate
                                parent_id = s.parentId
                                past_set = s.weightPast
                            }
                            listSFE.add(sE)
                        }

                        val exFullEntity = ExerciseFullEntity(
                            eE, ExerciseDescriptionEntity(), listSFE
                        )

                        listEFE.add(exFullEntity)

                    }
                    val wFullEntity = WorkoutFullEntity(
                        wE, listEFE
                    )

                    listWFE.add(wFullEntity)
                }

                return@async CycleFullEntity(
                    cycleE, listWFE
                )
            }
            //        Log.d("restore 2 ", cycleFE.await().cycleEntity.title)
            listFullEntities.add(cycleFE)
            //      Log.d("restore 2 ", listFullEntities.size.toString())
        }
        //}
        //     Log.d("restore time", "${System.currentTimeMillis() - start}")
        val cycleRepoDB = CycleRepoDB.get() as CycleRepoDB
        cycleRepoDB.saveFullEntitiesOnlyFromOtherDB(listFullEntities.awaitAll())
        withContext(Dispatchers.Main) { function() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileChoose() {
    val listState = rememberLazyListState()

    val list = listOf(
        CycleCheck(Cycle(title = "1", id = 1)),
        CycleCheck(Cycle(title = "2", id = 2)),
        CycleCheck(Cycle(title = "3", id = 3))
    )

    Scaffold(
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.save_Capitalizw),
                isVisible = listState.isScrollingUp(), icon = R.drawable.ic_send_to_email
            ) {}
        }
    ) { it ->
        Column(modifier = Modifier.fillMaxWidth()) {
            ListItemCycleWithCheck(
                lazyListState = listState,
                list = list,
            ) {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileChooseScreenWithChooseALL(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val listState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val cycleVM: CycleCheckVM = viewModel()
    val listCycles = cycleVM.cycleList

    LaunchedEffect(key1 = true) {
        cycleVM.getAllPersonalFromDB()
    }

    val list = remember {
        mutableStateOf(listCycles)
    }

    val action: (Long) -> Unit = cycleVM::checkItem

    Scaffold(modifier = Modifier.padding(paddingValues = paddingValues),
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.save_Capitalizw),
                isVisible = listState.isScrollingUp(), icon = R.drawable.ic_send_to_email
            ) {
                coroutine.launch {
                    Log.d("check", "${cycleVM.getCheckedCycle().size}")

                    // profileVM.send(context, cycleVM.getCheckedCycle())
                }
            }
        }
    ) { it ->
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier.padding(start = 8.dp),
                    checked = true,
                    onCheckedChange = {}
                )
                Text(text = "ВСЕ")
            }
            ListItemCycleWithCheck(
                lazyListState = listState,
                list = list.value.collectAsState().value,
                action = action
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileChooseScreenWithChooseALL() {
    val listState = rememberLazyListState()

    val list = listOf(
        CycleCheck(Cycle(title = "1", id = 1)),
        CycleCheck(Cycle(title = "2", id = 2)),
        CycleCheck(Cycle(title = "3", id = 3))
    )

    Scaffold(
        floatingActionButton = {
            FloatButtonWithState(
                text = stringResource(id = R.string.save_Capitalizw),
                isVisible = listState.isScrollingUp(), icon = R.drawable.ic_send_to_email
            ) {

            }
        }
    ) { it ->
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier.padding(start = 8.dp),
                    checked = true,
                    onCheckedChange = {}
                )
                Text(text = "ВСЕ")
            }
            ListItemCycleWithCheck(
                lazyListState = listState,
                list = list,
            ) {}
        }
    }
}
