package com.devinci.roomwordsample

import android.app.Application
import androidx.lifecycle.LiveData
import com.devinci.roomwordsample.data.db.NoteDao
import com.devinci.roomwordsample.data.db.NoteRoomDatabase
import com.devinci.roomwordsample.model.Note
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private val noteDao: NoteDao
    val allNotes: LiveData<List<Note>>

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        noteDao = db.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        Executors.newSingleThreadExecutor().execute {
            noteDao.insert(note)
        }
    }

    fun update(note: Note) {
        Executors.newSingleThreadExecutor().execute {
            noteDao.update(note)
        }
    }

    fun delete(note: Note) {
        Executors.newSingleThreadExecutor().execute {
            noteDao.delete(note)
        }
    }

    fun deleteAll() {
        Executors.newSingleThreadExecutor().execute {
            noteDao.deleteAll()
        }
    }
}
