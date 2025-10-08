package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemNoteBinding
import com.example.myapplication.model.Note

class NoteAdapter(private var notes: List<Note>) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()){

    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }






    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position) //Fixing to use the list properly
        holder.binding.textTitle.text = note.title
        holder.binding.textContent.text = note.content
    }

    override fun getItemCount(): Int = notes.size

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
