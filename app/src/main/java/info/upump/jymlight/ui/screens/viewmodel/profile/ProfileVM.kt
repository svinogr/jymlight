package info.upump.jymlight.ui.screens.viewmodel.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import info.upump.jym.utils.JSONRestoreBackup
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.utils.RestoreBackupable
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.flow.update


class ProfileVM(private val restoreInterface: RestoreBackupable = JSONRestoreBackup()) :
    BaseVMWithStateLoad() {

    suspend fun load(uri: Uri, context: Context) {
        _stateLoading.update { true }
        Log.d("load", "load")
        // val backupDB: RestoreBackupable = DBRestoreBackup()
        //  val backupDB: RestoreBackupable = JSONRestoreBackup()
        restoreInterface.restore(uri, context, _stateLoading)
    }

    suspend fun send(context: Context, list: List<Cycle>) {
        Log.d("v", "send")
        //  val backupDab: RestoreBackupable = DBRestoreBackup()
       // val backupDab: RestoreBackupable = JSONRestoreBackup()
        val intent = restoreInterface.getSendIntent( context, list)
        context.startActivity(intent)
    }
}