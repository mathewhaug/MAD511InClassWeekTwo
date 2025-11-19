package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.AppViewModel
import com.example.myapplication.R
import com.example.myapplication.SettingsActivity

class HomeFragment : Fragment() {

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    val username by viewModel.username.observeAsState("User")

                    HomeScreen(
                        username = username,
                        onLogoutClick = {
                            LogoutDialogFragment {
                                findNavController().navigate(R.id.loginFragment)
                            }.show(parentFragmentManager, "logoutDialog")
                        },
                        onSettingsClick = {
                            val intent = Intent(requireContext(), SettingsActivity::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
                        },
                        onNotesClick = {
                            findNavController().navigate(R.id.action_homeFragment_to_notesFragment)
                        },
                        onFlightTrackerClick = {
                            findNavController().navigate(R.id.action_homeFragment_to_flightSearchFragment)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    username: String,
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onNotesClick: () -> Unit,
    onFlightTrackerClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {

            // Welcome Text
            Text(
                text = "Welcome, $username",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            //  UPDATED ROW: Added third tile for "Crash App" (Crashlytics test)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {

                // NOTES tile
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                        .height(120.dp)
                        .clickable { onNotesClick() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Notes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // FLIGHT TRACKER tile
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .height(120.dp)
                        .clickable { onFlightTrackerClick() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Flight Tracker",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Coming Soon to you",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                // NEW CRASH TILE triggers Firebase Crashlytics crash

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                        .height(120.dp)
                        .clickable {
                            // The actual intentional crash
                            throw RuntimeException("Test crash triggered from Crashlytics tile")
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFCDD2) // light red tile
                    )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Crash App",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Crashlytics Test",
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            // Settings Button
            Button(
                onClick = onSettingsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Settings")
            }

            // Logout Button
            Button(
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout", color = Color.White)
            }
        }
    }
}
