package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import info.upump.jymlight.R
import info.upump.jymlight.ui.theme.MyTextHeader16
import info.upump.jymlight.ui.theme.MyTextLabel16
import info.upump.jymlight.ui.theme.MyTextTitleHeaderl16
import info.upump.jymlight.ui.theme.MyTextTitleLabel16


enum class GuidelineSets(val offset: Float) {
    ONE(0.05f), TWO(0.20f), THREE(0.40f), FOUR(0.7f)

}

@Composable
fun TableHeader(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondary
                )
        ) {
            val textNumber = createRef()
            val textWeight = createRef()
            val textPasWeight = createRef()
            val textReps = createRef()
            val guiOne = createGuidelineFromStart(GuidelineSets.ONE.offset)
            val guiTwo = createGuidelineFromStart(GuidelineSets.TWO.offset)
            val guiThree = createGuidelineFromStart(GuidelineSets.THREE.offset)
            val guiFour = createGuidelineFromStart(GuidelineSets.FOUR.offset)
            val modifierOneThree = Modifier.padding(top = 8.dp, bottom = 8.dp)
            Text(
                text = "№",
                modifier = modifierOneThree.constrainAs(textNumber) {
                    start.linkTo(guiOne)
                }, style = MyTextTitleHeaderl16
            )
            Text(
                text = stringResource(id = R.string.label_weight_set),
                modifier = modifierOneThree.constrainAs(textWeight) {
                    start.linkTo(guiTwo)
                }, style = MyTextTitleHeaderl16
            )
            Text(
                text = stringResource(id = R.string.label_weight_set_past),
                modifier = modifierOneThree.constrainAs(textPasWeight) {
                    start.linkTo(guiThree)
                }, style = MyTextHeader16
            )
            Text(
                text = stringResource(id = R.string.label_reps_sets_short),
                modifier = modifierOneThree.constrainAs(textReps) {
                    start.linkTo(guiFour)
                }, style = MyTextTitleHeaderl16
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TableHeaderPreview() {
    TableHeader()
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TableHeaderPreview2() {
    TableHeader(modifier = Modifier.background(colorResource(id = R.color.colorBackgroundChips)))
}