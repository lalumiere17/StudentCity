package com.example.studentcity.fragments.presenters

import com.example.studentcity.R
import com.example.studentcity.fragments.ListOfHostelsFragment
import com.example.studentcity.models.api.client.Hostels.DownloadListOfHostelsCallback
import com.example.studentcity.models.api.client.Hostels.LoaderOfHostels
import com.example.studentcity.models.database.Hostel

class ListOfHostelsFragmentPresenter(private val fragment: ListOfHostelsFragment) {
    private var loader: LoaderOfHostels? = null

    fun downloadListOfHostels() {
        loader = LoaderOfHostels()
        fragment.showProgress()
        loader!!.download(fragment.activity!!, object : DownloadListOfHostelsCallback {
            override fun onSuccess(hostels: Array<Hostel>) {
                fragment.hideProgress()
                fragment.showListHostel(hostels)
            }

            override fun onFailInternetConnection() {
                fragment.hideProgress()
                fragment.showMessage(fragment.getString(R.string.internet_connection_is_messed))
            }

            override fun onServerError() {
                fragment.hideProgress()
                fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            }
        })
    }

    fun cancelDownloading() {
        loader!!.cancel()
    }
}