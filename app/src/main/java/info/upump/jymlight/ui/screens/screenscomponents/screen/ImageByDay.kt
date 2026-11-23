package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Day

@Composable
fun ImageByDay(
    day: Day,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val filterColor = context.getColor(day.getColor())

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.day_background),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color(filterColor), BlendMode.Multiply)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageByDayPreview() {
    ImageByDay(day = Day.MONDAY)
}

@Preview
@Composable
fun ImageByDayPreview2() {
    ImageByDay(day = Day.THURSDAY)
}