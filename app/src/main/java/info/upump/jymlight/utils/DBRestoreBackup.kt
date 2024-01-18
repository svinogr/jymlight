package info.upump.jymlight.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB
import info.upump.database.repo.CycleRepo
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Cycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class DBRestoreBackup() : RestoreBackupable {
    override suspend fun getSendIntent(context: Context, list: List<Cycle>): Intent {
        val intentToSendToBd = Intent(Intent.ACTION_SEND)
        val ur = DBProvider().getDatabaseURIForBD(context)

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

    override suspend fun restore(uri: Uri, context: Context, _stateLoading: MutableStateFlow<Boolean>) {
        coroutineScope() {
            launch(Dispatchers.IO) {
                val file = File(RoomDB.DB_PATH_RESTORE, RoomDB.BASE_NAME_RESTORE)
                context.contentResolver.openInputStream(uri)?.use {
                    val bytes = it.readBytes()

                    FileOutputStream(file)
                            .use {
                                it.write(bytes)
                                DatabaseApp.initilizeDb(
                                        context,
                                        RoomDB.BASE_NAME_RESTORE,
                                        RoomDB.DB_PATH_RESTORE,
                                        true
                                )

                                val repoForRestore = CycleRepo.get() as CycleRepo
                                val list = repoForRestore.getAllFullestEntityPersonal()
                                for (l in list) {
                                    Log.d("list", "-" + l.cycleEntity.title + "  " + l.cycleEntity._id + "  " + l.listWorkoutEntity.size)
                                    for (w in l.listWorkoutEntity) {
                                        Log.d("list", "---" + w.workoutEntity.title + " " + w.workoutEntity._id + "  " + w.workoutEntity.parent_id)

                                    }
                                }
                            }
                }


                /*
                                DatabaseApp.initilizeDb(context, RoomDB.BASE_NAME, RoomDB.DB_PATH)
                                file.delete()
                                val file2 = File(RoomDB.BASE_NAME_RESTORE, RoomDB.DB_PATH)
                                file2.delete()

                                val repo = CycleRepo.get() as CycleRepo
                                repo.saveFullEntitiesOnlyFromOtherDB(list)
                                _stateLoading.update { false }*/

            }
        }
    }

}