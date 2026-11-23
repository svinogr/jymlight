package info.upump.jymlight.utils

import android.content.Context
import android.net.Uri
import info.upump.jymlight.models.entity.Cycle

interface ReadToBackupRestorable {
    suspend fun backupToUri(context: Context, list: List<Cycle>): Uri
    suspend fun restoreFromUri(uri: Uri, context: Context): List<Cycle>
}