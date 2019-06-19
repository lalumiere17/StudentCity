package com.example.studentcity.models.api.client.hostels.coordinateLoader

import android.support.v7.app.AppCompatActivity
import com.example.studentcity.models.api.client.ApiClient
import com.example.studentcity.models.database.Coordinates
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import java.lang.reflect.Type

open interface CoordinateLoaderCallback {
    fun onSuccess(coordinates: HashMap<String, Coordinates>)
    fun onFail()
}

fun load(activity: AppCompatActivity, callback: CoordinateLoaderCallback) {
    GlobalScope.launch {
        val response = withContext(Dispatchers.IO) {
            val client = ApiClient.getInstance(activity)
            return@withContext client.getRequest("api/hostels/coordinates/list")
        }

        if(response.isSuccessful && response.body() != null) {
            try {
                val strJson = response.body()!!.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                val type: Type = object : TypeToken<HashMap<String, Coordinates>>() {}.type

                val res = gson.fromJson<HashMap<String, Coordinates>>(strJson, type)
                activity.runOnUiThread { callback.onSuccess(res) }
            }
            catch (e:Exception) {
                e.printStackTrace()
                activity.runOnUiThread { callback.onFail() }
            }
        }
        else {
            activity.runOnUiThread { callback.onFail() }
        }
    }
}