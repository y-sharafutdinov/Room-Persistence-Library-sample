package com.example.developer.notes.view

import android.view.ViewGroup
import android.widget.TextView
import com.example.developer.notes.R
import com.example.developer.notes.adapter.BaseAdapter
import java.lang.ref.WeakReference

class NoteItemViewHolder(
        parent: ViewGroup,
        listener: WeakReference<BaseAdapter.ItemClickListener>?,
        longListener: WeakReference<BaseAdapter.ItemLongClickListener>?
) : BaseAdapter.ViewHolder(parent, R.layout.single_card_view, listener, longListener) {
    var txtTitle: TextView = itemView.findViewById(R.id.textView_title_card)
    var txtText: TextView = itemView.findViewById(R.id.textView_text_card)
    var txtDate: TextView = itemView.findViewById(R.id.textView_date_card)
}