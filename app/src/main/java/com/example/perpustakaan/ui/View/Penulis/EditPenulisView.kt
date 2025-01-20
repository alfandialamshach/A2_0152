package com.example.perpustakaan.ui.View.Penulis


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Penulis.UpdatePenulisViewModel
import com.example.perpustakaan.ui.ViewModel.Penulis.toPenulis
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomTopAppBar

import kotlinx.coroutines.launch

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    const val ID_Penulis = "id_penulis" // Key for the argument
    val routesWithArg = "$route/{$ID_Penulis}"// Argument placeholder in the route
   override val titleRes = "Update Penulis"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateView(

    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    // Collect the UI state from the ViewModel
    val uiState = viewModel.penulisuiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Edit Penulis",
                onKategoriClick = {},
                onPenulisClick = {},
                onPenerbitClick = {},
                scrollBehavior = scrollBehavior,
                onRefresh = {

                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu Dosen
                isPenulisEnabled = false, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = false // Menonaktifkan menu Mata Kuliah
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Pass the UI state to the EntryBody
            TambahBodyPenulis(
                insertPenulisUiState = uiState,
                onPenulisValueChange = { updatedValue ->
                    viewModel.updatePenulisState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    uiState.insertPenulisUiEvent?.let { insertPenulisUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updatePenulis(
                                id_penulis = viewModel.id_penulis, // Pass the NIM from ViewModel
                                penulis = insertPenulisUiEvent.toPenulis() // Convert InsertUiEvent to Mahasiswa

                            )
                            viewModel.ambilPenulis()
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}
