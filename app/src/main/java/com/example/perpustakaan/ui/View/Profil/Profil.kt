package com.example.perpustakaan.ui.View.Profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.perpustakaan.Navigasi.DestinasiNavigasi
import com.example.perpustakaan.R
import com.example.perpustakaan.ui.Widget.CustomBottomAppBar

object DestinasiHomeProfil : DestinasiNavigasi {
    override val route: String = "Home"
    override val titleRes = "Perpustakaan"
}

@Composable
fun HomeProfilScreen(
    onHomeClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onClickPenulis: () -> Unit = {},
    onClickPenerbit: () -> Unit = {},
    onClickKategori: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            CustomBottomAppBar(
                onHomeClick = onHomeClick,
                onProfileClick = onProfileClick,
                onAddDataClick = {},
                onBackClick = onBackClick,
                isBackEnabled = false,
                isHomeEnabled = true,
                isAddDataEnabled = true,
                isProfileEnabled = true
            )
        }
    ) { paddingValues ->
        HomeViewProfil(
            onClickPenulis = onClickPenulis,
            onClickPenerbit = onClickPenerbit,
            onClickKategori = onClickKategori,
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Composable
fun HomeViewProfil(
    onClickPenulis: () -> Unit = {},
    onClickPenerbit: () -> Unit = {},
    onClickKategori: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2196F3), Color.LightGray),
                    startY = 0f,
                    endY = 1000f
                )
            )
    ) {
        Header()
        BodyContent(
            onClickPenulis = onClickPenulis,
            onClickPenerbit = onClickPenerbit,
            onClickKategori = onClickKategori
        )
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.umy),
                contentDescription = "Logo UMY",
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            Text(
                text = "Halo,",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Alfandi Alamshach",
                color = Color.Yellow,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BodyContent(
    onClickPenulis: () -> Unit,
    onClickPenerbit: () -> Unit,
    onClickKategori: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topEnd = 50.dp)
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Menu Pilihan",
                color = Color(0xFF2196F3),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MenuItem(
                    iconResId = R.drawable.penulis,
                    label = "Penulis",
                    onClick = onClickPenulis
                )
                MenuItem(
                    iconResId = R.drawable.penerbit,
                    label = "Penerbit",
                    onClick = onClickPenerbit
                )
                MenuItem(
                    iconResId = R.drawable.kategori,
                    label = "Kategori",
                    onClick = onClickKategori
                )
            }
        }
    }
}

@Composable
fun MenuItem(iconResId: Int, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFFE3F2FD), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = label,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
