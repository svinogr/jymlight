package info.upump.jymlight.ui.screens.screenscomponents.itemcard.item.web

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import info.upump.jymlight.utils.BitmapCreator

@Composable
fun ItemImageWeb(
    modifier: Modifier = Modifier,
    image: String,
    defaultImage: String,
    action: () -> Unit
) {
    val requestId = remember {
        mutableIntStateOf(0)
    }
    val request = ImageRequest.Builder(LocalContext.current)
        .data(BitmapCreator.getImageWithWeb(image, defaultImage))
        .setParameter("requestId", requestId, memoryCacheKey = null)
        .build()
    AsyncImage(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                action()
            },
        contentScale = ContentScale.Crop,
        model = request,
        contentDescription = "image"
    )
}