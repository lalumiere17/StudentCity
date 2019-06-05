package com.example.studentcity

import android.app.Application
import com.vk.sdk.VKSdk

class DefaultApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(this)
    }
}