package com.pwillmann.moviediscovery.epoxy.card

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
import com.pwillmann.moviediscovery.epoxy.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CardText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val containerView: ConstraintLayout
    private var itemType: ItemType = ItemType.NORMAL

    init {
        inflate(context, R.layout.epoxy_card_text, this)
        textView = findViewById(R.id.text)
        containerView = findViewById(R.id.container)
    }

    @AfterPropsSet
    fun afterProps() {
        when (itemType) {
            ItemType.TOP -> containerView.setBackgroundResource(R.drawable.epoxy_background_card_top)
            ItemType.BOTTOM -> containerView.setBackgroundResource(R.drawable.epoxy_background_card_bottom)
            ItemType.NORMAL -> containerView.setBackgroundResource(R.drawable.epoxy_background_card)
        }
    }

    @OnViewRecycled
    fun clear() {
        containerView.setBackgroundResource(R.drawable.epoxy_background_card)
    }

    @TextProp
    fun setText(text: CharSequence?) {
        textView.text = text
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
