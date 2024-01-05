package info.upump.jymlight.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB
import info.upump.database.repo.CycleRepo
import info.upump.jymlight.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class DBRestoreBackup() {
    fun getSendIntent(context: Context): Intent {
        val intentToSendToBd = Intent(Intent.ACTION_SEND)
        val ur = DBProvider().getDatabaseURI(context)
        intentToSendToBd.type = "text/plain"
        intentToSendToBd.putExtra(Intent.EXTRA_STREAM, ur)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intentToSendToBd.putExtra(
            Intent.EXTRA_SUBJECT,
            context.getString(R.string.email_subject_for_beackup)
        )

        return intentToSendToBd
    }

    suspend fun restore(uri: Uri, context: Context, _stateLoading: MutableStateFlow<Boolean>) {
        coroutineScope {
            launch {
                val bytes = context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes()
                }

                val file = File(RoomDB.DB_PATH_RESTORE, RoomDB.BASE_NAME_RESTORE)

                withContext(Dispatchers.IO) {
                    FileOutputStream(file)
                }?.use {
                    it.write(bytes)
                    DatabaseApp.initilizeDb(
                        context,
                        RoomDB.BASE_NAME_RESTORE,
                        RoomDB.DB_PATH_RESTORE,
                        true
                    )

                    val repoForRestore = CycleRepo.get() as CycleRepo
                    repoForRestore.getAllFullestEntityPersonal().collect{list ->
                        DatabaseApp.initilizeDb(context, RoomDB.BASE_NAME, RoomDB.DB_PATH)

                        val repo = CycleRepo.get() as CycleRepo
                        repo.saveFullEntitiesOnlyFromOtherDB(list)
                        _stateLoading.update { false }
                    }
                }
            }
        }
    }
}