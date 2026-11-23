package info.upump.jymlight.ui.screens.screenscomponents

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R

@Composable
fun FloatActionButtonWithState(isVisible: Boolean, icon: Int, action: () -> Unit) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { 100.dp.roundToPx() }
        },
        exit = slideOutVertically {
            with(density) { 100.dp.roundToPx() }
        }
    ) {
        FloatingActionButton(
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = {
                action()
            },
            content = {
                Icon(
                    painter = painterResource(id =icon),
                    contentDescription = ""
                )
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewFab(){
   FloatActionButtonWithState(true, icon = R.drawable.ic_add_black_24dp) {
   }
}
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewFab2(){
 FloatActionButtonWithState(true, icon = R.drawable.ic_edit_black_24dp) {
    }
}

