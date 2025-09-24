package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {
    //Viewbinding Var
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up ViewBinding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get the username from the Intent Extra
        val username = intent.getStringExtra("username") ?: "User"

        //Display it in the TextView using ViewBinding
        binding.textSettings.text = "Logged in as: $username"
    }
}
