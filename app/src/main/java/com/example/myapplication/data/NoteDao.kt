package com.example.myapplication.data

import com.example.myapplication.model.Note
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    //This defines the SQL query we need to perform
    @Query("SELECT * FROM notes ORDER BY id DESC")
    //This allows room to stream updates to the UI reactively
    fun getAllNotes(): Flow<List<Note>>

    //DATA CRUDS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //Setup to run in coroutine off of main
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
    //Seting up for background work
    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotesOnce(): List<Note>

}
