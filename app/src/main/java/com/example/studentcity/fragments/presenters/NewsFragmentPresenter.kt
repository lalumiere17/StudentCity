package com.example.studentcity.fragments.presenters

import android.content.Intent
import android.text.TextUtils

import com.example.studentcity.R
import com.example.studentcity.fragments.NewsFragment
import com.example.studentcity.models.news.NewsModel
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiPhoto
import com.vk.sdk.api.model.VKApiPost
import com.vk.sdk.api.model.VKAttachments
import com.vk.sdk.api.model.VKList

import java.util.ArrayList

class NewsFragmentPresenter(private val fragment: NewsFragment) {

    private val accessTokenTracker = object : VKAccessTokenTracker() {
        fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                VKSdk.logout()
                VKSdk.login(fragment.getActivity(), null)
            }
        }
    }

    private val vkAuthCallback = object : VKCallback<VKAccessToken>() {
        fun onResult(res: VKAccessToken) {
            sendRequest()
        }

        fun onError(error: VKError) {

        }
    }

    private val convertCallback = object : NewsModel.ConvertCallback() {
        fun onConvert(news: ArrayList<NewsModel>) {
            fragment.hideProgress()
            fragment.showNews(news)
        }

        fun onFail() {

        }
    }

    fun sendRequest() {

        if (!fragment.checkInternetConnection()) {
            fragment.showMessage(fragment.getString(R.string.internet_connection_is_messed))
            return
        }

        fragment.showProgress()

        val request = VKApi
            .wall()
            .get(
                VKParameters
                    .from(
                        DOMAIN_PARAMETER, DOMAIN, VKApiConst.COUNT, COUNT_WALL,
                        VKApiConst.EXTENDED, 1
                    )
            )

        request.executeWithListener(object : VKRequest.VKRequestListener() {
            fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val posts = response.parsedModel as VKList<VKApiPost>
                NewsModel.convert(posts, convertCallback)
            }

            fun onError(error: VKError) {
                super.onError(error)
                fragment.hideProgress()
            }

            fun attemptFailed(request: VKRequest, attemptNumber: Int, totalAttempts: Int) {
                super.attemptFailed(request, attemptNumber, totalAttempts)
                fragment.hideProgress()
            }
        })
    }


    fun auth(): Boolean {
        if (fragment.checkInternetConnection()) {
            if (!VKSdk.isLoggedIn()) {
                VKSdk.login(fragment.getActivity(), null)
                return false
            }

            return true
        } else {
            fragment.showMessage(fragment.getString(R.string.internet_connection_is_messed))
            return false
        }
    }

    fun onAuthResult(requestCode: Int, resultCode: Int, data: Intent) {
        VKSdk.onActivityResult(requestCode, resultCode, data, vkAuthCallback)
    }

    fun startTokenTracking() {
        if (!accessTokenTracker.isTracking())
            accessTokenTracker.startTracking()
    }

    fun stopTokenTracking() {
        if (accessTokenTracker.isTracking())
            accessTokenTracker.stopTracking()
    }

    companion object {

        private val DOMAIN_PARAMETER = "domain"
        private val DOMAIN = "studgorod.susu"
        private val COUNT_WALL = 100
    }
}
