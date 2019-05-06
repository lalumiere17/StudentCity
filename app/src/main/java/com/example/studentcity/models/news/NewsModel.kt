package com.example.studentcity.models.news

import android.os.AsyncTask
import android.text.TextUtils

import com.vk.sdk.api.VKApi
import com.vk.sdk.api.model.VKApiPhoto
import com.vk.sdk.api.model.VKApiPost
import com.vk.sdk.api.model.VKAttachments
import com.vk.sdk.api.model.VKList

import java.util.ArrayList

class NewsModel(val text: String, val photo: String) {

    interface ConvertCallback {
        fun onConvert(news: ArrayList<NewsModel>)
        fun onFail()
    }

    private class Task internal constructor(
        private val posts: VKList<VKApiPost>?,
        private val callback: ConvertCallback
    ) : AsyncTask<Void, Void, ArrayList<NewsModel>>() {

        override fun doInBackground(vararg voids: Void): ArrayList<NewsModel>? {
            val news = ArrayList<NewsModel>()
            for (post in posts!!) {
                if (isCancelled) return null

                if (TextUtils.isEmpty(post.text) || TextUtils.isEmpty(getPhoto(post)))
                    continue

                val textPost = post.text
                val photoPost = getPhoto(post)

                news.add(NewsModel(textPost, photoPost))
            }

            return news
        }

        override fun onPostExecute(news: ArrayList<NewsModel>?) {
            super.onPostExecute(news)
            if (news == null) return

            if (posts != null && posts!!.size() !== 0 && news.size == 0)
                callback.onFail()
            else
                callback.onConvert(news)

        }

        private fun getPhoto(post: VKApiPost): String? {
            if (post.attachments.getCount() > 0) {
                for (attachment in post.attachments) {

                    if (attachment.getType().equals("photo")) {
                        val photo = attachment as VKApiPhoto

                        if (photo != null) {
                            return if (photo!!.photo_1280 != null)
                                photo!!.photo_1280
                            else if (photo!!.photo_604 != null)
                                photo!!.photo_604
                            else
                                null
                        }
                    }
                }
            }
            return null
        }
    }

    companion object {

        fun convert(posts: VKList<VKApiPost>, callback: ConvertCallback) {
            val task = Task(posts, callback)
            task.execute()
        }
    }
}
