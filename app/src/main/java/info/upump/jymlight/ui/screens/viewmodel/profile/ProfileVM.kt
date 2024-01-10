package info.upump.jymlight.ui.screens.viewmodel.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import info.upump.jym.utils.JSONRestoreBackup
import info.upump.jymlight.utils.RestoreBackupable
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.flow.update


class ProfileVM : BaseVMWithStateLoad() {
    suspend fun load(uri: Uri, context: Context, restoreInterface: RestoreBackupable) {
        _stateLoading.update { true }
        Log.d("load", "load")
        // val backupDB: RestoreBackupable = DBRestoreBackup()
      //  val backupDB: RestoreBackupable = JSONRestoreBackup()
        restoreInterface.restore(uri, context, _stateLoading)
    }

    suspend fun send(context: Context, restoreInterface: RestoreBackupable) {
        Log.d("v", "send")
        //  val backupDab: RestoreBackupable = DBRestoreBackup()
        val backupDab: RestoreBackupable = JSONRestoreBackup()
        val intent = restoreInterface.getSendIntent(context)
        context.startActivity(intent)
    }
}