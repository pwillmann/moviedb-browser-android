package com.pwillmann.moviediscovery.epoxy.v2.gallery

import android.graphics.Point

class GalleryOrientationHelper {

    fun getViewEnd(recyclerWidth: Int, recyclerHeight: Int): Int {
        return recyclerWidth
    }

    fun getDistanceToChangeCurrent(childWidth: Int, childHeight: Int): Int {
        return childWidth
    }

    fun setCurrentViewCenter(recyclerCenter: Point, scrolled: Int, outPoint: Point) {
        val newX = recyclerCenter.x - scrolled
        outPoint.set(newX, recyclerCenter.y)
    }

    fun shiftViewCenter(direction: GalleryDirection, shiftAmount: Int, outCenter: Point) {
        val newX = outCenter.x + direction.applyTo(shiftAmount)
        outCenter.set(newX, outCenter.y)
    }

    fun isViewVisible(
        viewCenter: Point,
        halfWidth: Int,
        halfHeight: Int,
        endBound: Int,
        extraSpace: Int
    ): Boolean {
        val viewLeft = viewCenter.x - halfWidth
        val viewRight = viewCenter.x + halfWidth
        return viewLeft < endBound + extraSpace && viewRight > -extraSpace
    }

    fun hasNewBecomeVisible(lm: GalleryLayoutManager): Boolean {
        val firstChild = lm.firstChild
        val lastChild = lm.lastChild
        val leftBound = -lm.extraLayoutSpace
        val rightBound = lm.width + lm.extraLayoutSpace
        val isNewVisibleFromLeft = lm.getDecoratedLeft(firstChild) > leftBound && lm.getPosition(firstChild) > 0
        val isNewVisibleFromRight = lm.getDecoratedRight(lastChild) < rightBound && lm.getPosition(lastChild) < lm.getItemCount() - 1
        return isNewVisibleFromLeft || isNewVisibleFromRight
    }

    fun offsetChildren(amount: Int, helper: GalleryLayoutManagerDelegate) {
        helper.offsetChildrenHorizontal(amount)
    }

    fun getDistanceFromCenter(center: Point, viewCenterX: Int, viewCenterY: Int): Float {
        return (viewCenterX - center.x).toFloat()
    }

    fun getFlingVelocity(velocityX: Int, velocityY: Int): Int {
        return velocityX
    }

    fun canScrollHorizontally(): Boolean {
        return true
    }

    fun canScrollVertically(): Boolean {
        return false
    }

    fun getPendingDx(pendingScroll: Int): Int {
        return pendingScroll
    }

    fun getPendingDy(pendingScroll: Int): Int {
        return 0
    }
}