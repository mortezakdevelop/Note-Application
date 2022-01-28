package com.example.noteapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapplication.model.NoteModels
import com.example.noteapplication.repository.AppRepository
import com.example.noteapplication.room.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository
):ViewModel(){

    init {
        getAllFromDb()
    }

    private val data:MutableLiveData<List<NoteEntity>> = MutableLiveData()
    val liveData:LiveData<List<NoteEntity>> = data

    fun insertToNoteModel(noteModels: NoteModels) {
        viewModelScope.launch (Dispatchers.IO) {
            appRepository.insertNote(noteModels)
        }
    }

    fun updateNoteDatabase(noteEntity: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.updateNote(noteEntity)
        }
    }
    fun deleteNote(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.deleteNote(noteEntity)
        }
    }


    fun getAllFromDb(){
        viewModelScope.launch (Dispatchers.IO) {
            appRepository.getAllData().collect {
                data.postValue(it)
            }
        }
    }


}