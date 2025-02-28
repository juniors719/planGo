package com.example.plango.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Trip(
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val startDate: Timestamp? = null,
    val endDate: Timestamp? = null,
    val destination: String? = null,
    val imageUrl: String? = null,
    val ownerId: String? = null
)