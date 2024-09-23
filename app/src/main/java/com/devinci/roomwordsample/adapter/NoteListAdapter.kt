package com.devinci.roomwordsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devinci.roomwordsample.R
import com.devinci.roomwordsample.model.Note

class NoteListAdapter(private val listener: OnNoteClickListener) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    private var notes: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteTitle.text = current.title
        holder.noteText.text = current.text

        holder.editButton.setOnClickListener {
            listener.onEditClick(current)
        }

        holder.deleteButton.setOnClickListener {
            listener.onDeleteClick(current)
        }
    }

    override fun getItemCount() = notes.size

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val noteText: TextView = itemView.findViewById(R.id.textViewText)
        val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    interface OnNoteClickListener {
        fun onEditClick(note: Note)
        fun onDeleteClick(note: Note)
    }
}
