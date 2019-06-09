package com.example.studentcity.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.studentcity.R
import com.example.studentcity.ui.adapters.SpinnerAdapter
import kotlinx.android.synthetic.main.zkh_create_ticker.*

class ZkhCreateTickerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.zkh_create_ticker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        SpinnerAdapter(
            context!!,
            R.layout.spinner_item,
            resources.getStringArray(R.array.ticket_types)
        ).also { adapter ->
            typeTickerView.adapter = adapter
            typeTickerView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    descriptionProblemView.isEnabled = position != 2
                }
            }
        }

        SpinnerAdapter(
            context!!,
            R.layout.spinner_item,
            resources.getStringArray(R.array.ticket_times)
        ).also { adapter ->
            timeTickerView.adapter = adapter
        }

        createTickerButton.setOnClickListener { this.fragmentManager?.popBackStack() }
    }
}