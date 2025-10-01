package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentNotesBinding
import com.example.myapplication.model.Note
import com.example.myapplication.ui.NoteAdapter
import com.example.myapplication.viewmodel.NoteViewModel

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!


    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up RecyclerView
        noteAdapter = NoteAdapter(emptyList())
        binding.recyclerViewNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }

        // Observe notes from ViewModel
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            noteAdapter.updateNotes(notes)
        }

        // Add a dummy note on button click
        binding.btnAddNote.setOnClickListener {
            val newNote =
                Note(title = "Note ${System.currentTimeMillis()}", content = "This is a new note.")
            noteViewModel.insert(newNote)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
