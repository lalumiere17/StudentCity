package com.example.studentcity.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.example.studentcity.R
import com.example.studentcity.fragments.presenters.HostelFragmentPresenter
import com.example.studentcity.models.ImageLoader.ImageLoader

import de.hdodenhof.circleimageview.CircleImageView

class HostelFragment : RootFragment() {

    private lateinit var hostelPhotoView: ImageView

    private lateinit var buttonCall: Button
    private lateinit var buttonMap: Button

    private lateinit var hostelTitle: TextView
    private lateinit var hostelAddress: TextView
    private lateinit var hostelNumberFloors: TextView
    private lateinit var hostelNumberStudents: TextView
    private lateinit var hostelRating: TextView

    private lateinit var hostelManagerPhoto: CircleImageView
    private lateinit var hostelManagerNameView: TextView

    private lateinit var studentManagerPhotoView: CircleImageView
    private lateinit var studentManagerNameView: TextView

    private lateinit var cultureManagerPhotoView: CircleImageView
    private lateinit var cultureManagerNameView: TextView

    private lateinit var sportManagerPhotoView: CircleImageView
    private lateinit var sportManagerNameView: TextView

    private lateinit var presenter: HostelFragmentPresenter

    private val loaders:ArrayList<ImageLoader> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hostel_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hostelPhotoView = view.findViewById(R.id.hostelPhoto)

        buttonCall = view.findViewById(R.id.buttonCall)
        buttonCall.setOnClickListener { presenter.redirectToCall() }

        buttonMap = view.findViewById(R.id.buttonInMap)

        hostelTitle = view.findViewById(R.id.hostelTitle)
        hostelAddress = view.findViewById(R.id.hostelAddress)
        hostelNumberFloors = view.findViewById(R.id.hostelNumberFloors)
        hostelNumberStudents = view.findViewById(R.id.hostelNumberStudents)
        hostelRating = view.findViewById(R.id.hostelRating)

        hostelManagerPhoto = view.findViewById(R.id.hostelManagerPhoto)
        hostelManagerNameView = view.findViewById(R.id.hostelManagerName)

        studentManagerPhotoView = view.findViewById(R.id.studentManagerPhoto)
        studentManagerNameView = view.findViewById(R.id.studentManagerName)

        cultureManagerPhotoView = view.findViewById(R.id.cultureManagerPhoto)
        cultureManagerNameView = view.findViewById(R.id.cultureManagerName)

        sportManagerPhotoView = view.findViewById(R.id.sportManagerPhoto)
        sportManagerNameView = view.findViewById(R.id.sportManagerName)

        presenter = HostelFragmentPresenter(this)
        presenter.showHostel()
    }

    override fun onPause() {
        super.onPause()
        loaders.forEach { it.cancel() }
    }

    private fun setText(textView: TextView, type: String, text: String) {
        val resultText = String.format("%s: %s", type, text)
        textView.text = resultText
    }

    fun showHostelPhoto(urlPhoto: String) {
        if (TextUtils.isEmpty(urlPhoto)) return

        val imageLoader = ImageLoader(hostelPhotoView, urlPhoto)
        loaders.add(imageLoader)
        imageLoader.load()
    }
    fun showHostelTitle(title: String) {
        if (TextUtils.isEmpty(title)) return
        hostelTitle.text = title
    }

    fun showAddress(address: String) {
        val typeForAddress = getString(R.string.address)
        setText(hostelAddress, typeForAddress, address)
    }

    fun showNumberFloors(number: Int) {
        val typeForNumberFloors = getString(R.string.numberFloors)
        setText(hostelNumberFloors, typeForNumberFloors, number.toString())
    }

    fun showNumberStudents(number: Int) {
        val typeForNumberStudents = getString(R.string.numberStudent)
        setText(hostelNumberStudents, typeForNumberStudents, number.toString())
    }

    fun showRating(rating: Double) {
        val typeForRating = getString(R.string.rating)
        setText(hostelRating, typeForRating, rating.toString())
    }

    fun showHostelManager(urlPhoto: String, name: String) {
        showManagerInfo(hostelManagerNameView, hostelManagerPhoto, urlPhoto, name)
    }

    fun showStudentManager(urlPhoto: String, name: String) {
        showManagerInfo(studentManagerNameView, studentManagerPhotoView, urlPhoto, name)
    }

    fun showCultureManager(urlPhoto: String, name: String) {
        showManagerInfo(cultureManagerNameView, cultureManagerPhotoView, urlPhoto, name)
    }

    fun showSportManager(urlPhoto: String, name: String) {
        showManagerInfo(sportManagerNameView, sportManagerPhotoView, urlPhoto, name)
    }

    private fun checkPhotoAndName(photo: String, name: String): Boolean {
        return TextUtils.isEmpty(photo) || TextUtils.isEmpty(name)
    }

    private fun showManagerInfo(nameManagerView: TextView,
                                photoManagerView: CircleImageView,
                                name: String,
                                urlPhoto: String) {
        if (checkPhotoAndName(urlPhoto, name)) return

        val imageLoader = ImageLoader(photoManagerView, urlPhoto)
        loaders.add(imageLoader)
        imageLoader.load()
        nameManagerView.text = name
    }
}