package com.example.noteapplication.room.dao

import androidx.room.*
import com.example.noteapplication.room.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Query("SELECT * FROM note_table")
    fun getAll(): Flow<List<NoteEntity>>

    @Insert
    fun insert(noteEntity: NoteEntity)


    @Delete
    fun delete(noteEntity: NoteEntity)


    @Update
    fun update(noteEntity: NoteEntity)

}
