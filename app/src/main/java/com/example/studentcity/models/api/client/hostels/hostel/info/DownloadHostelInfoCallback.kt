package com.example.studentcity.models.api.client.hostels.hostel.info

import com.example.studentcity.models.database.Hostel

interface DownloadHostelInfoCallback {
    fun onSuccess(hostel: Hostel)
    fun onFailInternetConnection()
    fun onServerError()
}