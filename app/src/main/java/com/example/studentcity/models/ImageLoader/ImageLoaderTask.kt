package com.example.studentcity.models.ImageLoader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView

import java.io.InputStream

internal class ImageLoaderTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg urls: String): Bitmap? {

        val url = if (urls.size > 0) urls[0] else null
        var bitmap: Bitmap? = null

        if (url == null) return bitmap

        try {
            val inputStream = java.net.URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("download image error", e.message)
            e.printStackTrace()
        }

        return bitmap
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        if (bitmap == null) return

        imageView.setImageBitmap(bitmap)
    }
}
