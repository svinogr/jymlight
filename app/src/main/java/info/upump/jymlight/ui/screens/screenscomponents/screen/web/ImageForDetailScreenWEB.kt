package info.upump.jymlight.ui.screens.screenscomponents.screen.web

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import info.upump.jymlight.utils.BitmapCreator
import info.upump.jymlight.ui.screens.viewmodel.db.cycle.CycleDetailVMBD

@Composable
fun ImageForDetailScreenWEB(
    image: String,
    defaultImage: String,
    modifier: Modifier = Modifier
) {
    Log.d("image", "imd $image | $defaultImage")
    val requestId = remember {
        mutableIntStateOf(0)
    }
    val request = ImageRequest.Builder(LocalContext.current)
        .data(BitmapCreator.getImageWithWeb(image, defaultImage))
        .setParameter("requestId", requestId, memoryCacheKey = null)
        .build()
    Box(modifier = modifier) {
        AsyncImage(
            model = request,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ImageForDetailScreenPreview() {
    ImageForDetailScreenWEB(
        image = CycleDetailVMBD.vmOnlyForPreview.img.collectAsState().value,
        defaultImage = CycleDetailVMBD.vmOnlyForPreview.imgDefault.collectAsState().value,
    )
}
