package com.kgandroid.mysanctuary.utilities

import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kgandroid.mysanctuary.R

fun getProgressDrawable(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply{
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

/*fun ImageView.loadImage(uri : String? , progressDrawable: CircularProgressDrawable){
    if (!uri.isNullOrEmpty()) {
        val options = RequestOptions
            .placeholderOf(progressDrawable)
            .error(R.mipmap.ic_launcher_round)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)
    }
}

@BindingAdapter("imageFromUrl")
fun loadImage(view : ImageView , url: String){
    if (!url.isNullOrEmpty()) {
        view.loadImage(url, getProgressDrawable(view.context))
    }
}*/

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}


@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}