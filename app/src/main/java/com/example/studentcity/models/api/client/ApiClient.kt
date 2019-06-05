package com.example.studentcity.models.api.client

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.app.FragmentActivity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient private constructor(private val activity: FragmentActivity) {

    companion object {
        private const val API_URL = "http://194.67.78.133/"
        private const val TIMEOUT = 30L
        private const val MEDIA_TYPE = "application/json"

        @SuppressLint("StaticFieldLeak")
        private var singleton: ApiClient? = null

        fun getInstance(activity: FragmentActivity): ApiClient {
            return if (singleton == null) {
                singleton = ApiClient(activity)
                singleton!!
            } else
                singleton!!
        }
    }

    private val client: OkHttpClient
    init {
        client = OkHttpClient.Builder()
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    val isOnline: Boolean
        get() {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }

    fun POSTRequest(action: String, data: Any): Response {

        val mediaType = MediaType.parse(MEDIA_TYPE)
        val jsonData = Gson().toJson(data)

        val body = RequestBody
            .create(mediaType, jsonData)

        val url = String.format("%s%s", API_URL, action)

        val request = Request.Builder()
            .post(body)
            .url(url)
            .build()

        return client.newCall(request).execute()
    }

    @Throws(IOException::class)
    fun getRequest(action: String): Response {

        val url = String.format("%s%s", API_URL, action)

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request).execute()
    }
}