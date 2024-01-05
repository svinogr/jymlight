package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImageTitleImageTitle(modifier: Modifier = Modifier, title: String, isTitleError: Boolean, updateText: (String) -> Unit,  content: @Composable() ()-> Unit) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            content()
            LabelTitleForImage(title, modifier = Modifier.align(Alignment.BottomStart))
        }
        CardTitle(title, isTitleError, updateText)
    }
}
@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreviewCycleWithDefaultImage() {
    val f: (String) -> Unit = { println(it) }
        ImageTitleImageTitle(Modifier, "its a title", true, f){
       ImageWithPicker("", "uk1", ::println)
    }
}

