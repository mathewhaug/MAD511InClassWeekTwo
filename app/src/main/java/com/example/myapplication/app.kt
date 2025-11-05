package com.example.myapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createChannel("background_ops", "Background Operations")
        createChannel("ongoing_ops", "Ongoing Tasks")
    }

    private fun createChannel(id: String, name: String) {
        if (Build.VERSION.SDK_INT >= 26) {
            val mgr = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW)
            mgr.createNotificationChannel(channel)
        }
    }
}
