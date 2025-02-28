package com.example.plango.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class CheckListItemDatabase: RoomDatabase() {
    abstract fun checklistItemDao(): ChecklistItemDao

    companion object {
        private var INSTANCE: CheckListItemDatabase? = null

        fun getDatabase(context: Context): CheckListItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CheckListItemDatabase::class.java,
                    "checklist_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}