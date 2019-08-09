package com.chethan.babylon.api.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }


    @JvmStatic
    @BindingAdapter(value = ["userAvatarImageUrl"])
    fun bindProductImage(imageView: ImageView, postId: String?) {
        Glide.with(imageView.context).load("https://api.adorable.io/avatars/" + postId).into(imageView)


    }
}
