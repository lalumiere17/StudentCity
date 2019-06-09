package com.example.studentcity.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentcity.R
import com.example.studentcity.models.HomeQuestion
import com.example.studentcity.models.Router
import com.example.studentcity.ui.adapters.HomeQuestionsAdapter
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.home_questions_fragment.*

class HomeQuestionsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_questions_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity!!.toolbar.title = getString(R.string.home_questions)

        val homeQuestions = ArrayList<HomeQuestion>()
        homeQuestions.add(HomeQuestion(0, getString(R.string.my_queues_title), getString(R.string.my_queues_description)))
        homeQuestions.add(HomeQuestion(1, getString(R.string.zkhTitle), getString(R.string.zkhDescription)))

        super.onViewCreated(view, savedInstanceState)
        val router = Router(this)
        HomeQuestionsAdapter(
            context!!,
            homeQuestions,
            object: HomeQuestionsAdapter.ItemClickListener {
                override fun onClick(homeQuestion: HomeQuestion) {
                    when(homeQuestion.id) {
                        0 -> { router.showFragment(QueuesFragment(), null) }
                    }
                }
            }
        ).also { adapter ->
            val layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            homeQuestionsList.layoutManager = layoutManager

            val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            homeQuestionsList.addItemDecoration(itemDecoration)

            homeQuestionsList.adapter = adapter
        }
    }
}