package com.pwillmann.moviediscovery.epoxy.v2

import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.card.MaterialCardView
import com.pwillmann.moviediscovery.core.GlideApp
import com.pwillmann.moviediscovery.epoxy.KotlinEpoxyHolder
import com.pwillmann.moviediscovery.epoxy.R
import com.pwillmann.moviediscovery.epoxy.R2

@EpoxyModelClass(layout = R2.layout.epoxy_v2_image_card)
abstract class ImageCardItem : EpoxyModelWithHolder<ImageCardItem.Holder>() {

    @EpoxyAttribute
    lateinit var url: CharSequence

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        GlideApp.with(holder.card.context)
                .load(url)
                .placeholder(R.color.gunmetal)
                .into(holder.coverImageView)
        holder.card.setOnClickListener(clickListener)
    }

    class Holder : KotlinEpoxyHolder() {
        val coverImageView: ImageView by bind(R.id.image)
        val card: MaterialCardView by bind(R.id.card)
    }
}
