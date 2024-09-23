package com.devinci.roomwordsample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.devinci.roomwordsample.adapter.WordListAdapter
import com.devinci.roomwordsample.databinding.ActivityMainBinding
import com.devinci.roomwordsample.model.Word

class MainActivity : AppCompatActivity(), WordListAdapter.OnWordClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mWordViewModel: WordViewModel
    private val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    private val EDIT_WORD_ACTIVITY_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize ViewModel
        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        val mAdapter = WordListAdapter(mWordViewModel, this)

        binding.contentMain.recyclerview.adapter = mAdapter
        binding.contentMain.recyclerview.setHasFixedSize(true)



        mWordViewModel.getAllWords().observe(this) { words: List<Word> ->
            // Update the cached copy of the words in the adapter.
            mAdapter.setWords(words)
        }
        // Set up the FloatingActionButton to launch NewWordActivity
        binding.contentMain.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onEditClick(word: Word) {
        val intent = Intent(this@MainActivity, NewWordActivity::class.java)
        intent.putExtra("word_id", word.id)
        intent.putExtra("word_text", word.getWord())
        startActivityForResult(intent, EDIT_WORD_ACTIVITY_REQUEST_CODE)
    }

    override fun onDeleteClick(word: Word) {
        mWordViewModel.deleteWord(word)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE) {
                val word = Word(
                    0,
                    data?.getStringExtra(NewWordActivity.EXTRA_REPLY)!!
                )
                mWordViewModel.insert(word)
            } else if (requestCode == EDIT_WORD_ACTIVITY_REQUEST_CODE) {
                val wordId = data?.getIntExtra("word_id", -1)
                val wordText = data?.getStringExtra(NewWordActivity.EXTRA_REPLY)
                if (wordId != -1 && wordText != null) {
                    val updatedWord = wordId?.let { Word(it, wordText) }
                    mWordViewModel.updateWord(updatedWord)
                } else {
                    Toast.makeText(
                        applicationContext, R.string.empty_not_saved,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                applicationContext, R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
