package com.example.studentcity.models.api.client.hostels.hostel.info

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.example.studentcity.models.api.client.ApiClient
import com.example.studentcity.models.database.Hostel
import com.google.gson.Gson
import java.lang.Exception

class DownloadHostelIInfoTask(
    activity:FragmentActivity,
    private val hostelName:String,
    private val callback: DownloadHostelInfoCallback) : AsyncTask<Void, Void, ResponseResult>() {

    companion object {
        private const val ACTION_LOAD_HOSTEL_INFO = "api/hostel/info"
    }

    private val apiClient:ApiClient = ApiClient.getInstance(activity)

    override fun doInBackground(vararg params: Void?): ResponseResult {
        if(!apiClient.isOnline) return MissedInternetConnectionResult()
        try {
            val response = apiClient.getRequest(String.format("%s/%s", ACTION_LOAD_HOSTEL_INFO, hostelName))
            if(response.isSuccessful) {
                val jsonString = response.body()?.string()
                return if(!TextUtils.isEmpty(jsonString)) {
                    SuccessResult(Gson().fromJson(jsonString, Hostel::class.java))
                } else ErrorResult()
            }
            return ErrorResult()
        }
        catch (e: Exception) {
            return ErrorResult()
        }
    }

    override fun onPostExecute(result: ResponseResult) {
        when(result.javaClass) {
            SuccessResult::class.java -> callback.onSuccess((result as SuccessResult).hostel)
            ErrorResult::class.java -> callback.onServerError()
            MissedInternetConnectionResult::class.java -> callback.onFailInternetConnection()
        }
    }
}