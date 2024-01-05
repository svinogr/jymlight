package info.upump.jymlight.ui.screens.screenscomponents.itemcard.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jymlight.R
import info.upump.jymlight.ui.theme.MyTextTitleLabel16

@Composable
fun ItemButton(
        modifier: Modifier = Modifier,
        title: String,
        icon: Int,
        action: () -> Unit
) {
    Card(
            modifier = modifier.background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(0.dp)
                    .clickable { action() },
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(0.dp),

            ) {
        Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
        )
        {
            Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                            .padding(8.dp)
                            .height(50.dp)
                            .width(50.dp)
            ) {
                Icon(modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp),
                        painter = painterResource(id = icon),
                        contentDescription = "")
            }
            Column(modifier = modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth()) {
                Text(
                        text = title,
                        style = MyTextTitleLabel16,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ItemButtonPreview() {
    ItemButton(action = ::println, icon = R.drawable.ic_down_to_db, title = "text")
}
