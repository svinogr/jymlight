package info.upump.jymlight.utils

import android.net.Uri
import android.util.Log

class BitmapCreator {
    companion object {
       // private const val DEFAULT_IMAGE = "drew"
      //  private const val DEFAULT_IMAGE_ROUTE = "android.resource://info.upump.jymlight/drawable"
        private const val DEFAULT_API_PATH = "http://10.0.2.2:8080/api/image"
        private const val DEFAULT_IMAGE = "drew"
        private const val DEFAULT_IMAGE_ROUTE = "android.resource://info.upump.jymlight/drawable"
        fun getImageWithUri(image: String, defaultImage: String): Uri {
            Log.d("image", "$image $defaultImage")

            val uri: Uri = if (image.isNotBlank()) {
               Uri.parse(image)
            } else if (defaultImage.isNotBlank()) {
               Uri.parse("$DEFAULT_IMAGE_ROUTE/$defaultImage")
            } else {
                Uri.parse("$DEFAULT_IMAGE_ROUTE/$DEFAULT_IMAGE")
            }
            Log.d("image uri", "$uri")
            return uri
        }

        fun getImageWithWeb(image: String, defaultImage: String): Uri {
            Log.d("image", "$image $defaultImage")

            val uri: Uri = if (image.isNotBlank()) {
                Uri.parse("$DEFAULT_API_PATH/$image")
            } else if (defaultImage.isNotBlank()) {
                Uri.parse("$DEFAULT_API_PATH/$defaultImage")
            } else {
                Uri.parse("$DEFAULT_API_PATH/$DEFAULT_IMAGE")
            }
            Log.d("image uri", "$uri")
            return uri
        }

    }
}