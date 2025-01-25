package com.example.perpustakaan.ui.View.Penerbit


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.ui.ViewModel.Penerbit.DetailPenerbitViewModel
import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitUiEvent
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar


object DestinasiDetailPenerbit : DestinasiNavigasi {
    override val route = "detailpnr"
    const val ID_Penerbit = "id_penerbit"
    val routesWithArg = "$route/{$ID_Penerbit}"
    override val titleRes = "Detail Penerbit"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPenerbitView(
    id_penerbit: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailPenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    onPenulisClick: () -> Unit,
    onKategoriClick: () -> Unit,
    onProfilClick: () -> Unit,
    onAddClick: () -> Unit,
    onHomeClick: () -> Unit = {},

    ) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Detail Buku",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = {},
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.ambilDetailPenerbit()
                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu Dosen
                isPenulisEnabled = true, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = false // Menonaktifkan menu Mata Kuliah
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_penerbit) },
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
        val penerbitUiState by viewModel.penerbitUiState

        BodyDetailBuku(
            modifier = Modifier.padding(innerPadding),
            penerbitUiState = penerbitUiState,
            //onItemClick = onItemClick // Menambahkan onItemClick ke BodyDetailBuku
        )
    }
}

@Composable
fun BodyDetailBuku(
    modifier: Modifier = Modifier,
    penerbitUiState: InsertPenerbitUiEvent,
    // onItemClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // Detail buku
        ItemDetailBuku(penerbitUiEvent = penerbitUiState)
    }
}

@Composable
fun ItemDetailBuku(
    penerbitUiEvent: InsertPenerbitUiEvent
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPenerbit(judul = "ID Penerbit", isinya = penerbitUiEvent.id_penerbit)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPenerbit(judul = "Nama Penerbit", isinya = penerbitUiEvent.nama_penerbit)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPenerbit(judul = "Alamat Penerbit", isinya = penerbitUiEvent.alamat_penerbit)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPenerbit(judul = "Telepon Penerbit", isinya = penerbitUiEvent.telepon_penerbit)


        }
    }
}

@Composable
fun ComponentDetailPenerbit(
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

