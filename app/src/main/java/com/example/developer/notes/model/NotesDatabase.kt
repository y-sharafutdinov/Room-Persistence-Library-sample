package com.example.developer.notes.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.developer.notes.model.dao.NoteDao
import com.example.developer.notes.model.entity.Note


@Database(entities = arrayOf(Note::class), version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NotesDatabase? = null
        private const val DB_NAME = "note-database"

        fun getInstance(context: Context): NotesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<NotesDatabase>(context.applicationContext, NotesDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE!!
        }
    }
}