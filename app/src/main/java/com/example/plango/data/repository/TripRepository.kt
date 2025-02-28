package com.example.plango.data.repository

import com.example.plango.data.model.ChecklistItem
import com.example.plango.data.model.Event
import com.example.plango.data.model.Trip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class TripRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun createTrip(trip: Trip): String {
        val user = auth.currentUser ?: throw Exception("Usuário não autenticado")
        val docRef = firestore.collection("trips").document()
        val tripToSave = trip.copy(id = docRef.id, ownerId = user.uid)
        docRef.set(tripToSave).await() // Usa await para esperar a operação assíncrona
        return docRef.id
    }

    fun getUserTrips(): Flow<List<Trip>> {
        val user = auth.currentUser ?: return emptyFlow() // Evita consultas desnecessárias
        return firestore.collection("trips")
            .whereEqualTo("ownerId", user.uid)
            .snapshots()
            .map { snapshot -> snapshot.toObjects(Trip::class.java) }
    }

    suspend fun updateTrip(tripId: String, updates: Map<String, Any>) {
        firestore.collection("trips").document(tripId)
            .update(updates)
            .await()
    }

    suspend fun deleteTrip(tripId: String) {
        firestore.collection("trips").document(tripId)
            .delete()
            .await()
    }

    suspend fun addChecklistItem(checklistItem: ChecklistItem) {
        firestore.collection("checklists")
            .add(checklistItem)
            .await()
    }

    suspend fun addPackingChecklistItem(checklistItem: ChecklistItem) {
        firestore.collection("packingChecklists")
            .add(checklistItem)
            .await()
    }

    fun getChecklistItems(tripId: String): Flow<List<ChecklistItem>> {
        return firestore.collection("checklists")
            .whereEqualTo("tripId", tripId)
            .snapshots()
            .map { snapshot -> snapshot.toObjects(ChecklistItem::class.java) }
    }

    fun getPackingChecklistItems(tripId: String): Flow<List<ChecklistItem>> {
        return firestore.collection("packingChecklists")
            .whereEqualTo("tripId", tripId)
            .snapshots()
            .map { snapshot -> snapshot.toObjects(ChecklistItem::class.java) }
    }

    fun updateChecklistItem(checklistItem: ChecklistItem) {
        firestore.collection("checklists")
            .document(checklistItem.id)
            .set(checklistItem)
    }

    fun updatePackingChecklistItem(checklistItem: ChecklistItem) {
        firestore.collection("packingChecklists")
            .document(checklistItem.id)
            .set(checklistItem)
    }

    fun deleteChecklistItem(checklistItem: ChecklistItem) {
        firestore.collection("checklists")
            .document(checklistItem.id)
            .delete()
    }

    fun deletePackingChecklistItem(checklistItem: ChecklistItem) {
        firestore.collection("packingChecklists")
            .document(checklistItem.id)
            .delete()
    }

    suspend fun addEvent(tripId: String, event: Event) {
        firestore.collection("trips").document(tripId)
            .update("events", FieldValue.arrayUnion(event))
            .await()
    }
}