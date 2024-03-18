package info.upump.jymlight.ui.screens.viewmodel.db.sets

import info.upump.jymlight.models.entity.Sets
import kotlinx.coroutines.flow.StateFlow

interface SetsVMInterface {
    val item: StateFlow<Sets>
    val id: StateFlow<Long>
    val parentId: StateFlow<Long>
    val reps: StateFlow<Int>
    val weight: StateFlow<Double>
    val weightPast: StateFlow<Double>
    val quantity: StateFlow<Int>

    fun updateReps(reps: Int)
    fun updateWeight(weight: Double)
    fun updatePastWeight(weight: Double)
    fun updateQuantity(quantity: Int)
    fun updateParentId(parentId: Long)
    fun getByParent(parentId: Long)
    fun getBy(id: Long)
    fun save()
}