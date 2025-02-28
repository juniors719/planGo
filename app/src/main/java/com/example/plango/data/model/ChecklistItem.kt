package com.example.plango.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName


data class ChecklistItem(
    @DocumentId
    val id: String = "", // ID único do item na coleção
    val title: String = "",   // Nome do item
    val tripId: String = "", // Referência à viagem à qual o item pertence
    @get:PropertyName("checked") @set:PropertyName("checked")
    var isChecked: Boolean = false // Status do item (marcado ou não)
)


