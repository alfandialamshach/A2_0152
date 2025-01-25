package com.example.perpustakaan.ui.View.Buku

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Buku.DetailBukuViewModel
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuUiEvent
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    const val ID_BUKU = "id_buku"
    val routesWithArg = "$route/{$ID_BUKU}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBukuView(
    id_buku: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    onPenulisClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    onKategoriClick: () -> Unit,
    onProfilClick: () -> Unit,
    onAddClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onItemClick: (String) -> Unit // Menambahkan parameter onItemClick
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Detail Buku",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = onPenerbitClick,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.ambilDetailBuku()
                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu Dosen
                isPenulisEnabled = true, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = true // Menonaktifkan menu Mata Kuliah
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_buku) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Buku"
                )
            }
        },
        bottomBar = {
            CustomBottomAppBar(
                isBackEnabled = false,
                onHomeClick = onHomeClick,
                onProfileClick = onProfilClick,
                onAddDataClick = onAddClick, // Navigate to item entry when Add Data is clicked
                onBackClick = {  } // Handle Back click action
            )
        },
    ) { innerPadding ->
        val bukuUiState by viewModel.bukuUiState

        BodyDetailBuku(
            modifier = Modifier.padding(innerPadding),
            bukuUiState = bukuUiState,
            onItemClick = onItemClick // Menambahkan onItemClick ke BodyDetailBuku
        )
    }
}

@Composable
fun BodyDetailBuku(
    modifier: Modifier = Modifier,
    bukuUiState: InsertBukuUiEvent,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Card untuk kategori, penulis, dan penerbit
        PilihanKategoriPenulisPenerbit(
            kategori = bukuUiState.id_kategori,
            penulis = bukuUiState.id_penulis,
            penerbit = bukuUiState.id_penerbit,
            onItemClick = onItemClick // Menambahkan onItemClick ke PilihanKategoriPenulisPenerbit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Detail buku
        ItemDetailBuku(bukuUiEvent = bukuUiState)
    }
}
@Composable
fun ItemDetailBuku(
    bukuUiEvent: InsertBukuUiEvent
) {
    // Format tanggal menggunakan SimpleDateFormat
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val formattedTanggalTerbit = bukuUiEvent.tanggal_terbit?.let {
        simpleDateFormat.format(it)  // Format tanggal sesuai dengan lokal
    } ?: "Tanggal tidak tersedia"

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailBuku(judul = "ID Buku", isinya = bukuUiEvent.id_buku)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBuku(judul = "Nama Buku", isinya = bukuUiEvent.nama_buku)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBuku(judul = "Deskripsi Buku", isinya = bukuUiEvent.deskripsi_buku)
            Spacer(modifier = Modifier.padding(4.dp))
            // Menampilkan tanggal terbit yang sudah diformat
            ComponentDetailBuku(judul = "Tanggal Terbit", isinya = formattedTanggalTerbit)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBuku(judul = "Status Buku", isinya = bukuUiEvent.status_buku)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}
@Composable
fun ComponentDetailBuku(
    judul: String,
    isinya: Any,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() } // Menambahkan clickable pada item
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = when (isinya) {
                is Int -> isinya.toString()
                is java.util.Date -> isinya.toString() // Format date if needed
                else -> isinya.toString()
            }
        )
    }
}

@Composable
fun PilihanKategoriPenulisPenerbit(
    kategori: Int,
    penulis: Int,
    penerbit: Int,
    onItemClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Menambahkan clickable pada kategori dengan efek timbul
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick("Kategori: $kategori") }
                    .padding(8.dp)
                    .shadow(4.dp, shape = MaterialTheme.shapes.medium) // Efek timbul
                    .background(MaterialTheme.colorScheme.surface) // Background untuk meningkatkan kontras
            ) {
                Text(
                    text = "Kategori: $kategori",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))

            // Menambahkan clickable pada penulis dengan efek timbul
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick("Penulis: $penulis") }
                    .padding(8.dp)
                    .shadow(4.dp, shape = MaterialTheme.shapes.medium) // Efek timbul
                    .background(MaterialTheme.colorScheme.surface) // Background untuk meningkatkan kontras
            ) {
                Text(
                    text = "Penulis: $penulis",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))

            // Menambahkan clickable pada penerbit dengan efek timbul
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick("Penerbit: $penerbit") }
                    .padding(8.dp)
                    .shadow(4.dp, shape = MaterialTheme.shapes.medium) // Efek timbul
                    .background(MaterialTheme.colorScheme.surface) // Background untuk meningkatkan kontras
            ) {
                Text(
                    text = "Penerbit: $penerbit",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
