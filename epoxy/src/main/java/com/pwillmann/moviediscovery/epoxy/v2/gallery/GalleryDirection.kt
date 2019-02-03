package com.pwillmann.moviediscovery.epoxy.v2.gallery

enum class GalleryDirection {

    START {
        override fun applyTo(delta: Int): Int {
            return delta * -1
        }

        override fun sameAs(direction: Int): Boolean {
            return direction < 0
        }
    },
    END {
        override fun applyTo(delta: Int): Int {
            return delta
        }

        override fun sameAs(direction: Int): Boolean {
            return direction > 0
        }
    };

    abstract fun applyTo(delta: Int): Int

    abstract fun sameAs(direction: Int): Boolean

    companion object {

        fun fromDelta(delta: Int): GalleryDirection {
            return if (delta > 0) END else START
        }
    }
}