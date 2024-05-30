package com.rizqanmr.githubusersearch.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setCircleImageUrl(url: String?, placeholder: Int?= null) {
    if (url.isNullOrBlank() && (placeholder == null || placeholder == 0)) return

    val glideRequest = Glide.with(context).load(url).circleCrop()
    placeholder?.let {
        glideRequest.placeholder(it)
    }
    glideRequest.into(this)
}