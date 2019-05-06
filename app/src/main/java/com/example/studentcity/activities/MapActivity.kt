package com.example.studentcity.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import com.example.studentcity.R
import com.example.studentcity.models.database.Coordinates
import com.example.studentcity.models.database.Hostel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
                    NavigationView.OnNavigationItemSelectedListener{

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var intent : Intent = Intent()

        var hostel : Hostel = intent.getSerializableExtra(Hostel.SERIALIZABLE_KEY) as Hostel
        if(hostel == null) {
            // TODO: вывод всех общежитий на экран
        }
        else {
            var coordinates : Coordinates = hostel.coordinates
            var hostelPosition : LatLng = LatLng(coordinates.latitude, coordinates.longitude)

            mMap.addMarker(MarkerOptions().position(hostelPosition).title(hostel.title))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hostelPosition))
        }
    }

    override fun onBackPressed() {
        var drawer_layout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id : Int = item.itemId

        var drawer_layout : DrawerLayout = findViewById(R.id.drawer_layout)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
