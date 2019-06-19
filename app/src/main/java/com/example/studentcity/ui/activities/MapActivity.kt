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

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
                    NavigationView.OnNavigationItemSelectedListener{

    private lateinit var mMap: GoogleMap
    private lateinit var progressBar: ProgressBar
    private lateinit var drawerLayout: DrawerLayout
    private val router = Router(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.title_activity_map)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val mapFragment:SupportMapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        showHostels()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_hostels -> {
                router.showFragmentGone(ListOfHostelsFragment(), null)
            }
            R.id.menu_news -> {
                router.showFragmentGone(NewsFragment(), null)
            }
            R.id.menu_pass -> {
                router.showFragmentGone(CreatePassFragment(), null)
            }
            R.id.menu_map -> {
                startActivity(Intent(this, MapActivity::class.java))
            }
            R.id.menu_home_questions -> {
                router.showFragmentGone(HomeQuestionsFragment(), null)
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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
