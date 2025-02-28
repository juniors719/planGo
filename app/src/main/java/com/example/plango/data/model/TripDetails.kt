package com.example.plango.data.model

data class TripDetails(
    val id: String,                             // Identificador único da viagem
    val name: String,                           // Nome da viagem
    val description: String?,                   // Descrição opcional
    val startDate: String,                      // Data de início da viagem (formato ISO 8601 ou customizado)
    val endDate: String?,                       // Data de término da viagem
    val destination: String,                    // Destino da viagem
    val checklist: List<ChecklistItem>,         // Lista de itens do checklist
    val packingChecklist: List<ChecklistItem>,  // Checklist para itens da mala
    val events: List<Event>,                    // Lista de eventos
    val imageUrl: String?                       // URL da imagem associada à viagem
)
