package com.devinci.roomwordsample

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.devinci.roomwordsample.databinding.ActivityNewNoteBinding

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding

    private lateinit var editTitleView: EditText
    private lateinit var editTextView: EditText

    companion object {
        const val EXTRA_REPLY_ID = "com.devinci.roomwordsample.REPLY_ID"
        const val EXTRA_REPLY_TITLE = "com.devinci.roomwordsample.REPLY_TITLE"
        const val EXTRA_REPLY_TEXT = "com.devinci.roomwordsample.REPLY_TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        editTitleView = binding.editNoteTitle
        editTextView = binding.editNoteText

        // Check if we are editing an existing note
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            editTitleView.setText(intent.getStringExtra(EXTRA_REPLY_TITLE))
            editTextView.setText(intent.getStringExtra(EXTRA_REPLY_TEXT))
        }

        val button = findViewById<Button>(R.id.button_save_note)
        button.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(editTitleView.text) || TextUtils.isEmpty(editTextView.text)) {
                setResult(RESULT_CANCELED, replyIntent)
            } else {
                // Send note details back to MainActivity
                val title = editTitleView.text.toString()
                val text = editTextView.text.toString()

                replyIntent.putExtra(EXTRA_REPLY_TITLE, title)
                replyIntent.putExtra(EXTRA_REPLY_TEXT, text)

                // Pass ID back if we are editing a note
                if (bundle != null && bundle.containsKey(EXTRA_REPLY_ID)) {
                    val id = intent.getIntExtra(EXTRA_REPLY_ID, -1)
                    replyIntent.putExtra(EXTRA_REPLY_ID, id)
                }

                setResult(RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}
