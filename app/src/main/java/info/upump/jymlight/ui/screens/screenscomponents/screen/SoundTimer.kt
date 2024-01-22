package info.upump.jymlight.ui.screens.screenscomponents.screen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.ui.theme.MyTextTitleLabel16
import info.upump.jymlight.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundTimer(modifier: Modifier = Modifier, start: Long, finish: Long) {
    val stateStart = remember {
        mutableDoubleStateOf(0.0)
    }
    val stateFinish = remember {
        mutableDoubleStateOf(0.0)
    }
    Card(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Column(modifier = modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth()) {
            Divider(
                color = MaterialTheme.colorScheme.onTertiary,
                thickness = DividerDefaults.Thickness, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = MyTextTitleLabel16,
                    text = "сигнал",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                        .weight(1f)
                )
                TextField(
                    modifier = Modifier
                        .drawCustomIndicatorLine(
                            BorderStroke(
                                DividerDefaults.Thickness,
                                MaterialTheme.colorScheme.background,
                                //      colorResource(R.color.colorBackgroundChips)
                            ), 8.dp
                        )
                        .padding(top = 0.dp, end = 0.dp, bottom = 0.dp).weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                        disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
                        containerColor = MaterialTheme.colorScheme.background,
                        textColor = MaterialTheme.colorScheme.onBackground
                    ),
                    value = stateStart.doubleValue.toString(),
                    onValueChange = { },
                    label = {
                        Text(
                            text ="через",
                            style = MyTextTitleLabelWithColor
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.label_description_cycle))
                    }
                )
                TextField(
                    modifier = Modifier
                        .drawCustomIndicatorLine(
                            BorderStroke(
                                DividerDefaults.Thickness,
                                MaterialTheme.colorScheme.background,
                            ), 8.dp
                        )
                        .padding(top = 0.dp, end = 0.dp, bottom = 0.dp).weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                        disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
                        containerColor = MaterialTheme.colorScheme.background,
                        textColor = MaterialTheme.colorScheme.onBackground
                    ),
                    value = stateFinish.doubleValue.toString(),
                    onValueChange = {
                        { println() }
                    },
                    label = {
                        Text(
                          //  text = stringResource(id = R.string.label_description_cycle),
                            text = "закончить",
                            style = MyTextTitleLabelWithColor
                        )
                    })

            }
            OutlinedButton(
                onClick = {},
                modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(style = MyTextTitleLabel16, text = "start", modifier = Modifier)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewSoundTimer() {
    SoundTimer(modifier = Modifier.fillMaxWidth(), start = 0, finish = 0)
}