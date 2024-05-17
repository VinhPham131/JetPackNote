package com.midterm.jetpacknote.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midterm.jetpacknote.data.NoteRepository
import com.midterm.jetpacknote.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            repository.getNotes().distinctUntilChanged()
                .collect {
                    listOfNotes ->
                    if(listOfNotes.isNullOrEmpty()){
                        Log.d("Debug", "Null or Empty")
                    }else{
                        _noteList.value = listOfNotes
                    }
                }
        }
    }

    fun addNote(note : Note) = viewModelScope.launch() {
        repository.addNote(note)
    }
    fun updateNote(note : Note) = viewModelScope.launch() {
        repository.updateNote(note)
    }
    fun removeNote(note : Note) = viewModelScope.launch() {
        repository.deleteNote(note)
    }
    fun removeAllNotes() = viewModelScope.launch() {
        repository.deleteAllNotes()
    }
}