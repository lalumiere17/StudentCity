package com.example.studentcity.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.studentcity.R
import com.example.studentcity.models.ImageLoader.ImageLoader
import com.example.studentcity.models.news.NewsModel
import java.util.*
import java.util.regex.Pattern

class ListNewsAdapter(
    private val context: Context,
    private var news: ArrayList<NewsModel>?,
    private val callback: ItemClickListener?
) : RecyclerView.Adapter<ListNewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(
            R.layout.item_of_news_list,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (news == null) return

        if (callback != null)
            holder.itemView.setOnClickListener { callback.onClick(news!![position]) }

        val post = news!![position]
        if (post.text != null) {
            holder.setTextContentView(post.text)
        } else {
            holder.hideText()
        }

        val photo = post.photo
        if (photo != null) {
            holder.setImageContentView(photo)
        } else
            holder.hideImage()
    }

    override fun getItemCount(): Int {
        return news!!.size
    }

    fun update(news: ArrayList<NewsModel>) {
        this.news = news
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textContentView: TextView = itemView.findViewById(R.id.textContentView)
        private val imageContentView: ImageView = itemView.findViewById(R.id.imageContentView)

        private val CHARS_OF_LINKS_PATTERN = arrayOf("[", "]", "|")

        fun setTextContentView(textContent: String) {
            var newContent = deleteLinkOfClub(textContent)
            newContent = deleteLinkOfClub(deleteLinkOfProfile(newContent))
            textContentView.text = newContent
            textContentView.movementMethod = LinkMovementMethod.getInstance()
        }

        fun setImageContentView(imageContent: String) {
            val imageLoader = ImageLoader(imageContentView, imageContent)
            imageLoader.load()
        }

        fun deleteText(text: String?, sourcePattern: String, vararg chars: String): String? {
            try {
                var result = ""
                val texts = text!!.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (i in texts.indices) {
                    do {
                        val pattern = Pattern.compile(sourcePattern)
                        val matcher = pattern.matcher(texts[i])

                        if (matcher.matches() && matcher.groupCount() >= 1) {
                            val group = matcher.group(1)
                            texts[i] = texts[i].replace(group, "")

                            for (inputChar in chars) {
                                texts[i] = texts[i].replace(inputChar, "")
                            }
                        } else
                            break
                    } while (true)
                }

                for (str in texts) {
                    result += String.format("%s\n", str)

                }

                return result
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

        fun deleteLinkOfClub(textContent: String?): String? {
            val PATTERN_FOR_LINK_OF_CLUB = ".+(club[0-9]+).+"
            return deleteText(textContent, PATTERN_FOR_LINK_OF_CLUB, *CHARS_OF_LINKS_PATTERN)
        }

        fun deleteLinkOfProfile(textContent: String?): String? {
            val PATTERN_FOR_LINK_OF_PROFILE = ".+(id[0-9]+).+"
            return deleteText(textContent, PATTERN_FOR_LINK_OF_PROFILE, *CHARS_OF_LINKS_PATTERN)
        }

        fun hideImage() {
            imageContentView.visibility = View.GONE
        }

        fun hideText() {
            textContentView.visibility = View.GONE
        }
    }

    interface ItemClickListener {
        fun onClick(news: NewsModel)
    }


}