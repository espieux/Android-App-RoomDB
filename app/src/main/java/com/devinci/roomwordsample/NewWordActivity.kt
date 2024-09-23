package com.devinci.roomwordsample

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

public class NewWordActivity : AppCompatActivity() {

    private lateinit var mEditWordView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        mEditWordView = findViewById(R.id.edit_word)

        if (intent.hasExtra("word_id")) {
            // This is an edit operation
            val wordText = intent.getStringExtra("word_text")
            mEditWordView.setText(wordText)
        }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(mEditWordView.text)) {
                setResult(RESULT_CANCELED, replyIntent)
            } else {
                val wordText = mEditWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, wordText)
                if (intent.hasExtra("word_id")) {
                    val wordId = intent.getIntExtra("word_id", -1)
                    replyIntent.putExtra("word_id", wordId)
                }
                setResult(RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object{
        val EXTRA_REPLY = "com.example.android.roomwordssample.REPLY"
    }
}