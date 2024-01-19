package info.upump.jymlight.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

class DBProvider : FileProvider() {
 /*   fun getDatabaseURIForBD(c: Context): Uri {
        val data = Environment.getDataDirectory()
        val dbName = "jym.db"
        val currentDBPath = "/data/info.upump.jymlight/databases/$dbName"
        val exportFile = File(data, currentDBPath)

        return getFileUri(c, exportFile)
    }
*/
    fun getDatabaseURIForJsonFile(c: Context, file: File): Uri {
        return getFileUri(c, file)
    }

    private fun getFileUri(c: Context, f: File): Uri {
        return getUriForFile(c!!, "info.upump.jymlight", f!!)
    }
}