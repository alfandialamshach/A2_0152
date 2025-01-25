package com.example.perpustakaan.ui.Widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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
    BottomAppBar(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentPadding = PaddingValues(horizontal = 16.dp),
        tonalElevation = 4.dp
    ) {
        // Back Button
        if (isBackEnabled) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Spacer to adjust layout when Back button is hidden
        if (isBackEnabled) {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Home Button
        if (isHomeEnabled) {
            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Spacer to adjust layout when Home button is hidden
        if (isHomeEnabled) {
            Spacer(modifier = Modifier.weight(1f))
        }

//

        // Add Data Button
        if (isAddDataEnabled) {
            IconButton(
                onClick = onAddDataClick,
                modifier = Modifier
                    .size(72.dp) // Ukuran tombol (default IconButton adalah 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Data",
                    modifier = Modifier.size(48.dp), // Ukuran ikon di dalam tombol
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

// Spacer to adjust layout when Add Data button is hidden
        if (isAddDataEnabled) {
            Spacer(modifier = Modifier.weight(1f))
        }


        // Profile Button
        if (isProfileEnabled) {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
