package info.upump.jymlight.ui.screens.screenscomponents.itemcard.item

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.CycleCheck
import info.upump.jymlight.ui.theme.MyTextLabel12
import info.upump.jymlight.ui.theme.MyTextTitleLabel16

@Composable
fun CycleItemCardWithCheck(
    modifier: Modifier = Modifier,
    cycle: CycleCheck,
    action: () -> Unit,
) {
    SideEffect {
        Log.d("check", "side CycleItemCardWithCheck ${cycle.isCheck} ${cycle.cycle.id}")
    }
    val state = remember {
        mutableStateOf(cycle.isCheck)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
            .clickable {
                // action()
            },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )

        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
            ) {

                Checkbox(checked = state.value,
                    onCheckedChange = {
                       state.value = it
                       action()
                    })
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                val modifierCol = Modifier
                    .padding(end = 8.dp)
                Text(
                    text = cycle.cycle.title!!.capitalize(),
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.fillMaxWidth()
                )

                if (!cycle.cycle.isDefaultType) {
                    Box(
                        modifier = modifierCol
                            .align(Alignment.End)
                            .padding(top = 4.dp)
                    ) {
                        Text(
                            text = cycle.cycle.finishStringFormatDate,
                            style = MyTextLabel12,
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCycleItemCardWithCheck() {
    val c = CycleCheck(cycle = Cycle().apply {
        title = "Новая"
        imageDefault = ""
        image = ""
    }, true)

    CycleItemCardWithCheck(cycle = c, action = ::println)
}