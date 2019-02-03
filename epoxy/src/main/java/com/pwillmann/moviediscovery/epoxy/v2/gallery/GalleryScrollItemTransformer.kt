package com.pwillmann.moviediscovery.epoxy.v2.gallery

import android.view.View

interface GalleryScrollItemTransformer {
    /**
     *
     * @param item View: The recyclerview item that is currently being scrolled
     * @param position Float: Value from [-1, 1) indicating the offset from the page at position.
     */
    fun onTransformItem(item: View, position: Float)
}