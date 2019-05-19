package com.example.studentcity.fragments

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.ParcelUuid
import android.print.PrinterId
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.studentcity.R
import com.example.studentcity.adapters.ListNewsAdapter
import com.example.studentcity.fragments.presenters.NewsFragmentPresenter
import com.example.studentcity.models.news.NewsModel

import java.util.ArrayList

class NewsFragment : RootFragment() {

    private var listNewsView: RecyclerView? = null
    private var presenter: NewsFragmentPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onViewCreated(
        fragmentView: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(fragmentView, savedInstanceState)

        listNewsView = fragmentView.findViewById(R.id.list_news_view)
        val layoutManager = LinearLayoutManager(
            getContext(),
            LinearLayoutManager.VERTICAL, false
        )
        listNewsView!!.layoutManager = layoutManager
        presenter = NewsFragmentPresenter(this)
    }

   override  fun onResume() {
        super.onResume()

        if (presenter!!.auth()) {
            presenter!!.sendRequest()
        }
        presenter!!.startTokenTracking()
    }

    override fun onStop() {
        super.onStop()
        presenter!!.stopTokenTracking()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter!!.onAuthResult(requestCode, resultCode, data)
    }

    fun showNews(news: ArrayList<NewsModel>) {
        if (listNewsView!!.adapter != null) {
            val adapter = listNewsView!!.adapter as ListNewsAdapter?
            adapter!!.update(news)
            return
        }

        val adapter = ListNewsAdapter(context!!, news, null)
        listNewsView!!.adapter = adapter
    }
}