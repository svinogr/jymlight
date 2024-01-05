package info.upump.jymlight.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
//TODO

class DBProvider : FileProvider() {
    fun getDatabaseURI(c: Context): Uri {
        val data = Environment.getDataDirectory()
        val dbName = "jym.db"
       // Log.d("path", "${data.path}")
        val currentDBPath = "/data/info.upump.jym/databases/$dbName"
      //  val currentDBPath = "databases/$dbName"
        val exportFile = File(data, currentDBPath)
    //    val exportFile = File(currentDBPath)
        Log.d("path", "${exportFile.path}")


        return getFileUri(c, exportFile)
    }

   private fun getFileUri(c: Context, f: File): Uri {
        return getUriForFile(c!!, "info.upump.jym", f!!)
    }
}