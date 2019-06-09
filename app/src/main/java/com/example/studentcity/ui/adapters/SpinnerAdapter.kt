package com.example.studentcity.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.example.studentcity.R
import kotlinx.android.synthetic.main.spinner_item.view.*

class SpinnerAdapter(context: Context,
                     private val resource: Int,
                     private val strings: Array<String>) : ArrayAdapter<String>(context, resource, strings) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, parent)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, parent)
    }

    override fun getCount(): Int {
        return strings.size
    }

    private fun getView(position: Int, viewGroup: ViewGroup) : View {
        val view = LayoutInflater.from(context).inflate(resource, viewGroup, false)
        view.spinnerTextView.text = strings[position]
        return view
    }
}