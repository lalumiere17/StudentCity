package com.example.studentcity.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentcity.R
import com.example.studentcity.models.QueueItem
import com.example.studentcity.ui.adapters.QueueListAdapter
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.home_questions_fragment.*
import kotlinx.android.synthetic.main.queues_fragment.*

class QueuesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.queues_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.toolbar.title = getString(R.string.my_queues_title)

        val queuesList = ArrayList<QueueItem>()
        queuesList.add(QueueItem(0, "Стирка", "10.06.2019 8:00"))
        queuesList.add(QueueItem(1, "Плотник", "12.06.2019 18:00"))
        queuesList.add(QueueItem(2, "Сантехник", "15.06.2019 15:00"))

        super.onViewCreated(view, savedInstanceState)
        QueueListAdapter(
            context!!,
            queuesList,
            object: QueueListAdapter.ItemClickListener {
                override fun onClick(queueItem: QueueItem) {

                }
            }
        ).also { adapter ->
            val layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            queuesListView.layoutManager = layoutManager

            val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            queuesListView.addItemDecoration(itemDecoration)

            queuesListView.adapter = adapter
        }
    }
}