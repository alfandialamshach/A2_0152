package com.example.perpustakaan.ui.View.Home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Perpustakaan",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = onPenerbitClick,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                viewModel.getBuku()
                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu Dosen
                isPenulisEnabled = true, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = true // Menonaktifkan menu Mata Kuliah
            )

        },
        bottomBar = {
            CustomBottomAppBar(
                isBackEnabled =false,
                onHomeClick = { viewModel.getBuku()},
                onProfileClick = onProfilClick,
                onAddDataClick = navigateToItemEntry, // Navigate to item entry when Add Data is clicked
                onBackClick = { } // Handle Back click action
            )
        },
    ) { innerPadding ->
        // Konten utama halaman
        HomeStatus(
            homeUtamaUiState = viewModel.bukuUIState,
            retryAction = { viewModel.getBuku() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteBuku(it.id_buku)
                viewModel.getBuku()
            },
            onUpdateBukuClick = onUpdateBukuClick
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
    onUpdateBukuClick: (Buku) -> Unit = {}  // Menambahkan parameter untuk update
) {
    // Format tanggal menggunakan SimpleDateFormat
    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val formattedDate = simpleDateFormat.format(buku.tanggal_terbit)

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
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
                        fontSize = 30.sp // Jika ingin lebih besar
                    )
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(buku) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                // Tombol Update
                IconButton(onClick = { onUpdateBukuClick(buku) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,  // Menambahkan icon Edit untuk tombol Update
                        contentDescription = "Update",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Tampilkan tanggal dengan format sederhana
            Text(
                text = "Tanggal Terbit: $formattedDate",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp // Jika ingin lebih besar
                )
            )
            Text(
                text = buku.deskripsi_buku,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp // Jika ingin lebih besar
                )
            )
        }
    }
}

