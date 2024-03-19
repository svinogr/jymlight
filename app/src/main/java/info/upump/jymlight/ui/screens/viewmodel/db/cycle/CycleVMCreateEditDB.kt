package info.upump.jymlight.ui.screens.viewmodel.db.cycle

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.db.CycleRepoDB
import info.upump.jymlight.models.entity.Cycle
import info.upump.jymlight.models.entity.Entity
import info.upump.jymlight.ui.screens.viewmodel.BaseVMWithStateLoad
import info.upump.jymlight.ui.screens.viewmodel.CycleEditVMInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import java.util.UUID

class CycleVMCreateEditDB() : BaseVMWithStateLoad(),
    CycleEditVMInterface {
    companion object {
        val vmOnlyForPreview by lazy {
            object : CycleEditVMInterface {
                private val _itemList = MutableStateFlow(
                    mutableListOf(
                        Cycle().apply { title = "Preview2" },
                        Cycle().apply { title = "Preview 3" })
                )

                private val _cycle = MutableStateFlow(Cycle().apply {
                    imageDefault = "drew"
                    title = "Preview"
                    comment = "это Preview"
                })

                override val item: StateFlow<Cycle> = _cycle.asStateFlow()

                private val _id = MutableStateFlow(_cycle.value.id)
                override val id: StateFlow<Long> = _id.asStateFlow()

                private val _title = MutableStateFlow(_cycle.value.title)
                override val title: StateFlow<String> = _title.asStateFlow()

                private val _comment = MutableStateFlow(_cycle.value.comment)
                override val comment: StateFlow<String> = _comment.asStateFlow()

                private val _startDate = MutableStateFlow(_cycle.value.startStringFormatDate)
                override val startDate: StateFlow<String> = _startDate

                private val _finishDate = MutableStateFlow(_cycle.value.finishStringFormatDate)
                override val finishDate: StateFlow<String> = _finishDate

                override val isTitleError: StateFlow<Boolean>
                    get() = TODO("Not yet implemented")

                private val _img = MutableStateFlow(_cycle.value.image)
                override val img: StateFlow<String> = _img.asStateFlow()

                private val _imgDefault = MutableStateFlow(_cycle.value.imageDefault)
                override val imgDefault: StateFlow<String> = _imgDefault

                override fun updateImageDefault(imgStr: String) {
                    _imgDefault.update { imgStr }
                }

                override fun updateEven(it: Boolean) {
                    TODO("Not yet implemented")
                }

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
                    TODO("Not yet implemented")
                }

                override fun updateTitle(it: String) {
                    TODO("Not yet implemented")
                }

                override fun updateImage(imgStr: String) {
                    TODO("Not yet implemented")
                }

                override fun updateStartDate(date: Date) {
                    TODO("Not yet implemented")
                }

                override fun updateFinishDate(date: Date) {
                    TODO("Not yet implemented")
                }

                override fun updateComment(comment: String) {
                    TODO("Not yet implemented")
                }

                override fun save(context: Context, callback: (id: Long) -> Unit) {
                    TODO("Not yet implemented")
                }

                override fun collectToSave(context: Context): Cycle {
                    TODO("Not yet implemented")
                }

                override fun isBlankFields(): Boolean {
                    return (title.value.trim().isEmpty())
                }

                override fun updateId(id: Long) {
                    _id.update { id }
                }

            }
        }
    }

    private val _cycle = MutableStateFlow(Cycle())
    override val item: StateFlow<Cycle> = _cycle.asStateFlow()

    private val _id = MutableStateFlow(0L)
    override val id: StateFlow<Long> = _id.asStateFlow()

    private val _title = MutableStateFlow("")
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _comment = MutableStateFlow("")
    override val comment: StateFlow<String> = _comment.asStateFlow()

    private val _startDate = MutableStateFlow("")
    override val startDate: StateFlow<String> = _startDate

    private val _finishDate = MutableStateFlow("")
    override val finishDate: StateFlow<String> = _finishDate

    private val _isTitleError = MutableStateFlow(false)
    override val isTitleError: StateFlow<Boolean> = _isTitleError.asStateFlow()

    private val _img = MutableStateFlow("")
    override val img: StateFlow<String> = _img.asStateFlow()

    private val _imgDefault = MutableStateFlow("")
    override val imgDefault: StateFlow<String> = _imgDefault

    private var tempImage = ""

    override fun getBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == 0L) {
                /*    _cycle.update {
                        Cycle()
                    }*/
                return@launch
            }

            CycleRepoDB.get().getFullEntityBy(id).map {
                Cycle.mapFullFromDbEntity(it)
            }.collect {
                updateId(it.id)
                updateTitle(it.title)
                updateComment(it.comment)
                updateStartDate(it.startDate)
                updateFinishDate(it.finishDate)
                updateImage(it.image)
                updateImageDefault(it.imageDefault)

                tempImage = it.image

                Log.d("ret", "$tempImage  / ${it.image}  / ${_img.value} / ${it.imageDefault}")
            }
        }
    }

    override fun updateId(id: Long) {
        _id.update { id }
    }

    override fun updateImageDefault(imgStr: String) {
        _imgDefault.update { imgStr }
    }

    override fun updateEven(it: Boolean) {
        TODO("Not yet implemented")
    }

    override fun save(context: Context, callback: (id: Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var cS = collectToSave(context)
            val cE = Cycle.mapToEntity(cS)
            val save = CycleRepoDB.get().save(cE)

            launch(Dispatchers.Main) {
                callback(save._id)
            }
        }
    }

    override fun updateTitle(title: String) {
        _title.update { title }
        _isTitleError.update { title.trim().isBlank() }
    }

    override fun updateImage(imgStr: String) {
        _img.update { imgStr }
    }

    override fun updateStartDate(date: Date) {
        _startDate.update { Entity.formatDateToString(date) }
    }

    override fun updateFinishDate(date: Date) {
        _finishDate.update { Entity.formatDateToString(date) }
    }

    override fun updateComment(comment: String) {
        _comment.update { comment }
    }

    override fun collectToSave(context: Context): Cycle {
        val c = Cycle().apply {
            id = _id.value
            comment = _comment.value
            title = _title.value

            setFinishDate(_finishDate.value)
            setStartDate(_startDate.value)
        }

        c.imageDefault = _imgDefault.value

        if (tempImage == img.value) {
            c.image = _img.value
            return c
        }

        if (img.value.isNotBlank()) {
            val file: File = writeToFile(img.value, context)
            c.image = file.toUri().toString()
            deleteTempImg(tempImage, context)
        }

        return c
    }

    private fun deleteTempImg(tempImage: String, context: Context) {
        if (tempImage.isBlank()) return
        try {
            val file = Uri.parse(tempImage).toFile()
            if (file.exists()) {
                file.delete()
            }
        } catch (e: IllegalArgumentException) {
            Log.d("Error", "нет такого файла для удаления")
        }
    }

    private fun writeToFile(strUri: String, context: Context): File {
        val bytes = context.contentResolver.openInputStream(Uri.parse(strUri))?.use {
            it.readBytes()
        }
        val uuid = UUID.randomUUID().toString()
        val file = File(context.filesDir, "${uuid}.jpg")

        FileOutputStream(file)?.use {
            it.write(bytes)
        }

        return file
    }

    override fun isBlankFields(): Boolean {
        val isBlank = title.value.trim().isBlank()
        _isTitleError.update { isBlank }

        return isBlank
    }

    override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
        TODO("Not yet implemented")
    }
}

