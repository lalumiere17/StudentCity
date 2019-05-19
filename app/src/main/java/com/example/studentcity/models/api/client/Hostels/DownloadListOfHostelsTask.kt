package com.example.studentcity.models.api.client.Hostels

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.example.studentcity.models.api.client.ApiClient
import com.example.studentcity.models.database.Hostel
import com.google.gson.Gson
import java.io.IOException

internal class DownloadListOfHostelsTask(
    activity: FragmentActivity,
    private val callback: DownloadListOfHostelsCallback) : AsyncTask<Void, Void, ResponseResult>() {

    private val client: ApiClient = ApiClient.getInstance(activity)

    override fun doInBackground(vararg voids: Void): ResponseResult {
        return if (client.isOnline) {
            try {
                val response = client.getRequest(API_URL_ACTION)
                val jsonResponse = response.body()?.string()
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
        when {
            SuccessResult::class.java == responseResult.javaClass -> {
                val result = responseResult as SuccessResult
                callback.onSuccess(result.hostels)
            }
            ErrorResult::class.java == responseResult.javaClass -> callback.onServerError()
            MissedInternetConnectionResult::class.java == responseResult.javaClass -> callback.onFailInternetConnection()
        }
    }

    override fun onCancelled() {
        super.onCancelled()
    }

    companion object {

        val API_URL_ACTION = "api/hostels/list"
    }
}
