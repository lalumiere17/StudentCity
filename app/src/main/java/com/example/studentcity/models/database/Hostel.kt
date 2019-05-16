package com.example.studentcity.models.database

import android.text.TextUtils

import java.io.Serializable
import java.util.ArrayList

data class Hostel(
    val title: String,
    val coordinates: Coordinates,
    val address: String,
    val photo: String,
    val phone: String,

    val numberFloors: Int,
    val numberStudents: Int,
    val rating: Double,

    val stuffs: ArrayList<Stuff>,
    val residents: ArrayList<Resident>
    ) : Serializable {

    companion object {
        val SERIALIZABLE_KEY = "HOSTEL"
    }
}

//data class Hostel(
//    val title:String,
//) : Serializable {
//
//    val coordinates: Coordinates
//    val title: String
//    var photo: String? = null
//        private set
//    var address: String? = null
//        private set
//    var phone: String? = null
//        private set
//
//    val numberFloors: Int
//    var numberStudents: Int = 0
//        private set
//    val rating: Double
//
//    val stuffs: ArrayList<Stuff>?
//    val residents: ArrayList<Resident>
//
//    init {
//        coordinates = Coordinates()
//        address = null
//        phone = address
//        photo = phone
//        title = title
//        numberStudents = 0
//        numberFloors = numberStudents
//        rating = 0.0
//
//        stuffs = ArrayList<Stuff>()
//        residents = ArrayList<Resident>()
//    }
//
//    fun getStuff(post: String): Stuff? {
//        if (TextUtils.isEmpty(post))
//            return null
//
//        if (stuffs == null) return null
//
//        for (stuff in stuffs) {
//            if (stuff.post?.title.equals(post))
//                return stuff
//        }
//
//        return null
//    }
//
//
//}