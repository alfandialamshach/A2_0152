package com.example.perpustakaan.ui.View.Buku

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
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.ui.ViewModel.Buku.UpdateBukuViewModel
import com.example.perpustakaan.ui.ViewModel.Buku.toBuku
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import kotlinx.coroutines.launch

object DestinasiUpdateBuku : DestinasiNavigasi {
    override val route = "update_buku"
    const val ID_Buku = "id_buku" // Key for the argument
    val routesWithArg = "$route/{$ID_Buku}" // Argument placeholder in the route
    override val titleRes = "Update Buku"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBukuView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Mengambil state dari ViewModel
    val uiState = viewModel.bukuUiState.value
    val kategoriList = viewModel.kategoriList.value
    val penulisList = viewModel.penulisList.value
    val penerbitList = viewModel.penerbitList.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Edit Buku",
                onKategoriClick = {},
                onPenulisClick = {},
                onPenerbitClick = {},
                scrollBehavior = scrollBehavior,
                onRefresh = {},
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu kategori
                isPenulisEnabled = false, // Menonaktifkan menu penulis
                isPenerbitEnabled = false // Menonaktifkan menu penerbit
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
            TambahBodyBuku(
                insertUiState = uiState,
                kategoriList = kategoriList,
                penulisList = penulisList,
                penerbitList = penerbitList,
                onBukuValueChange = { updatedValue ->
                    viewModel.updateBukuState(updatedValue) // Update state ViewModel
                },
                onSimpanClick = {
                    uiState.insertBukuUiEvent?.let { insertBukuUiEvent ->
                        coroutineScope.launch {
                            // Convert InsertBukuUiEvent to Buku object
                            val buku = insertBukuUiEvent.toBuku()

                            // Call ViewModel to update Buku
                            viewModel.updateBuku(buku) // Update buku in repository

                            // Reload data after update
                            viewModel.loadBukuData()

                            // Navigate back after saving
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}
