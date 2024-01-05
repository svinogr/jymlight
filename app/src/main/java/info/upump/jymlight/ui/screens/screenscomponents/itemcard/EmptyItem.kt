package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R

@Composable
fun EmptyItem(size: Dp = 50.dp,
              modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background).fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(size)
                    .width(size)
            ) {

            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyItemPreview(){
    EmptyItem()
}