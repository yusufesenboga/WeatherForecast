package com.agobnese.weatherApp.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.agobnese.weatherApp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Weather Forecast"
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initializeToolbar()

    }

    private fun initializeToolbar() {
        val host = supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag) as NavHostFragment
        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }
}