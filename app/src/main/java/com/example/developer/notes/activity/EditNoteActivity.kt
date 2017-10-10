package com.example.developer.notes.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.example.developer.notes.R
import com.example.developer.notes.extension.*
import com.example.developer.notes.model.NotesDatabase
import com.example.developer.notes.model.entity.Note
import petrov.kristiyan.colorpicker.ColorPicker
import java.util.*

class EditNoteActivity : AppCompatActivity() {
    companion object {
        private const val NOTE = "NOTE"

        fun start(context: Context, note: Note? = null) {
            val intent = Intent(context, EditNoteActivity::class.java)
            if (note != null) {
                intent.putExtra(NOTE, note)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var editTextTitle: EditText
    private lateinit var editTextText: EditText

    private lateinit var textViewDate: TextView

    private lateinit var layout: ConstraintLayout

    private val title get() = editTextTitle.text?.toString()
    private val text get() = editTextText.text?.toString()
    private var color = Color.WHITE

    private val isEditing get() = note != null
    private val isChanged: Boolean
        get() {
            val note = note
            if (note != null) {
                return note.title != title || note.text != text || note.color != color
            }

            return title.isNullOrEmpty() || text.isNullOrEmpty()
        }

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        note = intent.extras?.getParcelable(NOTE)
        color = ContextCompat.getColor(this, R.color.yellow)

        configView()
    }

    private fun configView() {
        layout = findViewById(R.id.layout_note)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle(if (isEditing) R.string.edit_note else R.string.new_note)
        }

        editTextTitle = findViewById(R.id.editText_title_note)
        editTextText = findViewById(R.id.editText_text)

        textViewDate = findViewById(R.id.textView_date)

        note?.apply {
            editTextTitle.setText(title, TextView.BufferType.EDITABLE)
            editTextText.setText(text, TextView.BufferType.EDITABLE)
            textViewDate.text = Date(date).toString(MMM_D_YYYY_HH_MM)

            updateColor(color)
        }

        updateColor()
    }

    private fun updateColor(newColor: Int = color) {
        color = newColor
        window.statusBarColor = color.darkerColor()
        layout.setBackgroundColor(color)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save_button -> saveNote()
            R.id.menu_color_picker -> pickColor()
            android.R.id.home -> goBack()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun pickColor() = ColorPicker(this)
            .setRoundColorButton(true)
            .disableDefaultButtons(true)
            .setOnFastChooseColorListener(colorListener)
            .show()

    private fun saveNote() {
        val title = title
        val text = text

        if (title != null && title.isNotBlank() || text != null && text.isNotBlank()) {
            val uid = note?.uid
            val note = Note(uid ?: 0, title, text, color, System.currentTimeMillis())
            val db = NotesDatabase.getInstance(this)

            if (uid != null) db.updateNoteAsync(note) else db.addNoteAsync(note)

            finish()
        } else {
            AlertDialog.Builder(this)
                    .setTitle(R.string.note_is_empty)
                    .setMessage(R.string.nothing_to_save)
                    .setPositiveButton(R.string.ok, null)
                    .show()
        }
    }

    private fun goBack() {
        if (isChanged) {
            AlertDialog.Builder(this)
                    .setMessage(getString(R.string.discard_all_changes))
                    .setPositiveButton(R.string.erase, { _, _ -> finish() })
                    .setNegativeButton(R.string.cancel, null)
                    .show()
        } else {
            finish()
        }
    }

    private val colorListener = object : ColorPicker.OnFastChooseColorListener {
        override fun onCancel() = Unit

        override fun setOnFastChooseColorListener(position: Int, color: Int) = updateColor(color)
    }
}