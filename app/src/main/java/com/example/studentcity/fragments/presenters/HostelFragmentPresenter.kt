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
    private val router: Router

    init {
        this.router = Router(fragment)
    }

    fun showHostel() {
        val args = fragment.getArguments()
        if (args == null) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            return
        }

        hostel = args!!.getSerializable(Hostel.SERIALIZABLE_KEY) as Hostel

        if (hostel == null) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data))
            return
        }

        fragment.showHostelTitle(hostel!!.getTitle())
        fragment.showHostelPhoto(hostel!!.getPhoto())
        fragment.showAddress(hostel!!.getAddress())
        fragment.showNumberFloors(hostel!!.getNumberFloors())
        fragment.showNumberStudents(hostel!!.getNumberStudents())
        fragment.showRating(hostel!!.getRating())

        val hostelManager = hostel!!.getStuff(Post.HOSTEL_MANAGER_POST)
        if (hostelManager != null) {
            val fullName = String.format(
                "%s %s %s", hostelManager!!.getSurname(),
                hostelManager!!.getFirstname(), hostelManager!!.getPatronymic()
            )

            fragment.showHostelManager(hostelManager!!.getPhoto(), fullName)
        }

        val studentManager = hostel!!.getStuff(Post.STUDENT_MANAGER_POST)
        if (studentManager != null) {
            val fullName = String.format(
                "%s %s %s", studentManager!!.getSurname(),
                studentManager!!.getFirstname(), studentManager!!.getPatronymic()
            )

            fragment.showStudentManager(studentManager!!.getPhoto(), fullName)
        }

        val cultureManager = hostel!!.getStuff(Post.CULTURE_MANAGER_POST)
        if (cultureManager != null) {
            val fullName = String.format(
                "%s %s %s", cultureManager!!.getSurname(),
                cultureManager!!.getFirstname(), cultureManager!!.getPatronymic()
            )

            fragment.showCultureManager(cultureManager!!.getPhoto(), fullName)
        }

        val sportManager = hostel!!.getStuff(Post.SPORT_MANAGER_POST)
        if (sportManager != null) {

            val fullName = String.format(
                "%s %s %s", sportManager!!.getSurname(),
                sportManager!!.getFirstname(), sportManager!!.getPatronymic()
            )

            fragment.showSportManager(sportManager!!.getPhoto(), fullName)
        }
    }

    fun redirectToCall() {
        if (hostel == null) return

        val phoneNumber = hostel!!.getPhone()
        if (!TextUtils.isEmpty(phoneNumber))
            router.redirectToCallForward(phoneNumber)

    }

    fun redirectToMap(coordinates: Coordinates) {

    }
}