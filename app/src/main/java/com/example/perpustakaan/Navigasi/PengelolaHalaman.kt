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
import com.example.perpustakaan.ui.View.Kategori.DestinasiDetailKategori
import com.example.perpustakaan.ui.View.Kategori.DestinasiHomeKategori
import com.example.perpustakaan.ui.View.Kategori.DestinasiTambahKategori
import com.example.perpustakaan.ui.View.Kategori.DestinasiUpdateKategori
import com.example.perpustakaan.ui.View.Kategori.DetailKategoriView
import com.example.perpustakaan.ui.View.Kategori.HomeKategori
import com.example.perpustakaan.ui.View.Kategori.TambahKategoriScreen
import com.example.perpustakaan.ui.View.Kategori.UpdateKategoriView
import com.example.perpustakaan.ui.View.Penerbit.DestinasiDetailPenerbit
import com.example.perpustakaan.ui.View.Penerbit.DestinasiHomePenerbit
import com.example.perpustakaan.ui.View.Penerbit.DestinasiTambahPenerbit
import com.example.perpustakaan.ui.View.Penerbit.DestinasiUpdateTerbit
import com.example.perpustakaan.ui.View.Penerbit.DetailPenerbitView
import com.example.perpustakaan.ui.View.Penerbit.HomePenerbit
import com.example.perpustakaan.ui.View.Penerbit.TambahPenerbitScreen
import com.example.perpustakaan.ui.View.Penerbit.UpdateTerbitView
import com.example.perpustakaan.ui.View.Penulis.DestinasiDetailPenulis
import com.example.perpustakaan.ui.View.Penulis.DestinasiHomePenulis
import com.example.perpustakaan.ui.View.Penulis.DestinasiTambahPenulis
import com.example.perpustakaan.ui.View.Penulis.DestinasiUpdate
import com.example.perpustakaan.ui.View.Penulis.DetailPenulisView
import com.example.perpustakaan.ui.View.Penulis.HomePenulis
import com.example.perpustakaan.ui.View.Penulis.TambahPenulisScreen
import com.example.perpustakaan.ui.View.Penulis.UpdateView
import com.example.perpustakaan.ui.View.Profil.DestinasiHomeProfil
import com.example.perpustakaan.ui.View.Profil.HomeProfilScreen
import com.example.perpustakaan.ui.View.Profil.HomeViewProfil

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

        composable(route = DestinasiHomeProfil.route) {
            HomeProfilScreen(
                onClickPenulis = { navController.navigate(DestinasiHomePenulis.route) },
                onClickPenerbit = { navController.navigate(DestinasiHomePenerbit.route) },
                onClickKategori = { navController.navigate(DestinasiHomeKategori.route) },
                onHomeClick = { navController.navigate(DestinasiHome.route) },
            )
        }


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
                onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
                onProfilClick = {navController.navigate(DestinasiHomeProfil.route)},
                onUpdateBukuClick = { buku ->
                    // Navigasi ke halaman update dengan id_buku dari objek buku sebagai argumen
                    navController.navigate("${DestinasiUpdateBuku.route}/${buku.id_buku}")
                },
            )
        }
        composable(DestinasiTambahBuku.route) {
            TambahBukuScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }

            },
                onBackClick = { navController.navigate(DestinasiHome.route) },
                onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
                onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)}
            )
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
                    onEditClick = { buku ->
                        // Navigasi ke halaman update dengan id_buku dari objek buku sebagai argumen
                        navController.navigate("${DestinasiUpdateBuku.route}/${id_buku}")
                    },
                    onItemClick = { item ->
                        if (id_buku != null) {
                            when {
                                item.startsWith("Kategori") -> {
                                    // Navigasi ke DestinasiUpdateBuku dengan id_buku
                                    navController.navigate("${DestinasiUpdateBuku.route}/$id_buku")
                                }
                                item.startsWith("Penulis") -> {
                                    // Navigasi ke DestinasiHomePenulis
                                    navController.navigate(DestinasiHomePenulis.route)
                                }
                                item.startsWith("Penerbit") -> {
                                    // Navigasi ke DestinasiHomePenerbit
                                    navController.navigate(DestinasiHomePenerbit.route)
                                }
                                else -> {
                                    Log.e("onItemClick", "Unknown item clicked: $item")
                                }
                            }
                        } else {
                            Log.e("onItemClick", "ID Buku is null")
                        }
                    },
                    onHomeClick = { navController.navigate(DestinasiHome.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onAddClick = { navController.navigate(DestinasiTambahBuku.route) },
                    onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                    onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},


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
                    modifier = modifier,
                    onBackClick = {  navController.navigate("${DestinasiDetail.route}/${id_buku}") },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onAddClick = { navController.navigate(DestinasiTambahBuku.route) },
                    onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                    onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
                )
            }
        }


        //Route untuk Kategori

            composable(DestinasiHomeKategori.route) {

                HomeKategori(
                    navigateToItemEntry = { navController.navigate(DestinasiTambahKategori.route) },
                    onDetailClick = { id_kategori ->
                        //Navigasi ke destinasi Detail dengan menyertakan nim
                        navController.navigate("${DestinasiDetailKategori.route}/${id_kategori}") {
                            popUpTo(DestinasiHomeKategori.route) {
                                inclusive = true
                            }
                        }
                    },
                    onUpdateKategoriClick = { kategori ->
                        // Navigasi ke halaman update dengan Penulis ID sebagai argumen
                        navController.navigate("${DestinasiUpdateKategori.route}/${kategori.id_kategori}")
                    },
                    onHomeClick = { navController.navigate(DestinasiHome.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                    onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
                )
            }
            composable(DestinasiTambahKategori.route) {

                TambahKategoriScreen(navigateBack = {
                    navController.navigate(DestinasiHomeKategori.route) {
                        popUpTo(DestinasiHomeKategori.route) {
                            inclusive = true
                        }
                    }
                },
                    onBackClick = { navController.navigate(DestinasiHomeKategori.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                    onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
                    )
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
                        modifier = modifier,
                        onBackClick = { navController.navigate(DestinasiHomeKategori.route) },
                        onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                        onAddClick = { navController.navigate(DestinasiTambahKategori.route) },
                        onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                        onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
                    )
                }
            }

        composable(
            DestinasiDetailKategori.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKategori.ID_Kategori) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Mendapatkan id_buku dari argument route
            val id_kategori = backStackEntry.arguments?.getInt(DestinasiDetailKategori.ID_Kategori)

            id_kategori?.let {
                // Mengirimkan id_buku ke DetailBukuView
                DetailKategoriView(
                    id_kategori = it,
                    onEditClick = { buku ->
                        // Navigasi ke halaman update dengan id_buku dari objek buku sebagai argumen
                        navController.navigate("${DestinasiUpdateKategori.route}/${id_kategori}")
                    },
//
                    onHomeClick = { navController.navigate(DestinasiHome.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onAddClick = { navController.navigate(DestinasiTambahKategori.route) },
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                    onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},


                    )
            } ?: run {
                // Tampilkan pesan error jika id_buku tidak ditemukan
                Text("ID Buku tidak valid atau tidak ditemukan.", color = Color.Red)
            }
        }


            //Route untuk Penulis

            composable(DestinasiHomePenulis.route) {
                HomePenulis(
                    navigateToItemEntry = { navController.navigate(DestinasiTambahPenulis.route) },
                    onUpdateClick = { penulis ->
                        // Navigasi ke halaman update dengan Penulis ID sebagai argumen
                        navController.navigate("${DestinasiUpdate.route}/${penulis.id_penulis}")
                    },
                    onDetailClick = { id_penulis ->
                        //Navigasi ke destinasi Detail dengan menyertakan nim
                        navController.navigate("${DestinasiDetailPenulis.route}/${id_penulis}") {
                            popUpTo(DestinasiHomePenulis.route) {
                                inclusive = true
                            }
                        }
                    },
                    onHomeClick = { navController.navigate(DestinasiHome.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                )
            }
            composable(DestinasiTambahPenulis.route) {
                TambahPenulisScreen(navigateBack = {
                    navController.navigate(DestinasiHomePenulis.route) {
                        popUpTo(DestinasiHomePenulis.route) {
                            inclusive = true
                        }
                    }
                },
                    onBackClick = { navController.navigate(DestinasiHomePenulis.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                )
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
                        modifier = modifier,
                        onBackClick = { navController.navigate(DestinasiHomePenulis.route) },
                        onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                        onAddClick = { navController.navigate(DestinasiTambahPenulis.route) },
                        onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                        onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},
                    )
                }
            }

        composable(
            DestinasiDetailPenulis.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPenulis.ID_Penulis) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Mendapatkan id_buku dari argument route
            val id_penulis = backStackEntry.arguments?.getInt(DestinasiDetailPenulis.ID_Penulis)

            id_penulis?.let {
                // Mengirimkan id_buku ke DetailBukuView
                DetailPenulisView(
                    id_penulis = it,
                    onEditClick = { penulis ->
                        // Navigasi ke halaman update dengan id_buku dari objek buku sebagai argumen
                        navController.navigate("${DestinasiUpdate.route}/${id_penulis}")
                    },
//
                    onHomeClick = { navController.navigate(DestinasiHome.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onAddClick = { navController.navigate(DestinasiTambahPenulis.route) },
                    onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                    onPenerbitClick = {navController.navigate(DestinasiHomePenerbit.route)},


                    )
            } ?: run {
                // Tampilkan pesan error jika id_buku tidak ditemukan
                Text("ID Buku tidak valid atau tidak ditemukan.", color = Color.Red)
            }
        }




        //Route untuk Penerbit

        composable(DestinasiHomePenerbit.route) {
            HomePenerbit(
                navigateToItemEntry = { navController.navigate(DestinasiTambahPenerbit.route) },
                onDetailClick = { id_penerbit ->
                    //Navigasi ke destinasi Detail dengan menyertakan nim
                    navController.navigate("${DestinasiDetailPenerbit.route}/${id_penerbit}") {
                        popUpTo(DestinasiHomePenerbit.route) {
                            inclusive = true
                        }
                    }
                },
                onUpdateTerbitClick = { penerbit ->
                    // Navigasi ke halaman update dengan Penulis ID sebagai argumen
                    navController.navigate("${DestinasiUpdateTerbit.route}/${penerbit.id_penerbit}")
                },
                onHomeClick = { navController.navigate(DestinasiHome.route) },
                onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
            )
        }
        composable(DestinasiTambahPenerbit.route) {
           TambahPenerbitScreen (navigateBack = {
                navController.navigate(DestinasiHomePenerbit.route) {
                    popUpTo(DestinasiHomePenerbit.route) {
                        inclusive = true
                    }
                }
            },
               onBackClick = { navController.navigate(DestinasiHomePenerbit.route) },
               onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
               onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
               onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
               )
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
                    modifier = modifier,
                    onBackClick = { navController.navigate(DestinasiHomePenerbit.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onAddClick = { navController.navigate(DestinasiTambahPenerbit.route) },
                    onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                    onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},
                )
            }
        }

        composable(
            DestinasiDetailPenerbit.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPenerbit.ID_Penerbit) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Mendapatkan id_buku dari argument route
            val id_penerbit = backStackEntry.arguments?.getInt(DestinasiDetailPenerbit.ID_Penerbit)

            id_penerbit?.let {
                // Mengirimkan id_buku ke DetailBukuView
                DetailPenerbitView(
                    id_penerbit = it,
                    onEditClick = { penerbit ->
                        // Navigasi ke halaman update dengan id_buku dari objek buku sebagai argumen
                        navController.navigate("${DestinasiUpdateTerbit.route}/${id_penerbit}")
                    },
//
                    onHomeClick = { navController.navigate(DestinasiHome.route) },
                    onProfilClick = { navController.navigate(DestinasiHomeProfil.route) },
                    onAddClick = { navController.navigate(DestinasiTambahPenerbit.route) },
                    onKategoriClick = {navController.navigate(DestinasiHomeKategori.route)},
                    onPenulisClick = {navController.navigate(DestinasiHomePenulis.route)},


                    )
            } ?: run {
                // Tampilkan pesan error jika id_buku tidak ditemukan
                Text("ID Buku tidak valid atau tidak ditemukan.", color = Color.Red)
            }
        }


    }
}