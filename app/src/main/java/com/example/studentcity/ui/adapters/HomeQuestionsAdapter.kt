package com.example.studentcity.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentcity.R
import com.example.studentcity.models.HomeQuestion
import kotlinx.android.synthetic.main.home_questions_item.view.*

open class HomeQuestionsAdapter(private val context: Context,
                                private val homeQuestions: ArrayList<HomeQuestion>,
                                private val itemClickListenerListener:ItemClickListener): RecyclerView.Adapter<HomeQuestionsAdapter.ViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.home_questions_item, container, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return homeQuestions.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindDate(homeQuestions[position].title, homeQuestions[position].description)
        viewHolder.itemView.setOnClickListener { itemClickListenerListener.onClick(homeQuestions[position]) }
    }

    override fun onClick(v: View?) {

    }

    class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindDate(title:String, description:String) {
            itemView.titleView.text = title
            itemView.descriptionView.text = description
        }
    }

    interface ItemClickListener {
        fun onClick(homeQuestion: HomeQuestion)
    }

}