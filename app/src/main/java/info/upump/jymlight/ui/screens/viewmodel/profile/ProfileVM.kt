package info.upump.jymlight.ui.screens.viewmodel.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.utils.DBRestoreBackup
import kotlinx.coroutines.flow.update

class ProfileVM : BaseVMWithStateLoad() {

    suspend fun load(uri: Uri, context: Context) {
        _stateLoading.update {true}
        Log.d("load", "load")
        val backupDB = DBRestoreBackup()
        backupDB.restore(uri, context, _stateLoading)

    }

    fun send(context: Context) {
        Log.d("v", "send")
        val backupDab = DBRestoreBackup()
        val intent = backupDab.getSendIntent(context)
        context.startActivity(intent)
    }

}