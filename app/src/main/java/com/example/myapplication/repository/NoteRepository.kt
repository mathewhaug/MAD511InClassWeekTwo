package com.example.myapplication.repository

import com.example.myapplication.data.NoteDao
import com.example.myapplication.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {

    val allNotes: Flow<List<Note>> = dao.getAllNotes()

    suspend fun insert(note: Note) {
        dao.insert(note)
    }

    suspend fun delete(note: Note) {
        dao.delete(note)
    }
}
