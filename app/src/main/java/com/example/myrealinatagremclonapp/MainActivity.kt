package com.example.myrealinatagremclonapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myrealinatagremclonapp.Fragments.HomeFragment
import com.example.myrealinatagremclonapp.Fragments.NotificationFragment
import com.example.myrealinatagremclonapp.Fragments.ProfileFragment
import com.example.myrealinatagremclonapp.Fragments.SearchFragment
import com.example.myrealinatagremclonapp.databinding.ActivityMainBinding
import com.example.myrealinatagremclonapp.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    internal var selectedFragments: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            HomeFragment()
        ).commit()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {

                selectedFragments = HomeFragment()
            }
            R.id.nav_search -> {

                selectedFragments = SearchFragment()
            }
            R.id.nav_add_post -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {

                selectedFragments = NotificationFragment()
            }
            R.id.nav_profile -> {

                selectedFragments = ProfileFragment()
            }
        }
        if(selectedFragments != null){
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragments!!
            ).commit()
        }
        false
    }
}