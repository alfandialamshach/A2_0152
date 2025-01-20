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
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import kotlinx.coroutines.launch


object DestinasiTambahPenulis: DestinasiNavigasi {
   override  val route = "item_penulis"
   override  val titleRes = "Tambah Penulis"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahPenulisScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Tambah Penulis",
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
    ) { innerPadding ->
        TambahBodyPenulis(
            insertPenulisUiState = viewModel.penulisuiState,
            onPenulisValueChange = viewModel::updateInsertPenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenulis()
                    navigateBack()
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
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
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
    onValueChange:(InsertPenulisUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPenulisUiEvent.nama_penulis,
            onValueChange = {onValueChange(insertPenulisUiEvent.copy(nama_penulis = it))},
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )



        OutlinedTextField(
            value = insertPenulisUiEvent.biografi,
            onValueChange = {onValueChange(insertPenulisUiEvent.copy(biografi = it))},
            label = { Text("Biografi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPenulisUiEvent.kontak,
            onValueChange = {onValueChange(insertPenulisUiEvent.copy(kontak = it))},
            label = { Text("Kontak") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        if (enabled){
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