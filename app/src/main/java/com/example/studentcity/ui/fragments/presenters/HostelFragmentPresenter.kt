package com.example.studentcity.ui.fragments.presenters

import android.text.TextUtils

import com.example.studentcity.R
import com.example.studentcity.ui.fragments.HostelFragment
import com.example.studentcity.models.Router
import com.example.studentcity.models.api.client.hostels.hostel.info.DownloadHostelInfoCallback
import com.example.studentcity.models.api.client.hostels.hostel.info.HostelInfoLoader
import com.example.studentcity.models.database.Coordinates
import com.example.studentcity.models.database.Hostel
import com.example.studentcity.models.database.Post

class HostelFragmentPresenter(private val fragment: HostelFragment) {
    private val router: Router = Router(fragment)
    private val hostelInfoLoader: HostelInfoLoader = HostelInfoLoader()

    fun showHostel() {
        val args = fragment.arguments
        if (args == null) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            return
        }

        val hostelName = args.getSerializable(Hostel.SERIALIZABLE_KEY) as String

        if (TextUtils.isEmpty(hostelName)) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            return
        }

        hostelInfoLoader.load(fragment.activity!!, hostelName, object : DownloadHostelInfoCallback{
            override fun onSuccess(hostel: Hostel) {
                fragment.buttonCall.setOnClickListener { router.redirectToCallForward(hostel.phone) }

                fragment.showHostelTitle(hostel.title)
                fragment.showHostelPhoto(hostel.photo)
                fragment.showAddress(hostel.address)
                fragment.showNumberFloors(hostel.numberFloors)
                fragment.showNumberStudents(hostel.numberStudents)
                fragment.showRating(hostel.rating)

                val hostelManager = hostel.getStuff(Post.HOSTEL_MANAGER_POST)
                if (hostelManager != null) {
                    val fullName = hostelManager.toString()
                    fragment.showHostelManager(hostelManager.photo, fullName)
                }

                val studentManager = hostel.getStuff(Post.STUDENT_MANAGER_POST)
                if (studentManager != null) {
                    val fullName = studentManager.toString()
                    fragment.showStudentManager(studentManager.photo, fullName)
                }

                val cultureManager = hostel.getStuff(Post.CULTURE_MANAGER_POST)
                if (cultureManager != null) {
                    val fullName = cultureManager.toString()
                    fragment.showCultureManager(cultureManager.photo, fullName)
                }

                val sportManager = hostel.getStuff(Post.SPORT_MANAGER_POST)
                if (sportManager != null) {
                    val fullName = sportManager.toString()
                    fragment.showSportManager(sportManager.photo, fullName)
                }
            }

            override fun onFailInternetConnection() {
                fragment.showMessage(fragment.getString(R.string.internet_connection_is_messed))
            }

            override fun onServerError() {
                fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            }
        })
    }

    fun redirectToMap(coordinates: Coordinates) {

    }
}