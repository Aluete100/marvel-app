package com.alan.intermediatest.utils.glide

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.alan.intermediatest.R
import com.alan.intermediatest.utils.Constants
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders


class GlideHelper {
    companion object {
        fun loadImage(
            view: View,
            imagePath: String,
            imageSize: String,
            imageExtension: String,
            loadInto: ImageView
        ) {
            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            GlideApp.with(view)
                .load("$imagePath/$imageSize.$imageExtension")
                .placeholder(circularProgressDrawable)
                .error(R.drawable.memeloading)
                .into(loadInto)

        }
    }

}