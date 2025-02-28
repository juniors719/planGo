package com.example.plango.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.plango.data.model.LocalChecklistItem
import com.example.plango.data.repository.ChecklistItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocalChecklistItemViewModel(
    private val repository: ChecklistItemRepository
) : ViewModel() {
    val checklistItems: Flow<List<LocalChecklistItem>> = repository.checklistItems

    fun addItem(
        title: String,
        isChecked: Boolean,
        tripId: String
    ){
        viewModelScope.launch {
            repository.addItem(title, isChecked, tripId)
        }
    }

    fun deleteItem(item: LocalChecklistItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
}


class LocalChecklistItemViewModelFactory(
    private val repository: ChecklistItemRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalChecklistItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocalChecklistItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}