package info.upump.jym.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import info.upump.database.entities.ExerciseDescriptionEntity
import info.upump.database.entities.ExerciseEntity
import info.upump.database.entities.ExerciseFullEntity
import info.upump.database.entities.SetsEntity
import info.upump.database.entities.WorkoutEntity
import info.upump.database.entities.WorkoutFullEntity
import info.upump.database.repo.CycleRepo
import info.upump.jymlight.R
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.utils.DBProvider
import info.upump.jymlight.utils.RestoreBackupable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okio.use
import java.io.File
import java.io.FileOutputStream

class JSONRestoreBackup : RestoreBackupable {
    override suspend fun backup(context: Context, listToJson: List<Cycle>): Intent =
            withContext(Dispatchers.IO) {
                val intentToSendToBd = Intent(Intent.ACTION_SEND)

                val gson = Gson()

                val toJson = gson.toJson(listToJson)

                val file = File(RoomDB.DB_PATH_RESTORE, RoomDB.BASE_NAME_RESTORE)
                FileOutputStream(file)
                        .use {
                            it.write(toJson.toByteArray())
                        }

                val uri = DBProvider().getDatabaseURIForJsonFile(context, file)

                intentToSendToBd.type = "text/plain"
                intentToSendToBd.putExtra(Intent.EXTRA_STREAM, uri)
                intentToSendToBd.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intentToSendToBd.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                intentToSendToBd.putExtra(
                        Intent.EXTRA_SUBJECT,
                        context.getString(R.string.email_subject_for_beackup)
                )

                return@withContext intentToSendToBd
            }


    override suspend fun restore(uri: Uri, context: Context) =
            withContext(Dispatchers.IO) {
                var fromJson = listOf<Cycle>()

                context.contentResolver.openInputStream(uri)?.use { inS ->
                    inS.bufferedReader().use {
                        val readText = it.readText()
                        val type = object : TypeToken<List<Cycle>>() {}.type
                        try {
                            fromJson = Gson().fromJson(readText, type)
                        }catch (e: JsonSyntaxException){
                            return@withContext
                        }
                    }
                }

                val listFullEntities = mutableListOf<Deferred<CycleFullEntity>>()
                //    val start = System.currentTimeMillis()

                for (cE in fromJson) {

                    val cycleFE = async {

                        val cycleE = CycleEntity().apply {
                            _id = cE.id
                            title = cE.title
                            comment = cE.comment
                            default_type = if (cE.isDefaultType) 1 else 0
                            //img = cE.image
                            img = null
                            start_date = cE.startStringFormatDate
                            finish_date = cE.finishStringFormatDate
                            default_img = cE.imageDefault
                        }

                        val listWFE = mutableListOf<WorkoutFullEntity>()

                        for (w in cE.workoutList) {
                            /* async {*/
                            val wE = WorkoutEntity().apply {
                                _id = 0
                                title = w.title
                                comment = w.comment
                                week_even = if (w.isWeekEven) 0 else 1
                                default_type = if (w.isDefaultType) 1 else 0
                                template = if (w.isTemplate) 0 else 1
                                day = w.day.name
                                start_date = w.startStringFormatDate
                                finish_date = w.finishStringFormatDate
                                parent_id = cE.id
                            }

                            val listEFE = mutableListOf<ExerciseFullEntity>()

                            for (e in w.exercises) {

                                val eE = ExerciseEntity().apply {
                                    _id = e.id
                                    comment = e.comment
                                    description_id = e.descriptionId
                                    type_exercise = e.typeMuscle.name
                                    default_type = if (e.isDefaultType) 1 else 0
                                    template = if (e.isTemplate) 1 else 0
                                    start_date = e.startStringFormatDate
                                    finish_date = e.finishStringFormatDate
                                    parent_id = e.parentId
                                }

                                val listSFE = mutableListOf<SetsEntity>()

                                for (s in e.setsList) {
                                    val sE = SetsEntity().apply {
                                        _id = s.id
                                        comment = s.comment
                                        weight = s.weight
                                        reps = s.reps
                                        start_date = s.startStringFormatDate
                                        finish_date = s.finishStringFormatDate
                                        parent_id = s.parentId
                                        past_set = s.weightPast
                                    }
                                    listSFE.add(sE)
                                }

                                val exFullEntity = ExerciseFullEntity(
                                        eE, ExerciseDescriptionEntity(), listSFE
                                )

                                listEFE.add(exFullEntity)

                            }
                            val wFullEntity = WorkoutFullEntity(
                                    wE, listEFE
                            )

                            listWFE.add(wFullEntity)
                        }

                        return@async CycleFullEntity(
                                cycleE, listWFE
                        )
                    }
                    //        Log.d("restore 2 ", cycleFE.await().cycleEntity.title)
                    listFullEntities.add(cycleFE)
                    //      Log.d("restore 2 ", listFullEntities.size.toString())
                }
                //}
                //     Log.d("restore time", "${System.currentTimeMillis() - start}")
                val cycleRepo = CycleRepo.get() as CycleRepo
                cycleRepo.saveFullEntitiesOnlyFromOtherDB(listFullEntities.awaitAll())
            }
}