package com.yusufarisoy.n11case.ui.detail

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("url")
fun ShapeableImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(context).load(it).into(this)
    }
}
