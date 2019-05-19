package com.example.studentcity.fragments.presenters

import android.content.Intent
import com.example.studentcity.R
import com.example.studentcity.fragments.NewsFragment
import com.example.studentcity.models.news.NewsModel
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiPost
import com.vk.sdk.api.model.VKList
import java.util.*

class NewsFragmentPresenter(private val fragment: NewsFragment) {

    private val accessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                VKSdk.logout()
                VKSdk.login(fragment.activity!!, null)
            }
        }
    }

    private val vkAuthCallback = object : VKCallback<VKAccessToken> {
        override fun onResult(res: VKAccessToken) {
            sendRequest()
        }

        override fun onError(error: VKError) {

        }
    }

    private val convertCallback = object : NewsModel.ConvertCallback {
        override fun onConvert(news: ArrayList<NewsModel>) {
            fragment.hideProgress()
            fragment.showNews(news)
        }

        override fun onFail() {

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
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val posts = response.parsedModel as VKList<VKApiPost>
                NewsModel.convert(posts, convertCallback)
            }

            override fun onError(error: VKError) {
                super.onError(error)
                fragment.hideProgress()
            }

            override fun attemptFailed(request: VKRequest, attemptNumber: Int, totalAttempts: Int) {
                super.attemptFailed(request, attemptNumber, totalAttempts)
                fragment.hideProgress()
            }
        })
    }


    fun auth(): Boolean {
        if (fragment.checkInternetConnection()) {
            if (!VKSdk.isLoggedIn()) {
                VKSdk.login(fragment.activity!!, null)
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
        if (!accessTokenTracker.isTracking)
            accessTokenTracker.startTracking()
    }

    fun stopTokenTracking() {
        if (accessTokenTracker.isTracking)
            accessTokenTracker.stopTracking()
    }

    companion object {
        private const val DOMAIN_PARAMETER = "domain"
        private const val DOMAIN = "studgorod.susu"
        private const val COUNT_WALL = 100
    }
}
