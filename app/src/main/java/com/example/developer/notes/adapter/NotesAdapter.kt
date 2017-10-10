package com.example.developer.notes.adapter

import android.view.ViewGroup
import com.example.developer.notes.extension.MMM_D_YYYY_HH_MM
import com.example.developer.notes.extension.toString
import com.example.developer.notes.model.entity.Note
import com.example.developer.notes.view.NoteItemViewHolder
import java.lang.ref.WeakReference
import java.util.*


class NotesAdapter(listener: ItemClickListener?,
                   longListener: ItemLongClickListener?,
                   items: List<Note>?) : BaseAdapter<NoteItemViewHolder, Note>(listener, longListener, items) {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int,
                                    listener: WeakReference<ItemClickListener>?,
                                    longListener: WeakReference<ItemLongClickListener>?): NoteItemViewHolder = NoteItemViewHolder(parent, listener, longListener)

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int, item: Note) {
        holder.txtTitle.text = item.title
        holder.txtText.text = item.text
        holder.txtDate.text = Date(item.date).toString(MMM_D_YYYY_HH_MM)

        holder.itemView.setBackgroundColor(item.color)
    }

    fun updateList(all: List<Note>?) {
        items.clear()
        all?.let {
            if (it.isNotEmpty()) {
                items.addAll(it)
            }
        }
        notifyDataSetChanged()
    }
}