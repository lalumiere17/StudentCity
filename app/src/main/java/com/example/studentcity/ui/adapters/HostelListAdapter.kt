package com.example.studentcity.ui.adapters

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.studentcity.R
import com.example.studentcity.models.database.Hostel

class HostelListAdapter(
    private val context: Context,
    private val hostels: Array<Hostel>,
    private val itemClickListener: onItemClickListener)
    : RecyclerView.Adapter<HostelListAdapter.ViewHolder>(), View.OnClickListener {

    private var inflater: LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(container: ViewGroup, i: Int): HostelListAdapter.ViewHolder {
        val itemList : View = inflater.inflate(R.layout.item_of_hostel_list, container,
            false)

        return ViewHolder(itemList)
    }

    override fun onBindViewHolder(
        viewHolder: HostelListAdapter.ViewHolder,
        position: Int) {
        val hostelName : TextView = viewHolder.itemView.findViewById(R.id.hostelName)
        val hostelAddress : TextView = viewHolder.itemView.findViewById(R.id.hostelAddress)

        viewHolder.itemView.setOnClickListener(View.OnClickListener {
            val hostel: Hostel = hostels[position]
            itemClickListener.onItemClick(hostel)
        })

        val hostel: Hostel = hostels[position]
        setText(hostelName, hostel.title)
        setText(hostelAddress, hostel.title)
    }

    private fun setText(textView: TextView, text: String) {
        val stub = context.getString(R.string.missing)
        if (!TextUtils.isEmpty(text)) {
            textView.text = text
        } else {
            textView.text = stub
        }
    }

    override fun getItemCount(): Int {
        return hostels.size
    }

    override fun onClick(v: View) {
        Snackbar.make(v, "Нажатие", Snackbar.LENGTH_LONG).show()
    }

    fun getItem(position: Int): Hostel {
        return hostels[position]
    }

    fun showMessage(message: String) {}

    class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface onItemClickListener {
        fun onItemClick(hostel: Hostel)
    }
}