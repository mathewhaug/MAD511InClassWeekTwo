package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentNotesBinding
import com.example.myapplication.model.Note
import com.example.myapplication.ui.HeaderAdapter
import com.example.myapplication.ui.NoteAdapter
import com.example.myapplication.viewmodel.NoteViewModel

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel: NoteViewModel by viewModels()

    // New Note Adpaters inherting same NoteAdapter
    private lateinit var pinnedAdapter: NoteAdapter
    private lateinit var otherAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Create adapters for both sections
        pinnedAdapter = NoteAdapter(emptyList())
        otherAdapter = NoteAdapter(emptyList())

        //Combine adapters using ConcatAdapter
        val concatAdapter = ConcatAdapter(
            HeaderAdapter("Pinned Notes"), //Use the HEader Adapter
            pinnedAdapter,
            HeaderAdapter("Other Notes"),
            otherAdapter
        )
        binding.recyclerViewNotes.adapter = concatAdapter

        //Set up RecyclerView
        binding.recyclerViewNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter //MAke sure to set it as ConCAt Adapter
        }

        //Observe notes and split into sections
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            val pinned = notes.filter { it.isPinned } //isPinned is the new property we added
            val others = notes.filter { !it.isPinned }

            pinnedAdapter.submitList(pinned)
            otherAdapter.submitList(others)
        }

        //Add a dummy note with random pinned state
        binding.btnAddNote.setOnClickListener {
            val newNote = Note(
                title = "Note ${System.currentTimeMillis()}",
                content = "This is a new note.",
                isPinned = System.currentTimeMillis() % 2 == 0L //half pinned, half not
            )
            noteViewModel.insert(newNote)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
