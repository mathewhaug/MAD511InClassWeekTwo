package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.data.CoreDatabase
import com.example.myapplication.model.Note
import com.example.myapplication.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init { //Setting up the data base for the first time
        val dao = CoreDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)
        //Getting all data as a Flow so we get a stream of data
        allNotes = repository.allNotes.asLiveData()
    }
    //Kotlin coroutine - Remember I told you itd be useful
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}
