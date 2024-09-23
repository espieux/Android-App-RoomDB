package com.devinci.roomwordsample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devinci.roomwordsample.model.Note

// Define the database version and include the entities (tables).
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {

    // Declare the DAO (Data Access Object) for the note entity.
    abstract fun noteDao(): NoteDao

    companion object {
        // The singleton instance of the database.
        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        // Method to get the database instance.
        fun getDatabase(context: Context): NoteRoomDatabase {
            // If the INSTANCE is null, create the database. Otherwise, return the existing instance.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration() // Optional: This will reset the database if schema changes occur.
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
