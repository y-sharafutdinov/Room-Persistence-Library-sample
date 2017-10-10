package com.example.developer.notes.fragment

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.developer.notes.activity.MainActivity
import com.example.developer.notes.viewmodel.NotesViewModel
import com.example.developer.notes.R
import com.example.developer.notes.view.SpacesItemDecoration
import com.example.developer.notes.activity.EditNoteActivity
import com.example.developer.notes.adapter.BaseAdapter
import com.example.developer.notes.adapter.NotesAdapter
import com.example.developer.notes.extension.deleteNoteAsync
import com.example.developer.notes.model.NotesDatabase
import com.example.developer.notes.model.entity.Note


class NotesListFragment : Fragment(), BaseAdapter.ItemClickListener, BaseAdapter.ItemLongClickListener {

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: NotesAdapter

    private lateinit var listViewModel: NotesViewModel

    private val mainActivity: MainActivity? get() = activity as? MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = NotesAdapter(this, this, emptyList())

        listViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        listViewModel.liveData.observe(this, Observer<List<Note>> { list ->
            adapter.updateList(list)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.note_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        val columns = context.resources.getInteger(R.integer.columns_count)
        recyclerView.layoutManager = GridLayoutManager(context, columns)
        recyclerView.addItemDecoration(SpacesItemDecoration(columns, context.resources.getDimensionPixelSize(R.dimen.space_between_card_view)))
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        mainActivity?.supportActionBar?.setTitle(R.string.all_notes)
    }

    override fun onItemClick(position: Int) {
        EditNoteActivity.start(context, adapter.items[position])
    }

    override fun onItemLongClick(position: Int): Boolean {
        AlertDialog.Builder(activity)
                .setTitle(R.string.title_delete_note)
                .setMessage(R.string.question_delete_note)
                .setPositiveButton(R.string.ok, { _, _ ->
                    NotesDatabase.getInstance(context).deleteNoteAsync(adapter.items[position])
                })
                .setNegativeButton(R.string.cancel, null)
                .show()
        return true
    }

}