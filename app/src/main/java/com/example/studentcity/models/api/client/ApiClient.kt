package com.example.studentcity.models.api.client

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.app.FragmentActivity

import com.google.gson.Gson

import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class ApiClient private constructor(private val activity: FragmentActivity) {

    private val client: OkHttpClient

    val isOnline: Boolean
        get() {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }

    init {
        client = OkHttpClient.Builder()
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
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

    companion object {
        private const val API_URL = "http://192.168.31.72/"
        private const val TIMEOUT = 30L
        private const val MEDIA_TYPE = "application/json"

        @SuppressLint("StaticFieldLeak")
        private var singleton: ApiClient? = null

        fun getInstance(activity: FraggtmentActivity): ApiClient {
            return if (singleton == null) {
                singleton = ApiClient(activity)
                singleton!!
            } else
                singleton!!
        }
    }


}