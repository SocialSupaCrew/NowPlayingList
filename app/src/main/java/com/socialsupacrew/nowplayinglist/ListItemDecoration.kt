package com.socialsupacrew.nowplayinglist

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.opengl.ETC1.getWidth



class ListItemDecoration(private var drawable: Drawable) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?,
                                state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent?.getChildAdapterPosition(view) == 0) {
            return
        }

        outRect?.top = drawable.intrinsicHeight
    }

    override fun onDraw(canvas: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(canvas, parent, state)

        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child?.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + drawable.intrinsicHeight

            drawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            drawable.draw(canvas)
        }
    }
}
