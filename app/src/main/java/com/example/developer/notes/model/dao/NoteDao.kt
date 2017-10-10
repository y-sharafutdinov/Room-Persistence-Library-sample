package com.example.developer.notes.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.developer.notes.model.entity.Note


@Dao
interface NoteDao {
    @Insert
    fun insertAll(vararg notes: Note)

    @Delete
    fun delete(note: Note)

    @Query("Select * FROM note ORDER BY date DESC")
    fun getAllOrderByDateDESC(): LiveData<List<Note>>

    @Update
    fun update(note: Note)
}