package info.upump.jymlight.ui.screens.screenscomponents.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.ui.theme.MyWatchTitleLabel20

@Composable
fun SoundTimer(
    modifier: Modifier = Modifier,
    soundTime: String,
    start: Int,
    finish: Int,
    editAction: () -> Unit,
    startAction: () -> Unit
) {
    val context = LocalContext.current
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
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
        ) {
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
                    style = MyWatchTitleLabel20,
                    text = soundTime,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
                        .weight(1.5f)
                )
                Text(
                    style = MyWatchTitleLabel20,
                    text = "$start / $finish",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
                        .weight(1.5f).align(Alignment.CenterVertically)
                )
                OutlinedButton(
                    onClick = {editAction()},
                    modifier
                        .padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit_black_24dp),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "drawable icons",
                        tint = colorResource(id = R.color.colorBackgroundCardView)
                    )
                }
                OutlinedButton(
                    onClick = {startAction()},
                    modifier
                        .padding(start = 4.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_btn_play_24),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "drawable icons",
                        tint = colorResource(id = R.color.colorBackgroundCardView)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewSoundTimer() {
    SoundTimer(modifier = Modifier.fillMaxWidth(), soundTime = "0000000", start = 10, finish = 60, {}, {})
}