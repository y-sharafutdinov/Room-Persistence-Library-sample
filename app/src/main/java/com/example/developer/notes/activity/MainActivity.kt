package com.example.developer.notes.activity

import android.os.Bundle
import android.view.MenuItem
import com.example.developer.notes.R
import com.example.developer.notes.fragment.NotesListFragment


class MainActivity : BaseActivity() {
    override val layoutResId get() = R.layout.activity_main
    override val menuRes get() = R.menu.main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteListFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (noteListFragment == null) {
            replaceFragment(R.id.fragmentContainer, NotesListFragment())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_add_note -> EditNoteActivity.start(this).let { true }
        else -> super.onOptionsItemSelected(item)
    }
}