package com.pwillmann.moviediscovery.epoxy.v2

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView

class GalleryLayoutManager(context: Context) : RecyclerView.LayoutManager() {

    companion object {
        private const val SCROLL_DISTANCE = 80 // dp
    }

    private var firstPosition = 0

    private val scrollDistance: Int

    init {
        val dm = context.resources.displayMetrics
        scrollDistance = (SCROLL_DISTANCE * dm.density + 0.5f).toInt()
    }

    override fun canScrollHorizontally(): Boolean = true

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

    override fun scrollToPosition(position: Int) {
        super.scrollToPosition(position)
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        val parentRight = width - paddingRight
        val oldLeftView = if (childCount > 0) getChildAt(0) else null
        var oldLeft = paddingLeft
        if (oldLeftView != null) {
            oldLeft = oldLeftView.left
        }

        detachAndScrapAttachedViews(recycler)

        val top = paddingLeft
        var bottom = height - paddingBottom
        var left = oldLeft
        var right: Int
        val count = state.itemCount
        var i = 0
        while (firstPosition + i < count && left < parentRight) {
            val v = recycler.getViewForPosition(firstPosition + i)
            addView(v, i)
            measureChildWithMargins(v, 0, 0)
            right = left + getDecoratedMeasuredWidth(v)
            bottom = top + getDecoratedMeasuredHeight(v)
            layoutDecorated(v, left, top, right, bottom)
            i++
            left = right
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (childCount == 0) {
            return 0
        }
        var scrolled = 0
        val top = paddingTop
        val bottom = height - paddingBottom
        if (dx < 0) {
            while (scrolled > dx) {
                val leftView = getChildAt(0)
                val hangingLeft = Math.max(-getDecoratedLeft(leftView!!), 0)
                val scrollBy = Math.min(scrolled - dx, hangingLeft)
                scrolled -= scrollBy
                offsetChildrenHorizontal(scrollBy)
                if (firstPosition > 0 && scrolled > dx) {
                    firstPosition--
                    val v = recycler.getViewForPosition(firstPosition)
                    addView(v, 0)
                    measureChildWithMargins(v, 0, 0)
                    val right = getDecoratedLeft(leftView)
                    val left = right - getDecoratedMeasuredWidth(v)
                    layoutDecorated(v, left, top, right, bottom)
                } else {
                    break
                }
            }
        } else if (dx > 0) {
            val parentWidth = width
            while (scrolled < dx) {
                val rightView = getChildAt(childCount - 1)
                val hangingRight = Math.max(getDecoratedRight(rightView!!) - parentWidth, 0)
                val scrollBy = -Math.min(dx - scrolled, hangingRight)
                scrolled -= scrollBy
                offsetChildrenHorizontal(scrollBy)
                if (scrolled < dx && state.itemCount > firstPosition + childCount) {
                    val v = recycler.getViewForPosition(firstPosition + childCount)
                    val left = getDecoratedRight(getChildAt(childCount - 1)!!)
                    addView(v)
                    measureChildWithMargins(v, 0, 0)
                    val right = left + getDecoratedMeasuredWidth(v)
                    layoutDecorated(v, left, top, right, bottom)
                } else {
                    break
                }
            }
        }
        recycleViewsOutOfBounds(recycler)
        return scrolled
    }

    override fun onFocusSearchFailed(focused: View, direction: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): View? {
        return super.onFocusSearchFailed(focused, direction, recycler, state)
    }

    private fun recycleViewsOutOfBounds(recycler: RecyclerView.Recycler) {
        val childCount = childCount
        val parentWidth = width
        val parentHeight = height
        var foundFirst = false
        var first = 0
        var last = 0
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            if (v!!.hasFocus() || getDecoratedBottom(v) >= 0 &&
                    getDecoratedTop(v) <= parentHeight &&
                    getDecoratedRight(v) >= 0 &&
                    getDecoratedLeft(v) <= parentWidth) {
                if (!foundFirst) {
                    first = i
                    foundFirst = true
                }
                last = i
            }
        }
        for (i in childCount - 1 downTo last + 1) {
            removeAndRecycleViewAt(i, recycler)
        }
        for (i in first - 1 downTo 0) {
            removeAndRecycleViewAt(i, recycler)
        }
        if (getChildCount() == 0) {
            firstPosition = 0
        } else {
            firstPosition += first
        }
    }
}