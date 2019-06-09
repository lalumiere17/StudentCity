package com.example.studentcity.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.studentcity.R
import com.example.studentcity.models.api.client.hostels.DownloadListOfHostelsCallback
import com.example.studentcity.models.api.client.hostels.LoaderOfHostels
import com.example.studentcity.models.database.Hostel
import kotlinx.android.synthetic.main.fragment_create_pass.*

class CreatePassFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loaderOfHostels = LoaderOfHostels()
        loaderOfHostels.download(activity!!, object:DownloadListOfHostelsCallback {
            override fun onFailInternetConnection() {

            }

            override fun onServerError() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess(hostels: Array<Hostel>) {
                val hostelNames = ArrayList<String>()
                for (hostel in hostels)
                    hostelNames.add(hostel.title)

                ArrayAdapter<String>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    hostelNames
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    hostelNameView.adapter = adapter
                }
            }
        })
    }
}