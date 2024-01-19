package info.upump.jymlight.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import info.upump.jym.utils.JSONRestoreBackup
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.utils.RestoreBackupable


class DataBaseAction(private val restoreInterface: RestoreBackupable = JSONRestoreBackup()) {
    suspend fun load(uri: Uri, context: Context) {
       restoreInterface.restore(uri, context)
    }

    suspend fun backup(context: Context, list: List<Cycle>): Intent {
        return restoreInterface.backup(context, list)
    }
}