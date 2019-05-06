package com.example.studentcity.models.api.client.Hostels

import com.example.studentcity.models.database.Hostel

interface DownloadListOfHostelsCallback {
    fun onSuccess(hostels: Array<Hostel>)
    fun onFailInternetConnection()
    fun onServerError()
}
