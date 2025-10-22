package com.example.myapplication.Network

//out t means it can output a value of type t | t is a covarient ex: NetworkResult<List<User>> OR NetowrkResult<List<Flight>> are both valid
sealed class NetworkResult<out T> { //All sub classes below with genaric return. This way we can have any thing returned
    data class Success<out T>(val data: T): NetworkResult<T>()
    data class Error(val message: String, val code: Int? = null): NetworkResult<Nothing>()
    object Loading: NetworkResult<Nothing>()
}

/*
data class success is a class that will wrapp a success result such that the (val data: T) will hold the
the API response and it will inherit from the sealed class

Loading state is defined as a singleton object as it cannot hold any data
 */
