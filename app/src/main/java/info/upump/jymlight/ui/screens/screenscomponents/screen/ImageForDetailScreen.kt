package info.upump.jymlight.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import info.upump.jymlight.utils.BitmapCreator
import info.upump.jymlight.ui.screens.viewmodel.cycle.CycleDetailVM

@Composable
fun ImageForDetailScreen(
    image: String,
    defaultImage: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = BitmapCreator.getImageWithUri(image, defaultImage),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ImageForDetailScreenPreview() {
    ImageForDetailScreen(
        image = CycleDetailVM.vmOnlyForPreview.img.collectAsState().value,
        defaultImage = CycleDetailVM.vmOnlyForPreview.imgDefault.collectAsState().value,
    )
}
