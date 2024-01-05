package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowChooseChips(modifier: Modifier = Modifier, vararg chips: CheckChips) {
    val scrollState = rememberScrollState()
    val chipsState = remember{
        mutableStateOf(chips)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp)
            .horizontalScroll(scrollState)
    ) {
        chipsState.value.forEach { chips ->
            AssistChip(
                onClick = {
                    chips.action()
                    chips.check = !chips.check
                    chipsState.value.forEach {
                        if(it.title != chips.title){
                            it.check = false
                        }
                    }
                },
                label = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = chips.title,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                leadingIcon =
                {
                    if (chips.check) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RowChooseChipsPreview() {
    RowChooseChips(
        modifier = Modifier,
        CheckChips(false, "Коментарий", R.drawable.ic_info_black_24dp, ::println),
        CheckChips(true, "Коментарий2", R.drawable.ic_add_black_24dp, ::println)
    )
}
