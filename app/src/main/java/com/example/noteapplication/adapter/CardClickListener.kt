package com.example.noteapplication.adapter

import android.view.View
import androidx.constraintlayout.utils.widget.ImageFilterButton
import com.example.noteapplication.room.entity.NoteEntity

interface CardClickListener {
    fun onItemRVClickListener(noteEntity: NoteEntity){

    }

    fun onMenuItemRVClickListener(imageFilterButton: View,noteEntity: NoteEntity){

    }
}