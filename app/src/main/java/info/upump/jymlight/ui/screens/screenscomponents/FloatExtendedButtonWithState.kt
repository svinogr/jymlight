package info.upump.jymlight.ui.screens.screenscomponents

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R

@Composable
fun FloatExtendedButtonWithState(
        text: String, isVisible: Boolean,
        icon: Int, modifier: Modifier = Modifier, action: () -> Unit
) {
    val text = text.replace(" ", "\n")
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
        ExtendedFloatingActionButton(
                modifier = modifier,
                containerColor =  MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp),
                text = {
                    Text(
                            textAlign = TextAlign.Center,
                            text = text
                    )
                },
                onClick = {
                    action()
                },
                icon = {
                    Icon(
                            painter = painterResource(id = icon),
                            contentDescription = ""
                    )
                }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewFloatExtendedButtonWithState() {
    FloatExtendedButtonWithState(
            "Добавить тренировку",
            true,
            icon = R.drawable.ic_add_black_24dp
    ) {
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = false, showSystemUi = true)
@Composable
fun PreviewFloatExtendedButtonWithState2() {
    FloatExtendedButtonWithState(
            "Добавить тренировку",
            true,
            icon = R.drawable.ic_edit_black_24dp
    ) {
    }
}


@Composable
fun FloatButtonWithState(
        text: String, isVisible: Boolean,
        icon: Int, modifier: Modifier = Modifier, action: () -> Unit
) {
    val text = text.replace(" ", "\n")
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
                modifier = modifier,
             //   containerColor =  MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp),

                onClick = {
                    action()
                }) {
            Icon(
                    painter = painterResource(id = icon),
                    contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloatButtonWithStatePreview() {
    FloatButtonWithState(
            "Добавить тренировку",
            true,
            icon = R.drawable.ic_edit_black_24dp
    ) {
    }
}

