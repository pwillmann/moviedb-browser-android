package com.pwillmann.moviediscovery.view.card

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.pwillmann.moviediscovery.view.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CardSpace @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val containerView: ConstraintLayout
    private var itemType: ItemType = ItemType.NORMAL

    init {
        inflate(context, R.layout.views_card_space, this)
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

    @ModelProp
    fun setItemType(itemType: ItemType?) {
        if (itemType == null) return
        this.itemType = itemType
    }
}