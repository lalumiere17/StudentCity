package com.example.studentcity.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentcity.R
import com.example.studentcity.models.QueueItem
import kotlinx.android.synthetic.main.queue_item.view.*

class QueueListAdapter (private val context: Context,
                        private val queues: ArrayList<QueueItem>,
                        private val itemClickListenerListener:ItemClickListener): RecyclerView.Adapter<QueueListAdapter.ViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.queue_item, container, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return queues.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindDate(queues[position].title, queues[position].time)
        viewHolder.itemView.setOnClickListener { itemClickListenerListener.onClick(queues[position]) }
    }

    override fun onClick(v: View?) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindDate(title: String, time: String) {
            itemView.titleQueueView.text = title
            itemView.timeQueueView.text = time
        }
    }

    interface ItemClickListener {
        fun onClick(queueItem: QueueItem)
    }
}