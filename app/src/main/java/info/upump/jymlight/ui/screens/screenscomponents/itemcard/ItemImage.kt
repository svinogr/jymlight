package info.upump.jymlight.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import info.upump.jymlight.utils.BitmapCreator

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    image: String,
    defaultImage: String,
    action: () -> Unit
) {
    AsyncImage(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                action()
            },
        contentScale = ContentScale.Crop,
        model = BitmapCreator.getImageWithUri(image, defaultImage),
        contentDescription = "image"
    )
}

@Preview(showBackground = true)
@Composable
fun ItemImagePreview() {
    ItemImage(
        image = info.upump.jymlight.ui.screens.viewmodel.cycle.CycleDetailVM.vmOnlyForPreview.img.collectAsState().value,
        defaultImage =
        info.upump.jymlight.ui.screens.viewmodel.cycle.CycleDetailVM.vmOnlyForPreview.imgDefault.collectAsState().value
    ) {}

}

