package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DividerCustom(
    dismissState: DismissState,
    modifier: Modifier = Modifier,
    state: Boolean = false
) {
    val direction = dismissState.dismissDirection
    val c = MaterialTheme.colorScheme.background
    val color = remember {
        mutableStateOf(c)
    }
    val padding = remember {
        mutableStateOf(8.dp)
    }

    if (direction == DismissDirection.StartToEnd ||
        direction == DismissDirection.EndToStart || state
    ) {
        color.value = MaterialTheme.colorScheme.onTertiary
        padding.value = 0.dp
    } else {
        color.value = MaterialTheme.colorScheme.background
        padding.value = 8.dp
    }
    Row(modifier = modifier) {
        Divider(
            color = color.value,
            modifier = Modifier
                .width(64.dp)
                .height(1.dp)
        )
        Divider(
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = padding.value)
                .height(1.dp),
        )


    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DividerPreview() {
    val dismissState = rememberDismissState(confirmStateChange = { value ->
        if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
        }

        true
    })
    dismissState.performDrag(10f)
    DividerCustom(dismissState)
}

@Preview(showBackground = true)
@Composable
fun klkl() {
    Divider(
        //    color = colorResource(id = R.color.colorBackgroundChips),
        color = Color.Red,
        thickness = 0.5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 64.dp, end = 8.dp)

    )
}

@Composable
fun DividerCustomBottom(modifier: Modifier = Modifier, state: Boolean = false) {
    val padding = remember {
        mutableStateOf(PaddingValues(start = 64.dp, end = 8.dp))
    }
    if (state) {
        padding.value = PaddingValues(0.dp)
    } else {
        padding.value = PaddingValues(start = 64.dp, end = 8.dp)
    }

    Row(modifier = modifier.fillMaxWidth()) {
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.value)
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DividerCustomDefPreview(modifier: Modifier = Modifier, state: Boolean = false) {
    DividerCustomBottom(state = true)
}

@Composable
fun DividerCustomTop(modifier: Modifier = Modifier, state: Boolean = false) {
    val color = remember {
        mutableStateOf(Color.Transparent)
    }
    if (state) {
        color.value = MaterialTheme.colorScheme.background
    } else color.value = Color.Transparent
    Divider(
        thickness = 1.dp,
        color = color.value,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-1).dp)
    )

}