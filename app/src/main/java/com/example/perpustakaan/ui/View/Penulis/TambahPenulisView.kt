package com.example.perpustakaan.ui.View.Penulis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisUiEvent
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisUiState
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisViewModel
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import kotlinx.coroutines.launch


object DestinasiTambahPenulis: DestinasiNavigasi {
   override  val route = "item_penulis"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahPenulisScreen(
    navigateBack: () -> Unit,
    onProfilClick: () -> Unit,
    onBackClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    onKategoriClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Tambah Penulis",
                onKategoriClick = onKategoriClick,
                onPenulisClick = {},
                onPenerbitClick = onPenerbitClick,
                scrollBehavior = scrollBehavior,
                onRefresh = {},
                isMenuEnabled = true,
                isKategoriEnabled = true,
                isPenulisEnabled = false,
                isPenerbitEnabled = true
            )
        },
        bottomBar = {
            CustomBottomAppBar(
                isHomeEnabled = false,
                onProfileClick = onProfilClick,
                onAddDataClick = {}, // Navigate to item entry when Add Data is clicked
                onBackClick = onBackClick // Handle Back click action
            )
        },
    ) { innerPadding ->
        TambahBodyPenulis(
            insertPenulisUiState = viewModel.penulisuiState,
            errorMessage = viewModel.errorMessage,
            onPenulisValueChange = viewModel::updateInsertPenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenulis()
                    if (viewModel.errorMessage.isEmpty()) {
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun TambahBodyPenulis(
    insertPenulisUiState: InsertPenulisUiState,
    errorMessage: String,
    onPenulisValueChange: (InsertPenulisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPenulis(
            insertPenulisUiEvent = insertPenulisUiState.insertPenulisUiEvent,
            onValueChange = onPenulisValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        // Display error message if any
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),

        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPenulis(
    insertPenulisUiEvent: InsertPenulisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPenulisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPenulisUiEvent.nama_penulis,
            onValueChange = { onValueChange(insertPenulisUiEvent.copy(nama_penulis = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPenulisUiEvent.biografi,
            onValueChange = { onValueChange(insertPenulisUiEvent.copy(biografi = it)) },
            label = { Text("Biografi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPenulisUiEvent.kontak,
            onValueChange = { onValueChange(insertPenulisUiEvent.copy(kontak = it)) },
            label = { Text("Kontak") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
