package com.rucech.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.rucech.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Thread.sleep(1700)
        setTheme(R.style.Theme_MyApplication)

        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)




        binding.actionGotoDrawerActivity.setOnClickListener {
            startActivity(Intent(this, DrawerActivity::class.java))
        }
        binding.actionGotoBottomActivity.setOnClickListener {
            startActivity(Intent(this, BottomActivity::class.java))
        }

    }
}