package info.upump.jymlight.ui.screens.viewmodel

import android.content.Context
import info.upump.jymlight.models.entity.Cycle
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface CycleEditVMInterface {
    val item: StateFlow<Cycle>
    val id: StateFlow<Long>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<String>
    val finishDate: StateFlow<String>
    val isTitleError: StateFlow<Boolean>
    val img: StateFlow<String>
    val imgDefault: StateFlow<String>
    fun updateImageDefault(imgStr: String)
    fun updateEven(it: Boolean)
    fun getBy(id: Long)
    fun save(context: Context, callback: (id: Long) -> Unit)
    fun saveWith(parentId: Long, callback: (id: Long) -> Unit)
    fun updateTitle(it: String)
    fun updateImage(imgStr: String)
    fun updateStartDate(date: Date)
    fun updateFinishDate(date: Date)
    fun updateComment(comment: String)
    fun collectToSave(context: Context): Cycle
    fun isBlankFields(): Boolean
    fun updateId(id: Long)
}