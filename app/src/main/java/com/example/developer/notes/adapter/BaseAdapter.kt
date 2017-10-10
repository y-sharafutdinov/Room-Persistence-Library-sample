package com.example.developer.notes.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import android.view.ViewGroup

import com.example.developer.notes.extension.weak
import java.lang.ref.WeakReference


abstract class BaseAdapter<VH : BaseAdapter.ViewHolder, I>(
        listener: ItemClickListener? = null,
        longListener: ItemLongClickListener? = null,
        items: List<I>? = null
) : RecyclerView.Adapter<VH>() {
    private val listener: WeakReference<ItemClickListener>? = listener?.weak()
    private val longListener: WeakReference<ItemLongClickListener>? = longListener.weak()

    val items: MutableList<I> = items?.toMutableList() ?: mutableListOf()

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = onCreateViewHolder(parent, viewType, listener, longListener)

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int, listener: WeakReference<ItemClickListener>?, longListener: WeakReference<ItemLongClickListener>?): VH

    override fun onBindViewHolder(holder: VH, position: Int) = onBindViewHolder(holder, position, items[position])

    abstract fun onBindViewHolder(holder: VH, position: Int, item: I)

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    interface ItemLongClickListener {
        fun onItemLongClick(position: Int): Boolean
    }

    open class ViewHolder(container: ViewGroup,
                          @LayoutRes layoutResId: Int,
                          private val listener: WeakReference<ItemClickListener>?,
                          private val longListener: WeakReference<ItemLongClickListener>?
    ) : RecyclerView.ViewHolder(LayoutInflater.from(container.context).inflate(layoutResId, container, false)) {
        init {
            itemView.setOnClickListener { listener?.get()?.onItemClick(adapterPosition) }
            itemView.setOnLongClickListener { longListener?.get()?.onItemLongClick(adapterPosition) == true }
        }
    }
}
