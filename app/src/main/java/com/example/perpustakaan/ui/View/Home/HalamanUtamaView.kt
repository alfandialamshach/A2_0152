package com.example.perpustakaan.ui.View.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.R
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.ui.ViewModel.Home.HomeUtamaUiState
import com.example.perpustakaan.ui.ViewModel.Home.HomeViewModel
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiHome: DestinasiNavigasi {
    override val route ="home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onKategoriClick: () -> Unit,
    onProfilClick: () -> Unit,
    onPenulisClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    onUpdateBukuClick: (Buku) -> Unit, // Menambahkan parameter untuk update
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Variabel untuk mengatur status dialog konfirmasi
    var isDialogVisible by remember { mutableStateOf(false) }
    var bukuToDelete by remember { mutableStateOf<Buku?>(null) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Perpustakaan",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = onPenerbitClick,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getBuku() },
                isMenuEnabled = true,
                isKategoriEnabled = true,
                isPenulisEnabled = true,
                isPenerbitEnabled = true
            )
        },
        bottomBar = {
            CustomBottomAppBar(
                isBackEnabled = false,
                onHomeClick = { viewModel.getBuku() },
                onProfileClick = onProfilClick,
                onAddDataClick = navigateToItemEntry,
                onBackClick = { }
            )
        }
    ) { innerPadding ->
        HomeStatus(
            homeUtamaUiState = viewModel.bukuUIState,
            retryAction = { viewModel.getBuku() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { buku ->
                // Tampilkan dialog konfirmasi sebelum menghapus
                bukuToDelete = buku
                isDialogVisible = true
            },
            onUpdateBukuClick = onUpdateBukuClick
        )
    }

    // Dialog konfirmasi sebelum menghapus buku
    if (isDialogVisible && bukuToDelete != null) {
        AlertDialog(
            onDismissRequest = { isDialogVisible = false },
            title = {
                Text(text = "Konfirmasi Hapus")
            },
            text = {
                Text(text = "Apakah Anda yakin ingin menghapus buku '${bukuToDelete?.nama_buku}'?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        bukuToDelete?.let {
                            viewModel.deleteBuku(it.id_buku)
                            viewModel.getBuku()
                        }
                        isDialogVisible = false
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                Button(onClick = { isDialogVisible = false }) {
                    Text("Tidak")
                }
            }
        )
    }
}


@Composable
fun HomeStatus(
    homeUtamaUiState: HomeUtamaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Buku) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onUpdateBukuClick: (Buku) -> Unit = {}  // Menambahkan parameter untuk update
){
    when (homeUtamaUiState){
        is HomeUtamaUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUtamaUiState.Success ->
            if(homeUtamaUiState.buku.isEmpty()){
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data buku", style = MaterialTheme.typography.bodyLarge)
                }
            }else{
                BukuList(
                    buku = homeUtamaUiState.buku,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_buku)
                    },
                    onDeleteClick={
                        onDeleteClick(it)
                    },
                    onUpdateBukuClick = onUpdateBukuClick
                )
            }
        is HomeUtamaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.loadingg),
            contentDescription = stringResource(R.string.loading)
        )
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun OnError(retryAction:()->Unit, modifier: Modifier = Modifier){
    Column(
        modifier=modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = stringResource(R.string.loading_failed),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BukuList(
    buku: List<Buku>,
    modifier: Modifier = Modifier,
    onDetailClick:(Buku)->Unit,
    onDeleteClick: (Buku) -> Unit = {},
    onUpdateBukuClick: (Buku) -> Unit = {}  // Menambahkan parameter untuk update
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(buku){ buku ->
            BukuCard(
                buku = buku,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(buku)},
                onDeleteClick={
                    onDeleteClick(buku)
                },
                onUpdateBukuClick = {
                    onUpdateBukuClick(buku)
                }
            )

        }
    }
}
@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier,
    onDeleteClick: (Buku) -> Unit = {},
    onUpdateBukuClick: (Buku) -> Unit = {}
) {
    // Format tanggal menggunakan SimpleDateFormat
    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val formattedDate = simpleDateFormat.format(buku.tanggal_terbit)

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF84A2A7), // Warna biru
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buku.nama_buku,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
                Spacer(Modifier.weight(1f))

                // Tombol Delete dengan desain lebih menarik dan jarak
                IconButton(
                    onClick = { onDeleteClick(buku) },
                    modifier = Modifier
                        .size(40.dp) // Ukuran lebih besar
                        .background(
                            color = Color(0xFF616161), // Warna oranye lembut untuk Delete
                            shape = CircleShape
                        )
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.width(16.dp)) // Jarak antara tombol Delete dan Update

                // Tombol Update dengan desain lebih menarik dan jarak
                IconButton(
                    onClick = { onUpdateBukuClick(buku) },
                    modifier = Modifier
                        .size(40.dp) // Ukuran lebih besar
                        .background(
                            color = Color(0xFF616161), // Warna biru muda untuk Update
                            shape = CircleShape
                        )
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Tampilkan tanggal dengan ikon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Tanggal Terbit",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Tanggal Terbit: $formattedDate",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }

            // Tampilkan deskripsi dengan ikon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Deskripsi Buku",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = buku.deskripsi_buku,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}
