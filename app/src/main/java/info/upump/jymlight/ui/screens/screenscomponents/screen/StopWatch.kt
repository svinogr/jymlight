package info.upump.jymlight.ui.screens.screenscomponents.screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.ui.theme.MyTextTitleLabel16
import info.upump.jymlight.ui.theme.MyWatchTitleLabel20

enum class StopWatchState {
    STOP, RESUME, PAUSE
}

@Composable
fun StopWatch(
    time: String,
    stopwatchState: StopWatchState,
    modifier: Modifier = Modifier,
    stop: () -> Unit,
    start: () -> Unit,
    pause: () -> Unit,
    resume: () -> Unit,
) {
    Card(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
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
            Row(modifier = Modifier.align(CenterHorizontally)) {
                Text(
                    style = MyWatchTitleLabel20,
                    text = time,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            }
            Row {
                if (stopwatchState == StopWatchState.STOP) {
                    OutlinedButton(
                        onClick = {start()},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "start", modifier = Modifier)
                    }
                }

                if (stopwatchState == StopWatchState.PAUSE) {
                    OutlinedButton(
                        onClick = {stop()},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "stop", modifier = Modifier)
                    }
                    OutlinedButton(
                        onClick = {resume()},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "continue", modifier = Modifier)
                    }
                }

                if (stopwatchState == StopWatchState.RESUME) {
                    OutlinedButton(
                        onClick = {stop()},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "stop", modifier = Modifier)
                    }
                    OutlinedButton(
                        onClick = {pause()},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "pause", modifier = Modifier)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StopWatchStopPreview() {
    StopWatch(time = "00:00:00", StopWatchState.STOP, start = ::println, stop = ::println, pause = ::println, resume = ::println)
}

@Preview(showBackground = true)
@Composable
fun StopWatchPausePreview() {
    StopWatch("00:00:00", StopWatchState.PAUSE, start = ::println, stop = ::println, pause = ::println, resume = ::println)
}

@Preview(showBackground = true)
@Composable
fun StopWatchResumePreview() {
    StopWatch("00:00:00", StopWatchState.RESUME, start = ::println, stop = ::println, pause = ::println, resume = ::println)
}
