package com.example.myrealinatagremclonapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myrealinatagremclonapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                binding.message.setText("Home")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search -> {
                binding.message.setText("search")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_add_post -> {
                binding.message.setText(" add post")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                binding.message.setText("notifications")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                binding.message.setText("Profile")
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }
}