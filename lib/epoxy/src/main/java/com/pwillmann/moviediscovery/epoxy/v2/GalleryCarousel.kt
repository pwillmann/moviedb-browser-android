package com.pwillmann.moviediscovery.epoxy.v2

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelView
import com.pwillmann.moviediscovery.epoxy.v2.gallery.GalleryLayoutManager
import com.pwillmann.moviediscovery.epoxy.v2.gallery.GalleryTransformer

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class GalleryCarousel(context: Context) : Carousel(context) {

    init {
        setInitialPrefetchItemCount(3)
    }

    override fun getSnapHelperFactory(): SnapHelperFactory? {
        return null
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GalleryLayoutManager(context, null).apply {
            setItemTransformer(
                    GalleryTransformer.Builder()
                            .setMaxScale(1f)
                            .setMinScale(0.85f)
                            .setOverlapDistance(dpToPx(8))
                            .setElevation(dpToPx(10), dpToPx(4))
                            .build())
            setOffscreenItems(3)
            setShouldSlideOnFling(true)
            setSlideOnFlingThreshold(2300)
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val isFling = super.fling(velocityX, velocityY)
        if (isFling) {
            (layoutManager as GalleryLayoutManager).onFling(velocityX, velocityY)
        } else {
            (layoutManager as GalleryLayoutManager).returnToCurrentPosition()
        }
        return isFling
    }
}

/** Add models to a CarouselModel_ by transforming a list of items into EpoxyModels.
 *
 * @param items The items to transform to models
 * @param modelBuilder A function that take an item and returns a new EpoxyModel for that item.
 */
inline fun <T> GalleryCarouselModelBuilder.withModelsFrom(
    items: List<T>,
    modelBuilder: (T) -> EpoxyModel<*>
) {
    models(items.map { modelBuilder(it) })
}
