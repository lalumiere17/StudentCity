package com.example.studentcity.ui.fragments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import com.example.studentcity.R
import kotlinx.android.synthetic.main.fragment_create_pass.*
import kotlinx.android.synthetic.main.pass_fragment.*

class PassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pass_fragment)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        fullname.text = getString(R.string.fullname)
        roomNumber.text = getString(R.string.roomNumberTxt)
        faculty.text = getString(R.string.facultyTxt)
        expiredDate.text = getString(R.string.expiredDate)
    }
}