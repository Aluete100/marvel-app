package com.alan.intermediatest.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.alan.intermediatest.R
import com.alan.intermediatest.ui.views.auth.AuthFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpNavigation()
        setUpNavigationListeners()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(app_toolbar)
        setupActionBarWithNavController(navController) //Connecting toolbar with navController

        //Initializing bottom navigation view and NavController
        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.itemIconTintList = null

        //Add navigation view and nav controller to NavigationUI
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun setUpNavigationListeners() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.charactersFragment -> {
                    showBottomNav()
                    activity_title.text = getString(R.string.toolbar_title)
                }
                R.id.eventsFragment -> {
                    showBottomNav()
                    activity_title.text = getString(R.string.toolbar_title)
                }
                else -> hideBottomNav()
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.character_menu_section -> {
                    navController.navigate(R.id.charactersFragment)
                }
                R.id.event_menu_section -> {
                    navController.navigate(R.id.eventsFragment)
                }
            }
            true
        }
    }

    private fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

        if (currFragment is AuthFragment) {
            currFragment.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}