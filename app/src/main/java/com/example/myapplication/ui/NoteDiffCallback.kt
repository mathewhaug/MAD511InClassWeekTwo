package com.example.myapplication.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.model.Note

//Used to setup diffutil
class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}