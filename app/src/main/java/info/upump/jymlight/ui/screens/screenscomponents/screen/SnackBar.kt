package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackBar(text: String, icon: Int, modifier: Modifier = Modifier, action: () -> Unit) {
        Snackbar(
            modifier = modifier.padding(16.dp),
             containerColor =  MaterialTheme.colorScheme.background,
            action = {
                AssistChip(
                    label = { Text("да", style = TextStyle(MaterialTheme.colorScheme.onBackground))
                         },
                    modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                         colors = AssistChipDefaults.assistChipColors(
                       // containerColor = colorResource(id = R.color.colorBackgroundChips)
                    ),
                    onClick = { action() },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = icon),
                            " ",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }
        ) {
            Text(text = text, style = TextStyle(MaterialTheme.colorScheme.onBackground))
        }
}

@Preview
@Composable
fun SnackBarpreview() {
    SnackBar("удалит45ь?", R.drawable.ic_delete_24) {

    }
}

