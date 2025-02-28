package com.example.plango.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_items")
data class LocalChecklistItem(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val isChecked: Boolean = false,
    val tripId: String = ""
)