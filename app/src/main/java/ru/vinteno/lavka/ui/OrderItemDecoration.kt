package ru.vinteno.lavka.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OrderItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = spacing
        }

        if (position == (parent.adapter?.itemCount ?: 0) - 1) {
            outRect.bottom = spacing
        }
    }
}

