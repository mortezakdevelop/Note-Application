package com.example.noteapplication.room

import androidx.room.TypeConverter
import com.example.noteapplication.model.NoteModels
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class noteTypeConverter {

    @TypeConverter
    fun toJson(noteModels: NoteModels):String?{

        if (noteModels == null){
            return null
        }
            val gson = Gson()
            val type: Type = object : TypeToken<NoteModels?>(){}.getType()
        return gson.toJson(noteModels,type)

    }

    @TypeConverter
    fun toDataClass(note:String?):NoteModels?{
        if(note == null){
            return null
        }
        val gson = Gson()
        val type:Type = object :TypeToken<NoteModels?>(){}.getType()
        return gson.fromJson(note,type)
    }
}