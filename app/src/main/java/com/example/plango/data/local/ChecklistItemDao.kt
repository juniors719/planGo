package com.example.plango.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plango.data.model.ChecklistItem
import com.example.plango.data.model.LocalChecklistItem

@Dao
interface ChecklistItemDao {

    @Query("SELECT * FROM checklist_items WHERE tripId = :tripId")
    suspend fun getChecklistItemsByTripId(tripId: String): List<LocalChecklistItem>

    @Query("SELECT * FROM checklist_items")
    fun getAllChecklistItems(): kotlinx.coroutines.flow.Flow<List<LocalChecklistItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklistItem(item: LocalChecklistItem)

    @Delete
    suspend fun deleteChecklistItem(itemId: String)

}