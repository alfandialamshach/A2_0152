package com.example.perpustakaan.ui.Widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    judul: String,
    onKategoriClick: () -> Unit,
    onPenulisClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onRefresh: () -> Unit, // Menambahkan parameter untuk onRefresh
    isMenuEnabled: Boolean = true, // Kontrol status aktif menu
    isKategoriEnabled: Boolean = true, // Kontrol status aktif menu Dosen
    isPenulisEnabled: Boolean = true, // Kontrol status aktif menu Mata Kuliah
    isPenerbitEnabled: Boolean = true, // Kontrol status aktif menu Mata Kuliah
    modifier: Modifier = Modifier
) {
    // State untuk mengontrol apakah menu dropdown ditampilkan
    var showMenu by remember { mutableStateOf(false) }

    // State untuk animasi gradien latar belakang
    val gradientColors by rememberUpdatedState(
        if (showMenu) listOf(Color(0xFF1976D2), Color(0xFF64B5F6))
        else listOf(Color(0xFF2196F3), Color(0xFF42A5F5))
    )

    val gradientBrush = remember(gradientColors) {
        Brush.horizontalGradient(
            colors = gradientColors,
            startX = 0f,
            endX = 1000f,
            tileMode = TileMode.Clamp
        )
    }

    // Struktur TopAppBar
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(gradientBrush) // Latar belakang dengan gradien dinamis
            .padding(top = 24.dp, bottom = 16.dp) // Menambahkan padding atas agar tidak tertutup oleh status bar
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Menambahkan jarak antar elemen: kiri, tengah, kanan
        ) {
            // Tombol menu dropdown, tampilkan hanya jika isMenuEnabled = true
            if (isMenuEnabled) {
                IconButton(
                    onClick = { showMenu = !showMenu },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                // Dropdown menu hanya akan muncul jika menu aktif
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    if (isKategoriEnabled) { // Tampilkan item "Kategori" hanya jika isKategoriEnabled = true
                        DropdownMenuItem(
                            text = { Text("Kategori") },
                            onClick = {
                                showMenu = false
                                onKategoriClick()
                            }
                        )
                    }
                    if (isPenulisEnabled) { // Tampilkan item "Penulis" hanya jika isPenulisEnabled = true
                        DropdownMenuItem(
                            text = { Text("Penulis") },
                            onClick = {
                                showMenu = false
                                onPenulisClick()
                            }
                        )
                    }

                    if (isPenerbitEnabled) { // Tampilkan item "Penerbit" hanya jika isPenerbitEnabled = true
                        DropdownMenuItem(
                            text = { Text("Penerbit") },
                            onClick = {
                                showMenu = false
                                onPenerbitClick()
                            }
                        )
                    }
                }
            }

            // Menambahkan Spacer agar ada jarak antara Icon dan judul
            Spacer(modifier = Modifier.width(16.dp)) // Memberikan jarak antara menu dan judul

            // Judul halaman
            Text(
                text = judul,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, end = 45.dp) // Memberikan sedikit jarak di kiri dan kanan judul
            )

            // Tombol refresh berada di kanan
            IconButton(
                onClick = onRefresh,
                modifier = Modifier
                    .padding(start = 8.dp) // Memberikan jarak sedikit di kiri tombol refresh
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White
                )
            }
        }
    }
}
