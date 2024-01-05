package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardTypeMuscle(
    text: String,
    readOnly: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
         //   colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, end = 8.dp, bottom = 0.dp),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                readOnly = readOnly,
                value = text,
                onValueChange = {
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.label_spinner_type_muscle),
                        style = MyTextTitleLabelWithColor
                    )
                }
            )
    }
}

@Preview(showBackground = true)
@Composable
fun CardTypeMusclePreview() {
    val title = "Sporty name"
    CardTypeMuscle(title)
}

