package com.example.perpustakaan.ui.View.Penerbit;

import androidx.compose.foundation.clickable;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.Edit;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.getValue;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.input.nestedscroll.nestedScroll;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.dp;
import androidx.compose.ui.unit.sp;
import androidx.lifecycle.viewmodel.compose.viewModel;
import com.example.perpustakaan.Navigasi.DestinasiNavigasi;
import com.example.perpustakaan.ui.ViewModel.Penerbit.DetailPenerbitViewModel;
import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitUiEvent;
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel;
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar;
import com.example.perpustakaan.ui.Widget.CustomTopAppBar;

object DestinasiDetailPenerbit : DestinasiNavigasi {
    override val route = "detailpnr";
    const val ID_Penerbit = "id_penerbit";
    val routesWithArg = "$route/{$ID_Penerbit}";
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
    onHomeClick: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior();
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Detail Penerbit",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = {},
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.ambilDetailPenerbit();
                },
                isMenuEnabled = true,
                isKategoriEnabled = true,
                isPenulisEnabled = true,
                isPenerbitEnabled = false
            );
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
                );
            }
        },
        bottomBar = {
            CustomBottomAppBar(
                isBackEnabled = false,
                onHomeClick = onHomeClick,
                onProfileClick = onProfilClick,
                onAddDataClick = onAddClick,
                onBackClick = {}
            );
        },
    ) { innerPadding ->
        val penerbitUiState by viewModel.penerbitUiState;

        BodyDetailBuku(
            modifier = Modifier.padding(innerPadding),
            penerbitUiState = penerbitUiState
        );
    }
}

@Composable
fun BodyDetailBuku(
    modifier: Modifier = Modifier,
    penerbitUiState: InsertPenerbitUiEvent
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp));

        ItemDetailBuku(penerbitUiEvent = penerbitUiState);
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
        ),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPenerbit(judul = "ID Penerbit", isinya = penerbitUiEvent.id_penerbit);
            Spacer(modifier = Modifier.height(8.dp));
            ComponentDetailPenerbit(judul = "Nama Penerbit", isinya = penerbitUiEvent.nama_penerbit);
            Spacer(modifier = Modifier.height(8.dp));
            ComponentDetailPenerbit(judul = "Alamat Penerbit", isinya = penerbitUiEvent.alamat_penerbit);
            Spacer(modifier = Modifier.height(8.dp));
            ComponentDetailPenerbit(judul = "Telepon Penerbit", isinya = penerbitUiEvent.telepon_penerbit);
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
            .clickable { onClick?.invoke() }
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul:",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        );
        Text(
            text = when (isinya) {
                is Int -> isinya.toString();
                is java.util.Date -> isinya.toString();
                else -> isinya.toString();
            },
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        );
    }
}
