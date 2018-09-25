package com.pwillmann.moviediscovery.views.card

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.pwillmann.moviediscovery.views.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CardCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val carouselView: Carousel
    private val containerView: ConstraintLayout
    private var itemType: ItemType = ItemType.NORMAL

    private var carouselModels: List<EpoxyModel<*>> = listOf()
    private var carouselId: String = ""
    private var carouselNumViewsToShowOnScreen: Float = 1f

    init {
        inflate(context, R.layout.views_card_carousel, this)
        carouselView = findViewById(R.id.carousel)
        containerView = findViewById(R.id.container)
    }

    @AfterPropsSet
    fun afterProps() {
        when (itemType) {
            ItemType.TOP -> containerView.setBackgroundResource(R.drawable.views_background_card_top)
            ItemType.BOTTOM -> containerView.setBackgroundResource(R.drawable.views_background_card_bottom)
            ItemType.NORMAL -> containerView.setBackgroundResource(R.drawable.views_background_card)
        }
        carouselView.setNumViewsToShowOnScreen(carouselNumViewsToShowOnScreen)
        carouselView.setModels(carouselModels)
    }
    @OnViewRecycled
    fun clear() {
        containerView.setBackgroundResource(R.drawable.views_background_card)
    }

    @ModelProp
    fun setItemType(itemType: ItemType?) {
        if (itemType == null) return
        this.itemType = itemType
    }

    @ModelProp
    fun setModels(models: List<EpoxyModel<*>>) {
        carouselModels = models
    }

    @ModelProp
    fun setCarouselId(id: String) {
        carouselId = id
    }

    @ModelProp
    fun setNumViewsToShowOnScreen(numViewsToShowOnScreen: Float) {
        carouselNumViewsToShowOnScreen = numViewsToShowOnScreen
    }
}