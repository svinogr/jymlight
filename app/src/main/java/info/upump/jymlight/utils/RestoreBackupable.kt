package info.upump.jymlight.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import info.upump.jymlight.models.entity.Cycle
import kotlinx.coroutines.flow.MutableStateFlow

interface RestoreBackupable {
    companion object {
        const val FILE_EXTENSION = "db"
    }

    suspend fun backup(context: Context, list: List<Cycle>): Intent
    suspend fun restore(uri: Uri, context: Context)
}