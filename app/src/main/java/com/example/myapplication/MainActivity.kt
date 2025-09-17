package com.example.myapplication

import android.os.Bundle
import android.util.Patterns
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

     //Viewbinding var
    private lateinit var binding: ActivityMainBinding
    //Keep it simple stew
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Nav Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}