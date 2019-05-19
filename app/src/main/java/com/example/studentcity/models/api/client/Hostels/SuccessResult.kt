package com.example.studentcity.models.api.client.Hostels

import com.example.studentcity.models.database.Hostel

class SuccessResult(val hostels: Array<Hostel>) : ResponseResult() {

}