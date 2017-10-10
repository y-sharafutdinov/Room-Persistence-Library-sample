package com.example.developer.notes.extension

import android.os.AsyncTask
import com.example.developer.notes.model.NotesDatabase
import com.example.developer.notes.model.entity.Note

private abstract class DbAsync(protected val db: NotesDatabase) : AsyncTask<Note, Void, Void>()

private class AddNoteDbAsync(db: NotesDatabase) : DbAsync(db) {
    override fun doInBackground(vararg params: Note): Void? {
        params.forEach {
            db.noteDao().insertAll(it)
        }
        return null
    }
}

private class UpdateNoteDbAsync(db: NotesDatabase) : DbAsync(db) {
    override fun doInBackground(vararg params: Note): Void? {
        params.forEach {
            db.noteDao().update(it)
        }
        return null
    }
}

private class DeleteAsyncTask(db: NotesDatabase) : DbAsync(db) {
    override fun doInBackground(vararg params: Note): Void? {
        params.forEach {
            db.noteDao().delete(it)
        }
        return null
    }
}

fun NotesDatabase.addNoteAsync(note: Note): AsyncTask<Note, Void, Void> = AddNoteDbAsync(this).execute(note)

fun NotesDatabase.updateNoteAsync(note: Note): AsyncTask<Note, Void, Void> = UpdateNoteDbAsync(this).execute(note)

fun NotesDatabase.deleteNoteAsync(note: Note): AsyncTask<Note, Void, Void> = DeleteAsyncTask(this).execute(note)