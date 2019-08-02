package com.chethan.mercari.api.binding

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
    @BindingAdapter("productPrize")
    fun setUserRatings(view: TextView, productPrize: String) {
        view.text = "$" + productPrize
    }


    @JvmStatic
    @BindingAdapter("productSoldOutStatus")
    fun setSoldOutImage(view: ImageView, status: String) {
        if (status.equals("sold_out"))
            view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter(value = ["productImageUrl"])
    fun bindProductImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).into(imageView)


    }
}
