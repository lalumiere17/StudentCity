package com.example.studentcity.models.api.client.hostels

import android.support.v4.app.FragmentActivity


class LoaderOfHostels {
    private var task: DownloadListOfHostelsTask? = null

    fun download(activity: FragmentActivity, callback: DownloadListOfHostelsCallback) {
        this.task = DownloadListOfHostelsTask(activity, callback)
        this.task!!.execute()
    }

    fun cancel() {
        if (!task!!.isCancelled) {
            task!!.cancel(true)
        }
    }
}