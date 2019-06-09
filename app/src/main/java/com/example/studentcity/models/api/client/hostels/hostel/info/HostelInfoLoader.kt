package com.example.studentcity.models.api.client.hostels.hostel.info

import android.support.v4.app.FragmentActivity

open class HostelInfoLoader {
    private var task:DownloadHostelIInfoTask? = null

    fun load(fragmentActivity: FragmentActivity, hostelName:String, callback: DownloadHostelInfoCallback) {
        task = DownloadHostelIInfoTask(fragmentActivity, hostelName, callback)
        task!!.execute()
    }

    fun cancel() {
        if(task != null && !task!!.isCancelled) {
            task!!.cancel(true)
        }
    }
}