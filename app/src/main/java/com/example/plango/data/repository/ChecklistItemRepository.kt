package com.example.plango.data.repository

import com.example.plango.data.local.ChecklistItemDao
import com.example.plango.data.model.ChecklistItem
import com.example.plango.data.model.LocalChecklistItem
import kotlinx.coroutines.flow.Flow

class ChecklistItemRepository(
    private val checklistItemDao: ChecklistItemDao
) {
    val checklistItems: Flow<List<LocalChecklistItem>> = checklistItemDao.getAllChecklistItems()

    suspend fun addItem(
        title: String,
        isChecked: Boolean,
        tripId: String
    ) {
        val newItem = LocalChecklistItem(
            title = title,
            isChecked = isChecked,
            tripId = tripId
        )
        checklistItemDao.insertChecklistItem(newItem)
    }

    suspend fun deleteItem(item: LocalChecklistItem) {
        checklistItemDao.deleteChecklistItem(item.id)
    }

}