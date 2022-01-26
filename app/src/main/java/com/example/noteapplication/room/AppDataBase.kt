package com.example.noteapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapplication.room.dao.RoomDao
import com.example.noteapplication.room.entity.NoteEntity

@TypeConverters(noteTypeConverter::class)
@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao

}

