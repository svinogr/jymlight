package info.upump.jymlight.ui.screens.myworkoutsscreens.screens.exercisescreens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.models.entity.TypeMuscle
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardDescription
import info.upump.jymlight.ui.screens.screenscomponents.screen.CardTypeMuscle
import info.upump.jymlight.ui.screens.screenscomponents.screen.ImageForDetailScreen
import info.upump.jymlight.ui.screens.viewmodel.exercise.ExerciseReviewVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseReviewScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>,
) {
    val exerciseVM: ExerciseReviewVM = viewModel()
    appBarTitle.value = exerciseVM.item.collectAsState().value.exerciseDescription!!.title

    LaunchedEffect(key1 = true) {
        exerciseVM.getItem(id)
    }
    Scaffold { it ->
        Column {
            ImageForDetailScreen(modifier = Modifier.height(200.dp),
                image = exerciseVM.item.collectAsState().value.exerciseDescription!!.img,
                defaultImage = exerciseVM.item.collectAsState().value.exerciseDescription!!.defaultImg
            )

            CardTypeMuscle(text = stringResource(id = exerciseVM.item.collectAsState().value.typeMuscle.title))

            CardDescription(textDescription = exerciseVM.item.collectAsState().value.comment, modifier =  Modifier.weight(4f))
        }
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExersiceReviewScreenPreview() {
    val m: MutableState<String> =
        MutableStateFlow(" ").asStateFlow().collectAsState() as MutableState<String>
    Column {
        ImageForDetailScreen(
            image = "",
            defaultImage = "uk2"
        )

        CardTypeMuscle(text = stringResource(id = TypeMuscle.NECK.title))

        CardDescription(textDescription = "djkwgdjkghdwkdghlkdwjkdgwgjkwdgjkwdwdgjk", modifier =  Modifier.weight(4f))
    }
}
