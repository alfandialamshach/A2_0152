package com.example.perpustakaan.ui.View.Penerbit


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
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitUiEvent
import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitUiState
import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitViewModel
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import kotlinx.coroutines.launch


object DestinasiTambahPenerbit: DestinasiNavigasi {
    override  val route = "item_penerbit"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahPenerbitScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    onProfilClick: () -> Unit,
    onBackClick: () -> Unit,
    onPenulisClick: () -> Unit,
    onKategoriClick: () -> Unit,
    viewModel: InsertPenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Tambah Penerbit",
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
                onProfileClick = onProfilClick,
                onAddDataClick = {}, // Navigate to item entry when Add Data is clicked
                onBackClick = onBackClick // Handle Back click action
            )
        },
    ) { innerPadding ->
        TambahBodyPenerbit(
            insertPenerbitUiState = viewModel.penerbituiState,
            errorMessage = viewModel.errorMessage,
            onPenerbitValueChange = viewModel::updateInsertPenerbitState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenerbit()
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
fun TambahBodyPenerbit(
    insertPenerbitUiState: InsertPenerbitUiState,
    onPenerbitValueChange: (InsertPenerbitUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPenerbit(
            insertPenerbitUiEvent = insertPenerbitUiState.insertPenerbitUiEvent,
            onValueChange = onPenerbitValueChange,
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun FormInputPenerbit(
    insertPenerbitUiEvent: InsertPenerbitUiEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertPenerbitUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPenerbitUiEvent.nama_penerbit,
            onValueChange = {onValueChange(insertPenerbitUiEvent.copy(nama_penerbit = it))},
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPenerbitUiEvent.telepon_penerbit,
            onValueChange = {onValueChange(insertPenerbitUiEvent.copy(telepon_penerbit = it))},
            label = { Text("Telepon Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPenerbitUiEvent.alamat_penerbit,
            onValueChange = {onValueChange(insertPenerbitUiEvent.copy(alamat_penerbit = it))},
            label = { Text("Alamat") },
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