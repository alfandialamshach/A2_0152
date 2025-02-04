package com.example.perpustakaan.ui.View.Penerbit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Penerbit.UpdatePenerbitViewModel
import com.example.perpustakaan.ui.ViewModel.Penerbit.toPenerbit
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import kotlinx.coroutines.launch


object DestinasiUpdateTerbit : DestinasiNavigasi {
    override val route = "update_penerbit"
    const val ID_Penerbit = "id_penerbit" // Key for the argument
    val routesWithArg = "$route/{$ID_Penerbit}"// Argument placeholder in the route
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTerbitView(
    onProfilClick: () -> Unit,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit = {},
    onPenulisClick: () -> Unit,
    onKategoriClick: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    // Collect the UI state from the ViewModel
    val penerbituiState = viewModel.penerbituiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Edit Penerbit",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = {},
                scrollBehavior = scrollBehavior,
                onRefresh = {

                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu Dosen
                isPenulisEnabled = true, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = false // Menonaktifkan menu Mata Kuliah
            )
        },
        bottomBar = {
            CustomBottomAppBar(
                isHomeEnabled = false,
                onHomeClick = {},
                onProfileClick = onProfilClick,
                onAddDataClick = onAddClick, // Navigate to item entry when Add Data is clicked
                onBackClick = onBackClick// Handle Back click action
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Pass the UI state to the EntryBody
            TambahBodyPenerbit(
                insertPenerbitUiState = penerbituiState,
                errorMessage = viewModel.errorMessage,
                onPenerbitValueChange = { updatedpValue ->
                    viewModel.updatePenerbitState(updatedpValue) // Update ViewModel state
                },
                onSaveClick = {
                    penerbituiState.insertPenerbitUiEvent?.let { insertPenerbitUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updatePenerbit(
                                id_penerbit = viewModel.id_penerbit, // Pass the NIM from ViewModel
                                penerbit = insertPenerbitUiEvent.toPenerbit() // Convert InsertUiEvent to Mahasiswa

                            )
                            viewModel.ambilPenerbit()
                            if (viewModel.errorMessage.isEmpty()) {
                                navigateBack()
                            }
                        }
                    }
                }
            )
        }
    }
}
