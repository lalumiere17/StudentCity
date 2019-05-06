package com.example.studentcity.models.database

import java.io.Serializable

open class Human : Serializable {
    private val id: Int = 0
    val surname: String? = null
    val firstname: String? = null
    val patronymic: String? = null
}