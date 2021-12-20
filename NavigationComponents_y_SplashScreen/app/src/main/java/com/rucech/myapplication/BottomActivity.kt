package com.rucech.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.NavigationUI
import com.rucech.myapplication.databinding.ActivityBottomBinding


class BottomActivity : AppCompatActivity() {

    lateinit var binding: ActivityBottomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ActivityBottomBinding.inflate(layoutInflater).also { binding = it }.root)


        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!.navController

        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
    }
}