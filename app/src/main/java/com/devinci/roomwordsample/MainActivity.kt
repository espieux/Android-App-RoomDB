package com.devinci.roomwordsample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devinci.roomwordsample.adapter.NoteListAdapter
import com.devinci.roomwordsample.databinding.ActivityMainBinding
import com.devinci.roomwordsample.model.Note

class MainActivity : AppCompatActivity(), NoteListAdapter.OnNoteClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel

    private val NEW_NOTE_REQUEST_CODE = 1
    private val EDIT_NOTE_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Set up RecyclerView
        val noteAdapter = NoteListAdapter(this)
        binding.contentMain.recyclerview.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val layoutManager = GridLayoutManager(this, 2)  // Ensure 2 items per row
        recyclerView.layoutManager = layoutManager

        // Observe LiveData from ViewModel
        noteViewModel.allNotes.observe(this) { notes ->
            notes?.let {
                noteAdapter.setNotes(it)
            }
        }

        // Floating Action Button to add a new note
        binding.contentMain.fab.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java) // Change to NewNoteActivity
            startActivityForResult(intent, NEW_NOTE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handle inserting a new note
            val title = data?.getStringExtra(NewNoteActivity.EXTRA_REPLY_TITLE)
            val text = data?.getStringExtra(NewNoteActivity.EXTRA_REPLY_TEXT)
            if (!title.isNullOrEmpty() && !text.isNullOrEmpty()) {
                val note = Note(title = title, text = text)
                noteViewModel.insert(note)
            }
        } else if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handle updating an existing note
            val id = data?.getIntExtra(NewNoteActivity.EXTRA_REPLY_ID, -1)
            val title = data?.getStringExtra(NewNoteActivity.EXTRA_REPLY_TITLE)
            val text = data?.getStringExtra(NewNoteActivity.EXTRA_REPLY_TEXT)

            if (id != -1 && !title.isNullOrEmpty() && !text.isNullOrEmpty()) {
                val updatedNote = Note(id = id!!, title = title, text = text)
                noteViewModel.update(updatedNote)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    // OnNoteClickListener interface implementation
    override fun onEditClick(note: Note) {
        val intent = Intent(this, NewNoteActivity::class.java).apply {
            putExtra(NewNoteActivity.EXTRA_REPLY_ID, note.id)
            putExtra(NewNoteActivity.EXTRA_REPLY_TITLE, note.title)
            putExtra(NewNoteActivity.EXTRA_REPLY_TEXT, note.text)
        }
        startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE)
    }

    override fun onDeleteClick(note: Note) {
        noteViewModel.delete(note)
    }
}
