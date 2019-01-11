package com.pwillmann.moviediscovery.view

import android.content.Context
import com.google.android.material.button.MaterialButton
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.pwillmann.moviediscovery.core.GlideApp

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TvItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val summaryView: TextView
    private val ratingView: TextView
    private val voteCountView: TextView
    private val moreButton: MaterialButton
    private val posterImageView: ImageView

    init {
        inflate(context, R.layout.views_tv_item, this)
        titleView = findViewById(R.id.title)
        summaryView = findViewById(R.id.summary)
        ratingView = findViewById(R.id.rating)
        voteCountView = findViewById(R.id.voteCount)
        moreButton = findViewById(R.id.more)
        posterImageView = findViewById(R.id.poster)
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        titleView.text = title
    }

    @TextProp
    fun setSummary(summary: CharSequence?) {
        summaryView.text = summary
    }

    @TextProp
    fun setRating(rating: CharSequence?) {
        ratingView.text = rating
    }

    @TextProp
    fun setVoteCount(voteCount: CharSequence?) {
        voteCountView.text = resources.getString(R.string.views_tv_item_vote_count, voteCount)
    }

    @TextProp
    fun setPosterImageUrl(url: CharSequence?) {
        GlideApp.with(this)
                .load(url)
                .placeholder(R.color.steel_light)
                .into(posterImageView)
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
        moreButton.setOnClickListener(clickListener)
    }
}