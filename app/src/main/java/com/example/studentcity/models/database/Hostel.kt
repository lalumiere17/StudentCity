package com.example.studentcity.models.database

import android.text.TextUtils

import java.io.Serializable
import java.util.ArrayList

class Hostel : Serializable {

    val coordinates: Coordinates
    val title: String
    var photo: String? = null
        private set
    var address: String? = null
        private set
    var phone: String? = null
        private set

    val numberFloors: Int
    var numberStudents: Int = 0
        private set
    val rating: Double

    val stuffs: ArrayList<Stuff>?
    val residents: ArrayList<Resident>

    init {
        coordinates = Coordinates()
        address = null
        phone = address
        photo = phone
        title = photo
        numberStudents = 0
        numberFloors = numberStudents
        rating = 0.0

        stuffs = ArrayList<Stuff>()
        residents = ArrayList<Resident>()
    }

    fun getStuff(post: String): Stuff? {
        if (TextUtils.isEmpty(post))
            return null

        if (stuffs == null) return null

        for (stuff in stuffs) {
            if (stuff.getPost().getTitle().equals(post))
                return stuff
        }

        return null
    }

    companion object {
        val SERIALIZABLE_KEY = "HOSTEL"
    }
}