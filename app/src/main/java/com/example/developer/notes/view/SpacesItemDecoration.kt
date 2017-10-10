package com.example.developer.notes.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class SpacesItemDecoration(private val columns: Int, private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        val spaceItem = space / 2
        outRect.bottom = spaceItem

        val childPosition = parent.getChildLayoutPosition(view)
        if (childPosition < columns) {
            outRect.top = space
        } else {
            outRect.top = spaceItem
        }
        if ((childPosition % columns) == 0) {
            outRect.left = space
        } else {
            outRect.left = spaceItem
        }
        if ((childPosition % columns) == (columns - 1)) {
            outRect.right = space
        } else {
            outRect.right = spaceItem
        }
    }
}