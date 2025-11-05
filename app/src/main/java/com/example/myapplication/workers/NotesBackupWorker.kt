package com.example.myapplication.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.myapplication.data.CoreDatabase
import com.example.myapplication.data.NoteDao
import com.example.myapplication.model.Note
import com.example.myapplication.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NotesBackupWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Access DAO and repository
            val dao = CoreDatabase.getDatabase(applicationContext).noteDao()
            val repo = NoteRepository(dao)

            // Simulate syncing all notes
            val notes = repo.getAllNotesOnce()

            if (notes.isEmpty()) {
                Log.d("NotesSyncWorker", "No notes to sync.")
            } else {
                notes.forEach { note ->
                    Log.d("NotesSyncWorker", "Synced note: ${note.title}")
                    // TODO iff we have a noteSynced flag; mark note as synced
                }
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("NotesSyncWorker", "Error syncing notes: ${e.message}")
            Result.retry()
        }
    }
}

    // Replace this with how to get your DAO (example shown below)
    private fun getNoteDaoFromYourApp(context: Context): NoteDao {
        return com.example.myapplication.data.CoreDatabase
            .getDatabase(context)
            .noteDao()
    }

