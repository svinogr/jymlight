package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
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
fun CardDescriptionWithEdit(
    textComment: String,
    updateComment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
         //   colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        TextField(modifier = Modifier
            .drawCustomIndicatorLine(
                BorderStroke(
                    DividerDefaults.Thickness,
                    MaterialTheme.colorScheme.background,
                    //      colorResource(R.color.colorBackgroundChips)
                ), 8.dp
            )
            .fillMaxWidth()
            .padding(top = 0.dp, end = 0.dp, bottom = 0.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                //focusedIndicatorColor = colorResource(R.color.colorBackgroundCardView),
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                //unfocusedIndicatorColor = colorResource(R.color.colorBackgroundCardView),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
               // containerColor = colorResource(R.color.colorBackgroundCardView),
                containerColor =MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground
            ),
            value = textComment,
            onValueChange = {
                updateComment(it)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.label_description_cycle),
                    style = MyTextTitleLabelWithColor
                )
            },
            placeholder = {
                Text(text = stringResource(id = R.string.label_description_cycle))
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DescriptionCardWithVMPreview() {
    CardDescriptionWithEdit("its comment", ::print)
}

