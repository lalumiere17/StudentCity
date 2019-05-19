package com.example.studentcity.fragments

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_hostels_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleRegistry = LifecycleRegistry(this)
        listOfHostelsView = view.findViewById(R.id.list_of_hostel)
        presenter = ListOfHostelsFragmentPresenter(this)
        router = Router(this)
    }

    override fun onPause() {
        super.onPause()
        presenter!!.cancelDownloading()
        hideProgress()
    }

    override fun onStop() {
        super.onStop()
        presenter!!.cancelDownloading()
        hideProgress()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.cancelDownloading()
        hideProgress()
    }

    override fun onResume() {
        super.onResume()
        presenter!!.downloadListOfHostels()
    }

    fun showListHostel(hostels: Array<Hostel>) {
        val adapter = HostelListAdapter(context!!, hostels, this)
        val linearLayoutManager = LinearLayoutManager(
            activity!!, LinearLayoutManager.VERTICAL, false
        )
        listOfHostelsView!!.layoutManager = linearLayoutManager
        listOfHostelsView!!.adapter = adapter
    }

    override fun onItemClick(hostel: Hostel) {
        val args = Bundle()
        args.putSerializable(Hostel.SERIALIZABLE_KEY, hostel)
        router!!.showFragment(HostelFragment(), args)
    }
}
