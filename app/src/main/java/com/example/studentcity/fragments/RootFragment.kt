package com.example.studentcity.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ProgressBar

import com.example.studentcity.R

class RootFragment : Fragment() {
    protected var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = activity!!.findViewById(R.id.progress_bar)
    }

    fun checkInternetConnection(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnected ?: false
    }

    fun showMessage(message: String) {
        Snackbar.make(view!!.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    fun showProgress() {
        if (progressBar.visibility != View.VISIBLE)
            progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        if (progressBar.visibility != View.INVISIBLE)
            progressBar.visibility = View.INVISIBLE
    }
}