package com.example.studentcity.models.api.client.hostels

import com.example.studentcity.models.database.Hostel
import java.util.ArrayList

abstract class ResponseResult {
    private val isOnline: Boolean = false
    private val isSuccess: Boolean = false
    private val hostels: ArrayList<Hostel> = ArrayList()
}