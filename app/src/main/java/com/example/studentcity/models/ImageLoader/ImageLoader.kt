package com.example.studentcity.models.ImageLoader

import android.widget.ImageView

class ImageLoader(private val imageView: ImageView, private val url: String) {
    private val task: ImageLoaderTask?

    init {
        task = ImageLoaderTask(imageView)
    }

    fun load() {
        task!!.execute(url)
    }

    fun cancel() {
        if (task != null || !task!!.isCancelled()) {
            task!!.cancel(true)
        }
    }
}