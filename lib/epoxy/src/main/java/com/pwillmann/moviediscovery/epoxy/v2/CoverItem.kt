package com.pwillmann.moviediscovery.epoxy.v2

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.pwillmann.moviediscovery.core.dagger.glide.GlideApp
import com.pwillmann.moviediscovery.epoxy.KotlinEpoxyHolder
import com.pwillmann.moviediscovery.epoxy.R
import com.pwillmann.moviediscovery.epoxy.R2

@EpoxyModelClass(layout = R2.layout.epoxy_v2_cover)
abstract class CoverItem : EpoxyModelWithHolder<CoverItem.Holder>() {

    @EpoxyAttribute
    lateinit var imageUrl: CharSequence

    @EpoxyAttribute
    lateinit var title: CharSequence

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        GlideApp.with(holder.container.context)
                .load(imageUrl)
                .placeholder(R.color.gunmetal)
                .into(holder.coverImageView)
        holder.container.setOnClickListener(clickListener)
        holder.titleTextView.text = title
    }

    class Holder : KotlinEpoxyHolder() {
        val coverImageView: ImageView by bind(R.id.cover_image)
        val container: ConstraintLayout by bind(R.id.cover_container)
        val titleTextView: TextView by bind(R.id.cover_title)
    }
}
