package com.spicysoft.currencyexchange

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.util.*

object BindingUtil {

    @JvmStatic
    @BindingAdapter("app:image")
    fun setImage(imageView: ImageView, name: String) {
        name?.toLowerCase(Locale.ENGLISH)?.also {
            val resource = imageView.context.resources
            val resourceId = resource.getIdentifier(it, "drawable", imageView.context.packageName)
            if(resourceId > 0) {
                imageView.setImageDrawable(resource.getDrawable(resourceId, null))
            }
        }
    }
}