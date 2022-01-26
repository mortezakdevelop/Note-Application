package com.example.noteapplication.repository

import com.example.noteapplication.model.NoteModels
import com.example.noteapplication.room.AppDatabase
import com.example.noteapplication.room.dao.RoomDao
import com.example.noteapplication.room.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// inject appDataBase in AppRepository and get roomDao in repository
class AppRepository @Inject constructor(
    appDatabase: AppDatabase
) {
    private val roomDao:RoomDao = appDatabase.roomDao()

    fun insertNote(noteModels: NoteModels){
        val noteEntiry = NoteEntity(0,noteModels)
        roomDao.insert(noteEntiry)
    }

    fun getAllData():Flow<List<NoteEntity>>{
        return roomDao.getAll()
    }

    fun updateNote(noteEntity: NoteEntity){
        roomDao.update(noteEntity)
    }
}