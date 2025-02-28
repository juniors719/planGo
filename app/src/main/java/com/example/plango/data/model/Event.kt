package com.example.plango.data.model

import com.google.firebase.firestore.DocumentId

data class Event(
    @DocumentId
    val id: String,      // Identificador único do evento
    val title: String,   // Nome do evento
    val description: String?, // Descrição do evento
    val location: String, // Local do evento
    val imageUrl: String? // URL da imagem associada ao evento (opcional)
)