package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // I broke my own rules below with the force
    private val binding
        get() = _binding!!

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)

        //Observe username and update welcome message
        viewModel.username.observe(viewLifecycleOwner) { name ->
            binding.textWelcome.text = "Welcome, $name"
        }

        //Logout button click
        binding.btnLogout.setOnClickListener {
            LogoutDialogFragment {
                findNavController().navigate(R.id.loginFragment)
            }.show(parentFragmentManager, "logoutDialog")
        }

        //Settings button click
        binding.btnSettings.setOnClickListener {
            //Remember Intents from last semester?
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            intent.putExtra("username", viewModel.username.value ?: "User")//Iff fail, default to user
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
