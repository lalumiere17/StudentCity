package com.example.studentcity.fragments

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.example.studentcity.R
import com.example.studentcity.fragments.presenters.HostelFragmentPresenter
import com.example.studentcity.models.ImageLoader.ImageLoader

import de.hdodenhof.circleimageview.CircleImageView

class HostelFragment : RootFragment() {

    private var hostelPhoto: ImageView? = null

    private var buttonCall: Button? = null
    private var buttonMap: Button? = null

    private var hostelTitle: TextView? = null
    private var hostelAddress: TextView? = null
    private var hostelNumberFloors: TextView? = null
    private var hostelNumberStudents: TextView? = null
    private var hostelRating: TextView? = null

    private var hostelManagerPhoto: CircleImageView? = null
    private var hostelManagerName: TextView? = null

    private var studentManagerPhoto: CircleImageView? = null
    private var studentManagerName: TextView? = null

    private var cultureManagerPhoto: CircleImageView? = null
    private var cultureManagerName: TextView? = null

    private var sportManagerPhoto: CircleImageView? = null
    private var sportManagerName: TextView? = null

    private var progressBar: ProgressBar? = null

    private var presenter: HostelFragmentPresenter? = null

    private var hostelPhotoLoader: ImageLoader? = null
    private var hostelManagerPhotoLoader: ImageLoader? = null
    private var studentManagerPhotoLoader: ImageLoader? = null
    private var cultureManagerPhotoLoader: ImageLoader? = null
    private var sportManagerPhotoLoader: ImageLoader? = null

    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hostel_fragment, container, false)
    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hostelPhoto = view.findViewById(R.id.hostelPhoto)

        buttonCall = view.findViewById(R.id.buttonCall)
        buttonCall!!.setOnClickListener { presenter!!.redirectToCall() }

        buttonMap = view.findViewById(R.id.buttonInMap)

        hostelTitle = view.findViewById(R.id.hostelTitle)
        hostelAddress = view.findViewById(R.id.hostelAddress)
        hostelNumberFloors = view.findViewById(R.id.hostelNumberFloors)
        hostelNumberStudents = view.findViewById(R.id.hostelNumberStudents)
        hostelRating = view.findViewById(R.id.hostelRating)

        hostelManagerPhoto = view.findViewById<View>(R.id.hostelManagerPhoto)
        hostelManagerName = view.findViewById(R.id.hostelManagerName)

        studentManagerPhoto = view.findViewById<View>(R.id.studentManagerPhoto)
        studentManagerName = view.findViewById(R.id.studentManagerName)

        cultureManagerPhoto = view.findViewById<View>(R.id.cultureManagerPhoto)
        cultureManagerName = view.findViewById(R.id.cultureManagerName)

        sportManagerPhoto = view.findViewById<View>(R.id.sportManagerPhoto)
        sportManagerName = view.findViewById(R.id.sportManagerName)

        progressBar = Activity().findViewById(R.id.progress_bar)

        presenter = HostelFragmentPresenter(this)
        presenter!!.showHostel()
    }

    fun onStop() {
        super.onStop()
        if (hostelPhotoLoader != null)
            hostelPhotoLoader!!.cancel()
    }

    fun onPause() {
        super.onPause()
        if (hostelPhotoLoader != null)
            hostelPhotoLoader!!.cancel()
    }

    fun onResume() {
        super.onResume()
    }

    private fun setText(textView: TextView, type: String, text: String) {
        val resultText = String.format("%s: %s", type, text)
        textView.text = resultText
    }

    fun showHostelPhoto(photo: String) {
        if (TextUtils.isEmpty(photo)) return

        if (hostelPhotoLoader == null) {
            hostelPhotoLoader = ImageLoader(hostelPhoto, photo)
            hostelPhotoLoader!!.load()
        }
    }

    fun showHostelTitle(title: String) {
        if (TextUtils.isEmpty(title)) return
        hostelTitle!!.text = title
    }

    fun showAddress(address: String) {
        val typeForAddress = getString(R.string.address)
        setText(hostelAddress!!, typeForAddress, address)
    }

    fun showNumberFloors(number: Int) {
        val typeForNumberFloors = getString(R.string.numberFloors)
        setText(hostelNumberFloors!!, typeForNumberFloors, number.toString())
    }

    fun showNumberStudents(number: Int) {
        val typeForNumberStudents = getString(R.string.numberStudent)
        setText(hostelNumberStudents!!, typeForNumberStudents, number.toString())
    }

    fun showRating(rating: Double) {
        val typeForRating = getString(R.string.rating)
        setText(hostelRating!!, typeForRating, rating.toString())
    }

    private fun checkPhotoAndName(photo: String, name: String): Boolean {
        return TextUtils.isEmpty(photo) || TextUtils.isEmpty(name)
    }

    fun showHostelManager(photo: String, name: String) {
        if (checkPhotoAndName(photo, name)) return

        if (hostelManagerPhotoLoader == null) {
            hostelManagerPhotoLoader = ImageLoader(hostelManagerPhoto, photo)
            hostelManagerPhotoLoader!!.load()
        }
        hostelManagerName!!.text = name
    }

    fun showStudentManager(photo: String, name: String) {
        if (checkPhotoAndName(photo, name)) return

        if (studentManagerPhotoLoader == null) {
            studentManagerPhotoLoader = ImageLoader(studentManagerPhoto, name)
            studentManagerPhotoLoader!!.load()
        }
        studentManagerName!!.text = name
    }

    fun showCultureManager(photo: String, name: String) {
        if (checkPhotoAndName(photo, name)) return

        if (cultureManagerPhotoLoader == null) {
            cultureManagerPhotoLoader = ImageLoader(cultureManagerPhoto, photo)
            cultureManagerPhotoLoader!!.load()
        }
        cultureManagerName!!.text = name
    }

    fun showSportManager(photo: String, name: String) {
        if (checkPhotoAndName(photo, name)) return

        if (sportManagerPhotoLoader == null) {
            sportManagerPhotoLoader = ImageLoader(sportManagerPhoto, name)
            sportManagerPhotoLoader!!.load()
        }
        sportManagerName!!.text = name
    }
}