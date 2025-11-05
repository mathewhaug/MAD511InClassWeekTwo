package com.example.myapplication.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.work.*
import com.example.myapplication.workers.NotesBackupWorker


class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active = cm.activeNetworkInfo
        val isConnected = active?.isConnected == true

        if (isConnected) {
            val syncRequest = OneTimeWorkRequestBuilder<NotesBackupWorker>()
                .setConstraints(Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build())
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, java.util.concurrent.TimeUnit.SECONDS)
                .addTag("notes_sync_retry")
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "notes_sync_retry",
                ExistingWorkPolicy.REPLACE,
                syncRequest
            )
        }
    }
}
