package com.example.studentcity.fragments.presenters

import android.os.Bundle
import android.text.TextUtils

import com.example.studentcity.R
import com.example.studentcity.fragments.HostelFragment
import com.example.studentcity.models.Router
import com.example.studentcity.models.database.Coordinates
import com.example.studentcity.models.database.Hostel
import com.example.studentcity.models.database.Post
import com.example.studentcity.models.database.Stuff

class HostelFragmentPresenter(private val fragment: HostelFragment) {
    private var hostel: Hostel? = null
    private val router: Router = Router(fragment)

    fun showHostel() {
        val args = fragment.arguments
        if (args == null) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            return
        }

        hostel = args.getSerializable(Hostel.SERIALIZABLE_KEY) as Hostel

        if (hostel == null) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            return
        }

        fragment.showHostelTitle(hostel!!.title)
        fragment.showHostelPhoto(hostel!!.photo)
        fragment.showAddress(hostel!!.address)
        fragment.showNumberFloors(hostel!!.numberFloors)
        fragment.showNumberStudents(hostel!!.numberStudents)
        fragment.showRating(hostel!!.rating)

        val hostelManager = hostel!!.getStuff(Post.HOSTEL_MANAGER_POST)
        if (hostelManager != null) {
            val fullName = hostelManager.toString()
            fragment.showHostelManager(hostelManager.photo, fullName)
        }

        val studentManager = hostel!!.getStuff(Post.STUDENT_MANAGER_POST)
        if (studentManager != null) {
            val fullName = studentManager.toString()
            fragment.showStudentManager(studentManager.photo, fullName)
        }

        val cultureManager = hostel!!.getStuff(Post.CULTURE_MANAGER_POST)
        if (cultureManager != null) {
            val fullName = cultureManager.toString()

            fragment.showCultureManager(cultureManager.photo, fullName)
        }

        val sportManager = hostel!!.getStuff(Post.SPORT_MANAGER_POST)
        if (sportManager != null) {

            val fullName = sportManager.toString()

            fragment.showSportManager(sportManager.photo, fullName)
        }
    }

    fun redirectToCall() {
        if (hostel == null) return

        val phoneNumber = hostel!!.phone
        if (!TextUtils.isEmpty(phoneNumber))
            router.redirectToCallForward(phoneNumber)

    }

    fun redirectToMap(coordinates: Coordinates) {

    }
}