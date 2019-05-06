package com.example.studentcity.models.api.client.Hostels

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity

import com.google.gson.Gson
import com.example.studentcity.models.api.client.ApiClient
import com.example.studentcity.models.database.Hostel

import java.io.IOException

import okhttp3.Response

internal class DownloadListOfHostelsTask(
    activity: FragmentActivity,
    private val callback: DownloadListOfHostelsCallback
) : AsyncTask<Void, Void, ResponseResult>() {

    private val client: ApiClient

    init {
        client = ApiClient.getInstance(activity)
    }

    override fun doInBackground(vararg voids: Void): ResponseResult {
        return if (client.isOnline()) {
            try {
                val response = client.getRequest(API_URL_ACTION)
                val jsonResponse = response.body().string()
                val hostels = Gson().fromJson(jsonResponse, Array<Hostel>::class.java)

                SuccessResult(hostels)
            } catch (exception: IOException) {
                ErrorResult()
            }

        } else {
            MissedInternetConnectionResult()
        }
    }

    override fun onPostExecute(responseResult: ResponseResult) {
        if (isCancelled) return

        if (SuccessResult::class.java == responseResult.getClass()) {
            val result = responseResult as SuccessResult
            callback.onSuccess(result.getHostels())
        } else if (ErrorResult::class.java == responseResult.getClass())
            callback.onServerError()
        else if (MissedInternetConnectionResult::class.java == responseResult.getClass())
            callback.onFailInternetConnection()
    }

    override fun onCancelled() {
        super.onCancelled()
    }

    companion object {

        val API_URL_ACTION = "api/hostels/list"
    }
}
