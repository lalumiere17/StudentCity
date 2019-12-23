package com.example.studentcity.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.studentcity.R
import com.example.studentcity.models.Router
import com.example.studentcity.models.api.client.hostels.coordinateLoader.CoordinateLoaderCallback
import com.example.studentcity.models.api.client.hostels.coordinateLoader.load
import com.example.studentcity.models.database.Coordinates
import com.example.studentcity.ui.fragments.CreatePassFragment
import com.example.studentcity.ui.fragments.HomeQuestionsFragment
import com.example.studentcity.ui.fragments.ListOfHostelsFragment
import com.example.studentcity.ui.fragments.NewsFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.app_bar.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback
{

    private lateinit var mMap: GoogleMap
    private lateinit var progressBar: ProgressBar
    private lateinit var drawerLayout: DrawerLayout
    private val router = Router(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        showHostels()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return false
    }

    private fun showHostel(title:String, latitude:Double, longitude:Double) {
        mMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(title))
    }

    private fun showMessage(message:String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun showHostels() {
        load(this, object : CoordinateLoaderCallback {
            override fun onSuccess(coordinates: HashMap<String, Coordinates>) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(55.160063, 61.367763),  15f)
                )
                for (coord in coordinates) {
                    showHostel(coord.key, coord.value.latitude, coord.value.longitude)
                }
            }

            override fun onFail() {
                val a = 1
            }
        })
    }
}
