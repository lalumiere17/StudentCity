package com.example.studentcity.models.database

import java.io.Serializable

class Post : Serializable {

    private val id: Int = 0
    val title: String? = null

    companion object {

        val MAIN_HOSTEL_MANAGER_POST = ""
        val MAIN_STUDENT_MANAGER_POST = ""
        val MAIN_CULTURE_MANAGER_POST = ""
        val MAIN_SPORT_MANAGER_POST = ""

        val HOSTEL_MANAGER_POST = "Заведующий"
        val STUDENT_MANAGER_POST = "Председатель"
        val CULTURE_MANAGER_POST = "Культорг"
        val SPORT_MANAGER_POST = "Спорторг"
    }
}