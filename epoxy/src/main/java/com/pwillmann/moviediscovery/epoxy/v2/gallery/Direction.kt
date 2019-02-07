package com.pwillmann.moviediscovery.epoxy.v2.gallery

/**
 * Borrowed from https://github.com/yarolegovich/DiscreteScrollView
 */
enum class Direction {

    START {
        override fun applyTo(delta: Int): Int {
            return delta * -1
        }
    },
    END {
        override fun applyTo(delta: Int): Int {
            return delta
        }
    };

    abstract fun applyTo(delta: Int): Int

    companion object {

        fun fromDelta(delta: Int): Direction {
            return if (delta > 0) END else START
        }
    }
}
