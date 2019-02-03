package com.pwillmann.moviediscovery.epoxy.v2.gallery

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.annotation.Px
import kotlin.math.sign

class GalleryTransformer : GalleryScrollItemTransformer {

    private var pivotX: GalleryPivot? = null
    private var pivotY: GalleryPivot? = null
    private var minScale: Float = 0.toFloat()
    private var maxMinDiffScale: Float = 0.toFloat()
    private var minAlpha: Float = 0.toFloat()
    private var maxMinDiffAlpha: Float = 0.toFloat()
    private var overlapDistance: Float = 0.toFloat()
    private var interpolator: Interpolator = AccelerateDecelerateInterpolator()

    init {
        pivotX = GalleryPivot.X.CENTER.create()
        pivotY = GalleryPivot.Y.CENTER.create()
        minScale = 0.8f
        minAlpha = 0.6f
        maxMinDiffScale = 1f - minScale
        maxMinDiffAlpha = 1f - minAlpha
    }

    override fun onTransformItem(item: View, position: Float) {
        pivotX!!.setOn(item)
        pivotY!!.setOn(item)
        val closenessToCenter = 1f - Math.abs(position)
        val scale = minScale + maxMinDiffScale * closenessToCenter
        val alpha = minAlpha + maxMinDiffAlpha * closenessToCenter
        item.scaleX = scale
        item.scaleY = scale
        item.alpha = alpha
        item.translationX = calcTranslation(item, position)
        if (-0.3f < position && position < 0.3f) item.bringToFront()
    }

    private fun calcTranslation(item: View, position: Float): Float {
        val maxTranslation = calcMaxTranslation(item)
        val sign = sign(position)
        val interpolatedPosition = interpolator.getInterpolation(Math.abs(position))
        val currentTranslation = maxTranslation * interpolatedPosition

        return sign * currentTranslation * -1f
    }

    private fun calcMaxTranslation(item: View): Float {
        val gapBetweenItems = (item.layoutParams as ViewGroup.MarginLayoutParams).leftMargin + (item.layoutParams as ViewGroup.MarginLayoutParams).rightMargin
        val widthDiffBetweenMinAndMaxScale = item.width * maxMinDiffScale
        return gapBetweenItems + widthDiffBetweenMinAndMaxScale + overlapDistance
    }

    class Builder {

        private val transformer: GalleryTransformer = GalleryTransformer()
        private var maxScale: Float = 0.toFloat()

        init {
            maxScale = 1f
        }

        fun setMinScale(scale: Float): Builder {
            transformer.minScale = scale
            return this
        }

        fun setMaxScale(scale: Float): Builder {
            maxScale = scale
            return this
        }

        fun setPivotX(pivotX: GalleryPivot.X): Builder {
            return setPivotX(pivotX.create())
        }

        fun setPivotX(pivot: GalleryPivot): Builder {
            assertAxis(pivot, GalleryPivot.AXIS_X)
            transformer.pivotX = pivot
            return this
        }

        fun setPivotY(pivotY: GalleryPivot.Y): Builder {
            return setPivotY(pivotY.create())
        }

        fun setPivotY(pivot: GalleryPivot): Builder {
            assertAxis(pivot, GalleryPivot.AXIS_Y)
            transformer.pivotY = pivot
            return this
        }

        fun setOverlapDistance(@Px distance: Int): Builder {
            transformer.overlapDistance = distance.toFloat()
            return this
        }

        fun build(): GalleryTransformer {
            transformer.maxMinDiffScale = maxScale - transformer.minScale
            return transformer
        }

        private fun assertAxis(pivot: GalleryPivot, @GalleryPivot.Axis axis: Int) {
            if (pivot.axis != axis) {
                throw IllegalArgumentException("You passed a Pivot for wrong axis.")
            }
        }
    }
}
