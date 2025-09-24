package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    //Logically see that _username is private with the inderscore
    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    fun setUsername(name: String) {
        _username.value = name
    }
}