package com.pwillmann.moviediscovery.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TitleRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView

    init {
        inflate(context, R.layout.views_title, this)
        titleView = findViewById(R.id.title)
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        titleView.text = title
    }
}