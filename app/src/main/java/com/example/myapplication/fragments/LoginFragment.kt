package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.AppViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //Added SharedViewModel Instance
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //init the neww SharedViewModel Instance
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    Snackbar.make(binding.root, "Please fill in all fields", Snackbar.LENGTH_SHORT).show()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.tilEmail.error = "Invalid email format"
                }
                else -> {
                    binding.tilEmail.error = null

                    //Lets make an assumption about the users name such that they enter
                    //Matt@Stclair.ca - The local part is their name
                    val name = email.substringBefore("@").replaceFirstChar { it.uppercaseChar() }
                    //Save data into our new global view model
                    viewModel.setUsername(name)
                    Snackbar.make(binding.root, "Login successful!", Snackbar.LENGTH_SHORT).show()
                    //nav to HomeFragment
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

