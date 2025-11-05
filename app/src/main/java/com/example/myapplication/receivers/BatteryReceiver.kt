package com.example.myapplication.receivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.work.WorkManager
class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Cancel the nightly notes backup
        WorkManager.getInstance(context).cancelAllWorkByTag("notes_backup_daily")

        Toast.makeText(context, "Battery low — notes backup paused", Toast.LENGTH_LONG).show()
    }
}
