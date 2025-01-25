package com.example.perpustakaan.ui.View.Buku

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuUiEvent
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuUiState
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuViewModel
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.DropdownField
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object DestinasiTambahBuku : DestinasiNavigasi {
    override val route = "item_entry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahBukuScreen(
    navigateBack: () -> Unit,
    onProfilClick: () -> Unit,
    onBackClick: () -> Unit,
    onPenulisClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    onKategoriClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Tambah Buku",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = onPenerbitClick,
                scrollBehavior = scrollBehavior,
                onRefresh = {},
                isMenuEnabled = true,
                isKategoriEnabled = true,
                isPenulisEnabled = true,
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
        TambahBodyBuku(
            insertUiState = viewModel.bukuUiState,
            errorMessage = viewModel.errorMessage,
            onBukuValueChange = viewModel::updateInsertBukuState,
            onSimpanClick = {
                coroutineScope.launch {
                    viewModel.insertBuku()
                    if (viewModel.errorMessage.isEmpty()) {
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            kategoriList = viewModel.kategoriList,
            penulisList = viewModel.penulisList,
            penerbitList = viewModel.penerbitList
        )
    }
}

@Composable
fun TambahBodyBuku(
    insertUiState: InsertBukuUiState,
    onBukuValueChange: (InsertBukuUiEvent) -> Unit,
    onSimpanClick: () -> Unit,
    errorMessage: String,
    modifier: Modifier = Modifier,
    kategoriList: List<Kategori>,
    penulisList: List<Penulis>,
    penerbitList: List<Penerbit>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputBuku(
            insertBukuUiEvent = insertUiState.insertBukuUiEvent,
            onValueChange = onBukuValueChange,
            modifier = Modifier.fillMaxWidth(),
            kategoriList = kategoriList,
            penulisList = penulisList,
            penerbitList = penerbitList
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
        }
        Button(
            onClick = onSimpanClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInputBuku(
    insertBukuUiEvent: InsertBukuUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertBukuUiEvent) -> Unit = {},
    kategoriList: List<Kategori>,
    penulisList: List<Penulis>,
    penerbitList: List<Penerbit>
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val selectedDate = remember { mutableStateOf(insertBukuUiEvent.tanggal_terbit ?: Date()) }

    var kategoriExpanded by remember { mutableStateOf(false) }
    var penulisExpanded by remember { mutableStateOf(false) }
    var penerbitExpanded by remember { mutableStateOf(false) }

    // Pilihan status buku
    val statusBukuOptions = listOf("Tersedia", "Habis", "Dipesan")
    val selectedStatusIndex = remember { mutableStateOf(insertBukuUiEvent.status_buku) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertBukuUiEvent.nama_buku,
            onValueChange = { onValueChange(insertBukuUiEvent.copy(nama_buku = it)) },
            label = { Text("Nama Buku") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertBukuUiEvent.deskripsi_buku,
            onValueChange = { onValueChange(insertBukuUiEvent.copy(deskripsi_buku = it)) },
            label = { Text("Deskripsi Buku") },
            modifier = Modifier.fillMaxWidth()
        )

        // RadioButton untuk status buku
        // RadioButton untuk status buku
        Text("Status Buku")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Tambahkan jarak antar elemen
            modifier = Modifier.fillMaxWidth()
        ) {
            statusBukuOptions.forEach { status ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedStatusIndex.value == status,
                        onClick = {
                            selectedStatusIndex.value = status
                            onValueChange(insertBukuUiEvent.copy(status_buku = status))
                        }
                    )
                    Text(
                        text = status,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }


        // Dropdown untuk Kategori
        DropdownField(
            label = "Kategori",
            items = kategoriList.map { it.nama_kategori },
            selectedItem = kategoriList.find { it.id_kategori == insertBukuUiEvent.id_kategori }?.nama_kategori
                ?: "",
            onItemSelected = { index ->
                kategoriList.getOrNull(index)?.let {
                    onValueChange(insertBukuUiEvent.copy(id_kategori = it.id_kategori))
                }
            },
            expanded = kategoriExpanded,
            onExpandedChange = { kategoriExpanded = it }
        )

        // Dropdown untuk Penulis
        DropdownField(
            label = "Penulis",
            items = penulisList.map { it.nama_penulis },
            selectedItem = penulisList.find { it.id_penulis == insertBukuUiEvent.id_penulis }?.nama_penulis
                ?: "",
            onItemSelected = { index ->
                penulisList.getOrNull(index)?.let {
                    onValueChange(insertBukuUiEvent.copy(id_penulis = it.id_penulis))
                }
            },
            expanded = penulisExpanded,
            onExpandedChange = { penulisExpanded = it }
        )

        // Dropdown untuk Penerbit
        DropdownField(
            label = "Penerbit",
            items = penerbitList.map { it.nama_penerbit },
            selectedItem = penerbitList.find { it.id_penerbit == insertBukuUiEvent.id_penerbit }?.nama_penerbit
                ?: "",
            onItemSelected = { index ->
                penerbitList.getOrNull(index)?.let {
                    onValueChange(insertBukuUiEvent.copy(id_penerbit = it.id_penerbit))
                }
            },
            expanded = penerbitExpanded,
            onExpandedChange = { penerbitExpanded = it }
        )

        // Tanggal terbit
        OutlinedTextField(
            value = dateFormatter.format(selectedDate.value),
            onValueChange = {},
            label = { Text("Tanggal Terbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            trailingIcon = {
                IconButton(onClick = {
                    val datePicker = DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth)
                            selectedDate.value = calendar.time
                            onValueChange(insertBukuUiEvent.copy(tanggal_terbit = calendar.time))
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePicker.show()
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Pilih Tanggal"
                    )
                }
            }
        )
    }
}
