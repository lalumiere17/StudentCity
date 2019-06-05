package com.example.studentcity.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import com.example.studentcity.R
import com.example.studentcity.models.api.client.hostels.DownloadListOfHostelsCallback
import com.example.studentcity.models.api.client.hostels.LoaderOfHostels
import com.example.studentcity.models.database.Coordinates
import com.example.studentcity.models.database.Hostel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.app_bar.*
import java.lang.Exception

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
                    NavigationView.OnNavigationItemSelectedListener{

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment:SupportMapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        try {
            val hostel:Hostel = intent.getSerializableExtra(Hostel.SERIALIZABLE_KEY) as Hostel
            showHostel(hostel)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                LatLng(
                    hostel.coordinates.latitude,
                    hostel.coordinates.longitude
                )
            ))
        }
        catch(e:Exception) {
            showHostels()
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id : Int = item.itemId

        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showHostel(hostel:Hostel) {
        val coordinates : Coordinates = hostel.coordinates
        val hostelPosition = LatLng(coordinates.latitude, coordinates.longitude)

        mMap.addMarker(MarkerOptions().position(hostelPosition).title(hostel.title))
    }

    private fun showMessage(message:String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun showHostels() {
        val loaderOfHostels = LoaderOfHostels()
        loaderOfHostels.download(this, object:DownloadListOfHostelsCallback{
            override fun onServerError() {
                showMessage(getString(R.string.error_downloading_data))
            }

            override fun onFailInternetConnection() {
                showMessage(getString(R.string.internet_connection_is_messed))
            }

            override fun onSuccess(hostels: Array<Hostel>) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(55.160063, 61.367763),  15f)
                )
                for (hostel in hostels) {
                    showHostel(hostel)
                }
            }
        })
    }
}
