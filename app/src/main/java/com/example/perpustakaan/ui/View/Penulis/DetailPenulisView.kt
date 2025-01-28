package com.example.perpustakaan.ui.View.Penulis

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
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
import com.example.perpustakaan.ui.ViewModel.Penulis.DetailPenulisViewModel
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisUiEvent
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar

object DestinasiDetailPenulis : DestinasiNavigasi {
    override val route = "detailtulis"
    const val ID_Penulis = "id_penulis"
    val routesWithArg = "$route/{$ID_Penulis}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPenulisView(
    id_penulis: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    onPenerbitClick: () -> Unit,
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
                judul = "Detail Penulis",
                onKategoriClick = onKategoriClick,
                onPenulisClick = {},
                onPenerbitClick = onPenerbitClick,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.ambilDetailPenulis()
                },
                isMenuEnabled = true,
                isKategoriEnabled = true,
                isPenulisEnabled = false,
                isPenerbitEnabled = true
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_penulis) },
                shape = RoundedCornerShape(50),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Penulis",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        bottomBar = {
            CustomBottomAppBar(
                isBackEnabled = false,
                onHomeClick = onHomeClick,
                onProfileClick = onProfilClick,
                onAddDataClick = onAddClick,
                onBackClick = {}
            )
        },
    ) { innerPadding ->
        val penulisUiState by viewModel.penulisUiState

        BodyDetailPenulis(
            modifier = Modifier.padding(innerPadding),
            penulisUiState = penulisUiState
        )
    }
}

@Composable
fun BodyDetailPenulis(
    modifier: Modifier = Modifier,
    penulisUiState: InsertPenulisUiEvent
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ItemDetailPenulis(penulisUiEvent = penulisUiState)
    }
}

@Composable
fun ItemDetailPenulis(
    penulisUiEvent: InsertPenulisUiEvent
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Elevasi untuk bayangan
        shape = RoundedCornerShape(16.dp) // Sudut rounded
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // ID Penulis
            ComponentDetailPenulis(judul = "ID Penulis", isinya = penulisUiEvent.id_penulis)
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Garis pemisah lebih jelas
            )

            // Nama Penulis
            ComponentDetailPenulis(judul = "Nama Penulis", isinya = penulisUiEvent.nama_penulis)
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Garis pemisah lebih jelas
            )

            // Biografi
            ComponentDetailPenulis(judul = "Biografi", isinya = penulisUiEvent.biografi)
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Garis pemisah lebih halus
            )

            // Kontak
            ComponentDetailPenulis(judul = "Kontak", isinya = penulisUiEvent.kontak)
        }
    }
}


@Composable
fun ComponentDetailPenulis(
    judul: String,
    isinya: Any,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = when (isinya) {
                is Int -> isinya.toString()
                is java.util.Date -> isinya.toString()
                else -> isinya.toString()
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
