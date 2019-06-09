package com.example.studentcity.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.example.studentcity.R
import com.example.studentcity.models.Router
import com.example.studentcity.ui.fragments.*
import kotlinx.android.synthetic.main.app_bar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var drawerLayout: DrawerLayout
    private val router = Router(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        progressBar = findViewById(R.id.progress_bar)

        router.startFragment(ListOfHostelsFragment(), null)
    }

    override fun onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
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

    fun showProgress(){
        if(progressBar.visibility != View.VISIBLE)
            progressBar.visibility = View.VISIBLE
    }

    fun hideProgress(){
        if(progressBar.visibility != View.INVISIBLE)
            progressBar.visibility = View.INVISIBLE
    }
}
