package com.example.perpustakaan.ui.View.Buku

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuUiEvent
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuUiState
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuViewModel
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.ui.Alignment
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.ui.ViewModel.Kategori.InsertKategoriViewModel
import com.example.perpustakaan.ui.Widget.DropdownField

object DestinasiTambahBuku: DestinasiNavigasi {
    override val route = "item_entry"
   override  val titleRes = "Entry Mhs"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahBukuScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: InsertBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Observasi state untuk kategori, penulis, dan penerbit
    val kategoriList = viewModel.kategoriList
    val penulisList = viewModel.penulisList
    val penerbitList = viewModel.penerbitList

    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Tambah Buku",
                onKategoriClick = {},
                onPenulisClick = {  },
                onPenerbitClick = {},
                scrollBehavior = scrollBehavior,
                onRefresh = {

                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu Dosen
                isPenulisEnabled = true, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = true // Menonaktifkan menu Mata Kuliah
            )

        }
    ) { innerPadding ->
        TambahBodyBuku(
            insertUiState = viewModel.bukuUiState,
            onBukuValueChange = viewModel::updateInsertBukuState,
            onSimpanClick = {
                coroutineScope.launch {
                    viewModel.insertBuku()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            kategoriList = kategoriList,
            penulisList = penulisList,
            penerbitList = penerbitList
        )
    }
}

@Composable
fun TambahBodyBuku(
    insertUiState: InsertBukuUiState,
    onBukuValueChange: (InsertBukuUiEvent) -> Unit,
    onSimpanClick: () -> Unit,
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
        Text("Status Buku")
        statusBukuOptions.forEach { status ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
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

        // Dropdown untuk Kategori
        DropdownField(
            label = "Kategori",
            items = kategoriList.map { it.nama_kategori },
            selectedItem = kategoriList.find { it.id_kategori == insertBukuUiEvent.id_kategori }?.nama_kategori ?: "",
            onItemSelected = { index ->
                val selectedKategori = kategoriList.getOrNull(index)
                selectedKategori?.let {
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
            selectedItem = penulisList.find { it.id_penulis == insertBukuUiEvent.id_penulis }?.nama_penulis ?: "",
            onItemSelected = { index ->
                val selectedPenulis = penulisList.getOrNull(index)
                selectedPenulis?.let {
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
            selectedItem = penerbitList.find { it.id_penerbit == insertBukuUiEvent.id_penerbit }?.nama_penerbit ?: "",
            onItemSelected = { index ->
                val selectedPenerbit = penerbitList.getOrNull(index)
                selectedPenerbit?.let {
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
