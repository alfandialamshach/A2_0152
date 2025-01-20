package com.example.perpustakaan.Navigasi

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perpustakaan.ui.View.Buku.DestinasiDetail
import com.example.perpustakaan.ui.View.Buku.DestinasiTambahBuku
import com.example.perpustakaan.ui.View.Buku.DestinasiUpdateBuku
import com.example.perpustakaan.ui.View.Buku.DetailBukuView
import com.example.perpustakaan.ui.View.Buku.TambahBukuScreen
import com.example.perpustakaan.ui.View.Buku.UpdateBukuView

import com.example.perpustakaan.ui.View.Home.DestinasiHome
import com.example.perpustakaan.ui.View.Home.HomeScreen
import com.example.perpustakaan.ui.View.Kategori.DestinasiHomeKategori
import com.example.perpustakaan.ui.View.Kategori.DestinasiTambahKategori
import com.example.perpustakaan.ui.View.Kategori.DestinasiUpdateKategori
import com.example.perpustakaan.ui.View.Kategori.HomeKategori
import com.example.perpustakaan.ui.View.Kategori.TambahKategoriScreen
import com.example.perpustakaan.ui.View.Kategori.UpdateKategoriView
import com.example.perpustakaan.ui.View.Penerbit.DestinasiHomePenerbit
import com.example.perpustakaan.ui.View.Penerbit.DestinasiTambahPenerbit
import com.example.perpustakaan.ui.View.Penerbit.DestinasiUpdateTerbit
import com.example.perpustakaan.ui.View.Penerbit.HomePenerbit
import com.example.perpustakaan.ui.View.Penerbit.TambahPenerbitScreen
import com.example.perpustakaan.ui.View.Penerbit.UpdateTerbitView
import com.example.perpustakaan.ui.View.Penulis.DestinasiHomePenulis
import com.example.perpustakaan.ui.View.Penulis.DestinasiTambahPenulis
import com.example.perpustakaan.ui.View.Penulis.DestinasiUpdate
import com.example.perpustakaan.ui.View.Penulis.HomePenulis
import com.example.perpustakaan.ui.View.Penulis.TambahPenulisScreen
import com.example.perpustakaan.ui.View.Penulis.UpdateView

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {



        //Route untuk Buku

        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiTambahBuku.route) },
                onDetailClick = { id_buku ->
                     //Navigasi ke destinasi Detail dengan menyertakan nim
                    navController.navigate("${DestinasiDetail.route}/${id_buku}") {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },

                onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)}
            )
        }
        composable(DestinasiTambahBuku.route) {
            TambahBukuScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.ID_BUKU) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Mendapatkan id_buku dari argument route
            val id_buku = backStackEntry.arguments?.getInt(DestinasiDetail.ID_BUKU)

            id_buku?.let {
                // Mengirimkan id_buku ke DetailBukuView
                DetailBukuView(
                    id_buku = it,
                    navigateBack = {
                        // Aksi ketika tombol "Kembali" ditekan
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) {
                                inclusive = true // Pop sampai ke DestinasiHome
                            }
                        }
                    },
                    onEditClick = { buku ->
                        // Navigasi ke halaman update dengan id_buku dari objek buku sebagai argumen
                        navController.navigate("${DestinasiUpdateBuku.route}/${id_buku}")
                    },
                    onItemClick = { item ->
                        // Navigasi berdasarkan item yang dipilih, bisa kategori, penulis, atau penerbit
                        when (item) {
                            "Kategori" -> navController.navigate("${DestinasiUpdateBuku.route}/$id_buku")
                            "Penulis" -> navController.navigate(DestinasiHomePenulis.route)
                            "Penerbit" -> navController.navigate(DestinasiHomePenerbit.route)
                        }
                    }

                )
            } ?: run {
                // Tampilkan pesan error jika id_buku tidak ditemukan
                Text("ID Buku tidak valid atau tidak ditemukan.", color = Color.Red)
            }
        }

        composable(
            DestinasiUpdateBuku.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBuku.ID_Buku) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_buku =
                backStackEntry.arguments?.getInt(DestinasiUpdateBuku.ID_Buku)
            id_buku?.let {
                UpdateBukuView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }


        //Route untuk Kategori

            composable(DestinasiHomeKategori.route) {

                HomeKategori(
                    navigateToItemEntry = { navController.navigate(DestinasiTambahKategori.route) },

                    onUpdateKategoriClick = { kategori ->
                        // Navigasi ke halaman update dengan Penulis ID sebagai argumen
                        navController.navigate("${DestinasiUpdateKategori.route}/${kategori.id_kategori}")
                    }
                )
            }
            composable(DestinasiTambahKategori.route) {

                TambahKategoriScreen(navigateBack = {
                    navController.navigate(DestinasiHomeKategori.route) {
                        popUpTo(DestinasiHomeKategori.route) {
                            inclusive = true
                        }
                    }
                })
            }


            composable(
                DestinasiUpdateKategori.routesWithArg,
                arguments = listOf(
                    navArgument(DestinasiUpdateKategori.ID_Kategori) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val id_kategori =
                    backStackEntry.arguments?.getInt(DestinasiUpdateKategori.ID_Kategori)
                id_kategori?.let {
                    UpdateKategoriView(
                        navigateBack = { navController.popBackStack() },
                        modifier = modifier
                    )
                }
            }


            //Route untuk Penulis

            composable(DestinasiHomePenulis.route) {
                HomePenulis(
                    navigateToItemEntry = { navController.navigate(DestinasiTambahPenulis.route) },
                    onUpdateClick = { penulis ->
                        // Navigasi ke halaman update dengan Penulis ID sebagai argumen
                        navController.navigate("${DestinasiUpdate.route}/${penulis.id_penulis}")
                    }
                )
            }
            composable(DestinasiTambahPenulis.route) {
                TambahPenulisScreen(navigateBack = {
                    navController.navigate(DestinasiHomePenulis.route) {
                        popUpTo(DestinasiHomePenulis.route) {
                            inclusive = true
                        }
                    }
                })
            }
            composable(
                DestinasiUpdate.routesWithArg,
                arguments = listOf(
                    navArgument(DestinasiUpdate.ID_Penulis) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val id_penulis = backStackEntry.arguments?.getInt(DestinasiUpdate.ID_Penulis)
                id_penulis?.let {
                    UpdateView(
                        navigateBack = { navController.popBackStack() },
                        modifier = modifier
                    )
                }
            }





        //Route untuk Penerbit

        composable(DestinasiHomePenerbit.route) {
            HomePenerbit(
                navigateToItemEntry = { navController.navigate(DestinasiTambahPenerbit.route) },
//                onUpdateClick = {
//                    // Navigasi ke halaman update dengan NIM sebagai argumen
//                    navController.navigate("${DestinasiUpdate.route}/${it}")
//                }
                onUpdateTerbitClick = { penerbit ->
                    // Navigasi ke halaman update dengan Penulis ID sebagai argumen
                    navController.navigate("${DestinasiUpdateTerbit.route}/${penerbit.id_penerbit}")
                }

            )
        }
        composable(DestinasiTambahPenerbit.route) {
           TambahPenerbitScreen (navigateBack = {
                navController.navigate(DestinasiHomePenerbit.route) {
                    popUpTo(DestinasiHomePenerbit.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiUpdateTerbit.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateTerbit.ID_Penerbit) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_penerbit = backStackEntry.arguments?.getInt(DestinasiUpdateTerbit.ID_Penerbit)
            id_penerbit?.let {
                UpdateTerbitView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

    }
}