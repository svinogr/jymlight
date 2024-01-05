package info.upump.jymlight.ui.screens.screenscomponents

import android.net.Uri

class BitmapCreator {
    companion object {
        private const val DEFAULT_IMAGE = "drew"
        private const val DEFAULT_IMAGE_ROUTE = "android.resource://info.upump.jym/drawable"
        fun getImageWithUri(image: String, defaultImage: String): Uri {
            val uri: Uri = if (image.isNotBlank()) {
                Uri.parse(image)
            } else if (defaultImage.isNotBlank()) {
                Uri.parse("$DEFAULT_IMAGE_ROUTE/$defaultImage")
            } else {
                Uri.parse("$DEFAULT_IMAGE_ROUTE/$DEFAULT_IMAGE")
            }

            return uri
        }
    }
}