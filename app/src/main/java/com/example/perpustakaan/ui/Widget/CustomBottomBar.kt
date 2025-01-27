package com.example.perpustakaan.ui.Widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomAppBar(
    onHomeClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onAddDataClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    isBackEnabled: Boolean = true,    // Enable/Disable Back icon
    isHomeEnabled: Boolean = true,    // Enable/Disable Home icon
    isAddDataEnabled: Boolean = true, // Enable/Disable Add Data icon
    isProfileEnabled: Boolean = true, // Enable/Disable Profile icon
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        BottomAppBar(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary),
            contentPadding = PaddingValues(horizontal = 16.dp),
            tonalElevation = 8.dp
        ) {
            // Conditional rendering based on Home and Back button states
            if (isHomeEnabled) {
                // Home Button (di sisi paling kiri jika Home diaktifkan)
                IconButton(onClick = onHomeClick) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(40.dp)
                    )
                }
            } else if (isBackEnabled) {
                // Back Button (di sisi paling kiri jika Home tidak aktif)
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(40.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp)) // Placeholder
            }

            Spacer(modifier = Modifier.weight(1f)) // This keeps the Profile button aligned to the right

            // Profile Button (di sisi kanan)
            if (isProfileEnabled) {
                IconButton(onClick = onProfileClick) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        // Floating Action Button (FAB) for Add Data (di tengah)
        if (isAddDataEnabled) {
            FloatingActionButton(
                onClick = onAddDataClick,
                containerColor = Color(0xFF1976D2), // Set container color to blue
                contentColor = Color.White, // Set content color (icon) to white
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -28.dp) // Adjust FAB position
                    .size(72.dp) // Ukuran FAB sesuai kebutuhan
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Data",
                    tint = Color.White
                )
            }
        }
    }
}
