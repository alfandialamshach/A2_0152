package com.example.perpustakaan.ui.View.Kategori

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
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.ui.ViewModel.Kategori.HomeKategoriUiState
import com.example.perpustakaan.ui.ViewModel.Kategori.HomeKategoriViewModel
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar


object DestinasiHomeKategori: DestinasiNavigasi {
    override val route = "home_kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKategori(
    navigateToItemEntry:()->Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit ={},
    onProfilClick: () -> Unit,
    onPenulisClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onUpdateKategoriClick: (Kategori) -> Unit, // Menambahkan parameter untuk update
    viewModel: HomeKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Kategori",
                onKategoriClick = {},
                onPenulisClick = onPenulisClick,
                onPenerbitClick = onPenerbitClick,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getKategori()

                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isPenulisEnabled = true, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = true, // Menonaktifkan menu Mata Kuliah
                isKategoriEnabled = false, // Mengaktifkan menu Dosen
            )
        },
        bottomBar = {
            CustomBottomAppBar(
                isBackEnabled =false,
                onHomeClick = onHomeClick,
                onProfileClick = onProfilClick,
                onAddDataClick = navigateToItemEntry, // Navigate to item entry when Add Data is clicked
                onBackClick = { } // Handle Back click action
            )
        },
    ) { innerPadding->
        HomeStatus(
            homeKategoriUiState = viewModel.kategoriUIState,
            retryAction = {viewModel.getKategori()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteBuku(it.id_kategori)
                viewModel.getKategori()
            },
            onUpdateKategoriClick = onUpdateKategoriClick
        )
    }
}

@Composable
fun HomeStatus(
    homeKategoriUiState: HomeKategoriUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onUpdateKategoriClick: (Kategori) -> Unit, // Menambahkan parameter untuk update
){
    when (homeKategoriUiState){
        is HomeKategoriUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomeKategoriUiState.Success ->
            if(homeKategoriUiState.kategori.isEmpty()){
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data buku", style = MaterialTheme.typography.bodyLarge)
                }
            }else{
                BukuList(
                    kategori = homeKategoriUiState.kategori,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_kategori)
                    },
                    onDeleteClick={
                        onDeleteClick(it)
                    },
                    onUpdateKategoriClick = onUpdateKategoriClick
                )
            }
        is HomeKategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDetailClick:(Kategori)->Unit,
    onDeleteClick: (Kategori) -> Unit = {},
    onUpdateKategoriClick: (Kategori) -> Unit = {}  // Menambahkan parameter untuk update
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kategori){ kategori ->
            BukuCard(
                kategori = kategori,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(kategori)},
                onDeleteClick={
                    onDeleteClick(kategori)
                },
                onUpdateKategoriClick = {
                    onUpdateKategoriClick(kategori)  // Menambahkan fungsi update
                }
            )

        }
    }
}

@Composable
fun BukuCard(
    kategori: Kategori,
    modifier: Modifier = Modifier,
    onDeleteClick:(Kategori)->Unit={},
    onUpdateKategoriClick: (Kategori) -> Unit = {}  // Menambahkan parameter untuk update
){
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
                    text = kategori.id_kategori.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp // Jika ingin lebih besar
                    )
                )

                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                // Tombol Update
                IconButton(onClick = { onUpdateKategoriClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,  // Menambahkan icon Edit untuk tombol Update
                        contentDescription = "Update",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                text = kategori.nama_kategori,
                style = MaterialTheme.typography.titleLarge
            )

        }
    }
}