package info.upump.jymlight.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

class DBProvider : FileProvider() {
    fun getDatabaseURIForJsonFile(c: Context, file: File): Uri {
        return getFileUri(c, file)
    }

    private fun getFileUri(c: Context, f: File): Uri {
        return getUriForFile(c!!, "info.upump.jymlight", f!!)
    }
}