package com.example.studentcity.models

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

import com.example.studentcity.R

class Router {

    private var fragmentManager: FragmentManager? = null
    private var activity: Activity? = null

    private val currentFragment: Fragment?

    constructor(fragment: Fragment) {
        this.fragmentManager = fragment
            .activity!!
            .supportFragmentManager

        this.currentFragment = fragment

        this.activity = fragment.activity
    }

    constructor(appCompatActivity: AppCompatActivity) {
        this.activity = appCompatActivity
        this.fragmentManager = appCompatActivity.supportFragmentManager
    }

    fun startFragment(newFragment: Fragment, args: Bundle?) {
        if (args != null)
            newFragment.arguments = args

        fragmentManager!!.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(R.id.fragment_container, newFragment)
            .commit()
    }

    fun showFragment(newFragment: Fragment, args: Bundle?) {
        if (currentFragment != null && currentFragment.javaClass == newFragment.javaClass) return

        if (args != null)
            newFragment.arguments = args

        fragmentManager!!.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.fragment_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    fun showFragmentGone(newFragment: Fragment, args: Bundle?) {
        if (currentFragment != null && currentFragment.javaClass == newFragment.javaClass) return

        if (args != null)
            newFragment.arguments = args

        fragmentManager!!.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.fragment_container, newFragment)
            .commit()
    }

    fun clearFragment() {
        if (currentFragment != null)
            fragmentManager!!.beginTransaction()
                .remove(currentFragment)
                .commit()
    }

    fun redirectToCallForward(phoneNumber: String) {
        val intentCallForward = Intent(Intent.ACTION_DIAL)
        val uriPhone = Uri.parse("tel:$phoneNumber")
        intentCallForward.data = uriPhone
        activity!!.startActivity(intentCallForward)
    }
}