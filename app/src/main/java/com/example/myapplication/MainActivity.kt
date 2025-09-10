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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        //Handle login click
        binding.btnLogin.setOnClickListener {
            //Accesses inner text of var
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    Snackbar.make(binding.root, "Please fill in all fields", Snackbar.LENGTH_SHORT).show()
                }
                //build in android regex checker - super helpful because who actually knows regex by heart
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    //.error class sets red text
                    binding.tilEmail.error = "Invalid email format"
                }
                else -> {
                    binding.tilEmail.error = null
                    Snackbar.make(binding.root, "Login successful!", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}