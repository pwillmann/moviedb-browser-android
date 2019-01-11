package com.pwillmann.moviediscovery.view.card

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.TextProp
import com.pwillmann.moviediscovery.view.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CardTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val containerView: ConstraintLayout
    private var itemType: ItemType = ItemType.NORMAL

    init {
        inflate(context, R.layout.views_card_title, this)
        titleView = findViewById(R.id.title)
        containerView = findViewById(R.id.container)
    }

    @AfterPropsSet
    fun afterProps() {
        when (itemType) {
            ItemType.TOP -> containerView.setBackgroundResource(R.drawable.views_background_card_top)
            ItemType.BOTTOM -> containerView.setBackgroundResource(R.drawable.views_background_card_bottom)
            ItemType.NORMAL -> containerView.setBackgroundResource(R.drawable.views_background_card)
        }
    }

    @OnViewRecycled
    fun clear() {
        containerView.setBackgroundResource(R.drawable.views_background_card)
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        titleView.text = title
    }

    @ModelProp
    fun setItemType(itemType: ItemType?) {
        if (itemType == null) return
        this.itemType = itemType
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
    }
}