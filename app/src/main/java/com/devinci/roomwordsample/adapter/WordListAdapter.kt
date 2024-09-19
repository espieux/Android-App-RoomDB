package com.devinci.roomwordsample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devinci.roomwordsample.R
import com.devinci.roomwordsample.WordViewModel
import com.devinci.roomwordsample.model.Word

class WordListAdapter(private val mWordViewModel: WordViewModel) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private var mWords: List<Word> = emptyList() // Copie en cache des mots
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item, parent, false
        )
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = mWords[position]
        holder.wordItemView.text = current.getWord()
        holder.deleteButton.setOnClickListener {
            mWordViewModel.deleteWord(current)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWords(words: List<Word>) {
        mWords = words
        notifyDataSetChanged()
    }

    // getItemCount() est appelé plusieurs fois, et lorsqu'il est appelé pour la première fois,
    // mWords n'a pas été mis à jour (cela signifie qu'initialement, il est nul, et nous ne pouvons pas retourner nul).
    override fun getItemCount(): Int {
        return mWords.size
    }

    class WordViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView
        val deleteButton: ImageButton

        init {
            wordItemView = itemView.findViewById(R.id.textView)
            deleteButton = itemView.findViewById(R.id.deleteButton)
        }
    }
}