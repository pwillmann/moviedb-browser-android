package com.pwillmann.moviediscovery.epoxy.v2.gallery.layoutmanager

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Borrowed from https://github.com/yarolegovich/DiscreteScrollView
 */
class GalleryLayoutManagerDelegate(private val layoutManager: RecyclerView.LayoutManager) {

    val childCount: Int
        get() = layoutManager.childCount

    val itemCount: Int
        get() = layoutManager.itemCount

    val width: Int
        get() = layoutManager.width

    val height: Int
        get() = layoutManager.height

    fun attachView(view: View) {
        layoutManager.attachView(view)
    }

    fun detachView(view: View) {
        layoutManager.detachView(view)
    }

    fun detachAndScrapView(view: View, recycler: RecyclerView.Recycler) {
        layoutManager.detachAndScrapView(view, recycler)
    }

    fun detachAndScrapAttachedViews(recycler: RecyclerView.Recycler) {
        layoutManager.detachAndScrapAttachedViews(recycler)
    }

    fun recycleView(view: View, recycler: RecyclerView.Recycler) {
        recycler.recycleView(view)
    }

    fun removeAndRecycleAllViews(recycler: RecyclerView.Recycler) {
        layoutManager.removeAndRecycleAllViews(recycler)
    }

    fun getMeasuredChildForAdapterPosition(position: Int, recycler: RecyclerView.Recycler): View {
        val view = recycler.getViewForPosition(position)
        layoutManager.addView(view)
        layoutManager.measureChildWithMargins(view, 0, 0)
        return view
    }

    fun layoutDecoratedWithMargins(v: View, left: Int, top: Int, right: Int, bottom: Int) {
        layoutManager.layoutDecoratedWithMargins(v, left, top, right, bottom)
    }

    fun getChildAt(index: Int): View {
        return layoutManager.getChildAt(index)!!
    }

    fun getPosition(view: View): Int {
        return layoutManager.getPosition(view)
    }

    fun getMeasuredWidthWithMargin(child: View): Int {
        val lp = child.layoutParams as ViewGroup.MarginLayoutParams
        return layoutManager.getDecoratedMeasuredWidth(child) + lp.leftMargin + lp.rightMargin
    }

    fun getMeasuredHeightWithMargin(child: View): Int {
        val lp = child.layoutParams as ViewGroup.MarginLayoutParams
        return layoutManager.getDecoratedMeasuredHeight(child) + lp.topMargin + lp.bottomMargin
    }

    fun offsetChildrenHorizontal(amount: Int) {
        layoutManager.offsetChildrenHorizontal(amount)
    }

    fun requestLayout() {
        layoutManager.requestLayout()
    }

    fun startSmoothScroll(smoothScroller: RecyclerView.SmoothScroller) {
        layoutManager.startSmoothScroll(smoothScroller)
    }

    fun removeAllViews() {
        layoutManager.removeAllViews()
    }
}
