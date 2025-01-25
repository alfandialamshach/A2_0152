package com.example.perpustakaan.ui.View.Penerbit


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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.navigation.compose.rememberNavController
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.R
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.ui.ViewModel.Penerbit.HomePenerbitUiState
import com.example.perpustakaan.ui.ViewModel.Penerbit.HomePenerbitViewModel
import com.example.perpustakaan.ui.ViewModel.PenyediaViewModel
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar
import com.example.perpustakaan.ui.Widget.CustomTopAppBar


object DestinasiHomePenerbit: DestinasiNavigasi {
  override  val route ="home_Penerbit"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePenerbit(
    navigateToItemEntry:()->Unit,
    onProfilClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onPenulisClick: () -> Unit,
    onKategoriClick: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit ={},
    onUpdateTerbitClick: (Penerbit) -> Unit, // Menambahkan parameter untuk update
    viewModel: HomePenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                judul = "Penerbit",
                onKategoriClick = onKategoriClick,
                onPenulisClick = onPenulisClick,
                onPenerbitClick = {},
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPenerbit()
                },
                isMenuEnabled = true, // Menampilkan ikon menu
                isKategoriEnabled = true, // Mengaktifkan menu Dosen
                isPenulisEnabled = true, // Menonaktifkan menu Mata Kuliah
                isPenerbitEnabled = false // Menonaktifkan menu Mata Kuliah
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
            homePenerbitUiState = viewModel.penerbitUiState,
            retryAction = {viewModel.getPenerbit()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,onDeleteClick = {
                viewModel.deletePenerbit(it.id_penerbit)
                viewModel.getPenerbit()
            },
            onUpdateTerbitClick = onUpdateTerbitClick // Menggunakan fungsi update
        )
    }
}

@Composable
fun HomeStatus(
    homePenerbitUiState: HomePenerbitUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onUpdateTerbitClick: (Penerbit) -> Unit, // Menambahkan parameter untuk update
){
    when (homePenerbitUiState){
        is HomePenerbitUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomePenerbitUiState.Success ->
            if(homePenerbitUiState.penerbit.isEmpty()){
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data buku", style = MaterialTheme.typography.bodyLarge)
                }
            }else{
                BukuList(
                    penerbit = homePenerbitUiState.penerbit,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_penerbit)
                    },
                    onDeleteClick={
                        onDeleteClick(it)
                    },
                    onUpdateTerbitClick = onUpdateTerbitClick
                )
            }
        is HomePenerbitUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
    penerbit: List<Penerbit>,
    modifier: Modifier = Modifier,
    onDetailClick:(Penerbit)->Unit,
    onUpdateTerbitClick: (Penerbit) -> Unit,  // Menambahkan parameter untuk update
    onDeleteClick: (Penerbit) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penerbit){ penerbit ->
            BukuCard(
                penerbit = penerbit,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(penerbit)},
                onDeleteClick={
                    onDeleteClick(penerbit)
                },
                onUpdateTerbitClick = {
                    onUpdateTerbitClick(penerbit)  // Menambahkan fungsi update
                }
            )

        }
    }
}

@Composable
fun BukuCard(
    penerbit: Penerbit,
    modifier: Modifier = Modifier,
    onDeleteClick:(Penerbit)->Unit={},
    onUpdateTerbitClick: (Penerbit) -> Unit = {}  // Menambahkan parameter untuk update

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
                    text = penerbit.id_penerbit.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp // Jika ingin lebih besar
                    )
                )



                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(penerbit)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                // Tombol Update
                IconButton(onClick = { onUpdateTerbitClick(penerbit) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,  // Menambahkan icon Edit untuk tombol Update
                        contentDescription = "Update",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                text = penerbit.nama_penerbit,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold, // Membuat teks tebal
                    fontSize = 20.sp // Menyesuaikan ukuran teks
                )
            )
            Text(
                text = penerbit.telepon_penerbit,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal, // Tetap normal (tidak bold)
                    fontSize = 18.sp // Ukuran sedikit lebih kecil untuk perbedaan visual
                )
            )

        }
    }
}