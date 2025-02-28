package com.example.plango.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plango.data.model.ChecklistItem
import com.example.plango.data.model.Trip
import com.example.plango.data.repository.TripRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TripViewModel(repository: TripRepository) : ViewModel() {
    private val tripRepository = TripRepository()

    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips = _trips as StateFlow<List<Trip>>

    private val _checklists = MutableStateFlow<List<ChecklistItem>>(emptyList())
    val checklists: StateFlow<List<ChecklistItem>> = _checklists.asStateFlow()

    private val _packingChecklists = MutableStateFlow<List<ChecklistItem>>(emptyList())
    val packingChecklists: StateFlow<List<ChecklistItem>> = _packingChecklists.asStateFlow()

    var isLoading by mutableStateOf(false)

    init {
        tripRepository.getUserTrips().onEach { trips ->
            _trips.value = trips
        }.launchIn(viewModelScope)
    }

    fun createTrip(
        name: String,
        description: String,
        startDate: Timestamp?,
        endDate: Timestamp?,
        destination: String,
        imageUrl: String?,
        onSuccess: (Boolean) -> Unit,
    ) {
        isLoading = true
        viewModelScope.launch {
            try {
                val trip = Trip(
                    name = name,
                    description = description,
                    startDate = startDate,
                    endDate = endDate,
                    destination = destination,
                    imageUrl = imageUrl ?: "",
                )
                tripRepository.createTrip(trip)
                onSuccess(true)
            } catch (e: Exception) {
                onSuccess(false)
                Log.e("TripViewModel", "Erro ao criar viagem", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun createCheckListItem(
        tripId: String,
        title: String,
        isChecked: Boolean,
        onSuccess: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val checklistItem = ChecklistItem(
                    tripId = tripId,
                    title = title,
                    isChecked = isChecked
                )
                tripRepository.addChecklistItem(checklistItem)
                _checklists.value += checklistItem
                onSuccess(true)
                loadChecklist(tripId)
            } catch (e: Exception) {
                onSuccess(false)
                Log.e("TripViewModel", "Erro ao criar item de checklist", e)
            }
        }
    }

    fun createPackingCheckListItem(
        tripId: String,
        title: String,
        isChecked: Boolean,
        onSuccess: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val checklistItem = ChecklistItem(
                    tripId = tripId,
                    title = title,
                    isChecked = isChecked
                )
                tripRepository.addPackingChecklistItem(checklistItem)
                onSuccess(true)
                loadPackingChecklist(tripId)
            } catch (e: Exception) {
                onSuccess(false)
                Log.e("TripViewModel", "Erro ao criar item de checklist", e)
            }
        }
    }

    fun loadChecklist(tripId: String) {
        Log.d("TripViewModel", "Loading checklist for tripId: $tripId")
        _checklists.value = emptyList()
        tripRepository.getChecklistItems(tripId).onEach { items ->
            _checklists.value = items
        }.launchIn(viewModelScope)
    }

    fun loadPackingChecklist(tripId: String) {
        _packingChecklists.value = emptyList()
        tripRepository.getPackingChecklistItems(tripId).onEach { items ->
            _packingChecklists.value = items
        }.launchIn(viewModelScope)
    }

    fun updateCheckListItem(
        item: ChecklistItem
    ) {
        viewModelScope.launch {
            tripRepository.updateChecklistItem(item)
        }
    }

    fun updatePackingCheckListItem(
        item: ChecklistItem
    ) {
        viewModelScope.launch {
            tripRepository.updatePackingChecklistItem(item)
        }
    }

    fun deleteCheckListItem(
        item: ChecklistItem
    ) {
        viewModelScope.launch {
            tripRepository.deleteChecklistItem(item)
            _checklists.value = _checklists.value.filter { it != item }
            loadChecklist(item.tripId)
        }
    }

    fun deletePackingCheckListItem(
        item: ChecklistItem
    ) {
        viewModelScope.launch {
            tripRepository.deletePackingChecklistItem(item)
            _packingChecklists.value = _packingChecklists.value.filter { it != item }
            loadPackingChecklist(item.tripId)
        }
    }

}