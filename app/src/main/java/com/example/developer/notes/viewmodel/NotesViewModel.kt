package com.example.developer.notes.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.developer.notes.model.NotesDatabase
import com.example.developer.notes.model.entity.Note


class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val database: NotesDatabase = NotesDatabase.getInstance(application)

    val liveData: LiveData<List<Note>>

    init {
        liveData = database.noteDao().getAllOrderByDateDESC()
    }
}