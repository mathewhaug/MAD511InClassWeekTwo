package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    //Create a private var for AppViewModel again
    private lateinit var viewModel: AppViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            LogoutDialogFragment {
                // nav back to LoginFragment when confirmed
                findNavController().navigate(R.id.loginFragment)
            }.show(parentFragmentManager, "logoutDialog")
        }
        // Ref a textview
        val welcomeText = view.findViewById<TextView>(R.id.textWelcome)

        //Init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)

        //Observe username LiveData and update UI
        viewModel.username.observe(viewLifecycleOwner) { name ->
            welcomeText.text = "Welcome, $name"
        }

        return view
    }
}
