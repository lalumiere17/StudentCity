package com.example.studentcity.fragments

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.example.studentcity.R
import com.example.studentcity.adapters.HostelListAdapter
import com.example.studentcity.fragments.presenters.ListOfHostelsFragmentPresenter
import com.example.studentcity.models.Router
import com.example.studentcity.models.database.Hostel

class ListOfHostelsFragment : RootFragment(), HostelListAdapter.onItemClickListener, LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry? = null

    private var listOfHostelsView: RecyclerView? = null

    private var presenter: ListOfHostelsFragmentPresenter? = null

    private var router: Router? = null

    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_hostels_fragment, container, false)
    }

    fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(fragmentView, savedInstanceState)

        lifecycleRegistry = LifecycleRegistry(this)
        listOfHostelsView = fragmentView.findViewById(R.id.list_of_hostel)
        presenter = ListOfHostelsFragmentPresenter(this)
        router = Router(this)

        setRetainInstance(true)
    }

    fun onPause() {
        super.onPause()
        presenter!!.cancelDownloading()
        hideProgress()
    }

    fun onStop() {
        super.onStop()
        presenter!!.cancelDownloading()
        hideProgress()
    }

    fun onDestroy() {
        super.onDestroy()
        presenter!!.cancelDownloading()
        hideProgress()
    }

    fun onResume() {
        super.onResume()
        presenter!!.downloadListOfHostels()
    }

    fun showListHostel(hostels: Array<Hostel>) {
        val adapter = HostelListAdapter(getContext(), hostels, this)
        val linearLayoutManager = LinearLayoutManager(
            getActivity(), LinearLayoutManager.VERTICAL, false
        )
        listOfHostelsView!!.layoutManager = linearLayoutManager
        listOfHostelsView!!.adapter = adapter
    }

    fun onItemClick(hostel: Hostel) {
        val args = Bundle()
        args.putSerializable(Hostel.SERIALIZABLE_KEY, hostel)
        router!!.showFragment(HostelFragment(), args)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
