package com.devinci.roomwordsample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.devinci.roomwordsample.adapter.WordListAdapter
import com.devinci.roomwordsample.databinding.ActivityMainBinding
import com.devinci.roomwordsample.model.Word

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mWordViewModel: WordViewModel
    private val NEW_WORD_ACTIVITY_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize ViewModel
        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        val mAdapter = WordListAdapter(mWordViewModel)
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

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data:
        Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode ==
            RESULT_OK
        ) {
            val word = Word(
                0,
                data?.getStringExtra(NewWordActivity.EXTRA_REPLY)!!
            )
            mWordViewModel.insert(word)
        } else {
            Toast.makeText(
                applicationContext, R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
