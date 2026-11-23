package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.ui.theme.MyOutlineTextTitleLabel20Text

@Composable
fun LabelTitleForImage(
    title: String,
    alignmentText: Alignment = Alignment.BottomStart,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        val modifier1 = Modifier
            .padding(bottom = 16.dp, start = 8.dp)
            .align(alignmentText)

        TextField(modifier = modifier1,
            textStyle = MyOutlineTextTitleLabel20Text,
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            ),
            readOnly = true,
            value = title,
            onValueChange = {
                // nothing to change
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 1)
@Composable
fun LabelTitleForImagePreview() {
    val title = "its Title"
    LabelTitleForImage(title)
}

