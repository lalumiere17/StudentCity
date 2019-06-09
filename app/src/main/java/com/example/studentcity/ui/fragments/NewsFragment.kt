package com.example.studentcity.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.studentcity.R
import com.example.studentcity.ui.adapters.ListNewsAdapter
import com.example.studentcity.ui.fragments.presenters.NewsFragmentPresenter
import com.example.studentcity.models.news.NewsModel
import kotlinx.android.synthetic.main.app_bar.*

import java.util.ArrayList

class NewsFragment : RootFragment() {

    private var listNewsView: RecyclerView? = null
    private var presenter: NewsFragmentPresenter = NewsFragmentPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.toolbar.title = getString(R.string.news)

        listNewsView = view.findViewById(R.id.list_news_view)
        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        listNewsView!!.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        itemDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
        listNewsView?.addItemDecoration(itemDecoration)
        presenter = NewsFragmentPresenter(this)
    }

   override  fun onResume() {
        super.onResume()
        if (presenter.auth()) {
            presenter.sendRequest()
        }
        presenter.startTokenTracking()
    }

    override fun onStop() {
        super.onStop()
        presenter.stopTokenTracking()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onAuthResult(requestCode, resultCode, data)
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