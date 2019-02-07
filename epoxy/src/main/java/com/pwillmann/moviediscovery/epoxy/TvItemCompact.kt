package com.pwillmann.moviediscovery.epoxy

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.pwillmann.moviediscovery.core.GlideApp

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TvItemCompact @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val ratingView: TextView
    private val voteCountView: TextView
    private val posterImageView: ImageView

    init {
        inflate(context, R.layout.epoxy_tv_item_compact, this)
        titleView = findViewById(R.id.title)
        ratingView = findViewById(R.id.rating)
        voteCountView = findViewById(R.id.voteCount)
        posterImageView = findViewById(R.id.poster)
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        titleView.text = title
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
    }
}
