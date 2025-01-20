package com.example.perpustakaan.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perpustakaan.PerpustakaanApplication
import com.example.perpustakaan.ui.ViewModel.Buku.DetailBukuViewModel
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuViewModel
import com.example.perpustakaan.ui.ViewModel.Buku.UpdateBukuViewModel
import com.example.perpustakaan.ui.ViewModel.Home.HomeViewModel
import com.example.perpustakaan.ui.ViewModel.Kategori.HomeKategoriViewModel
import com.example.perpustakaan.ui.ViewModel.Kategori.InsertKategoriViewModel
import com.example.perpustakaan.ui.ViewModel.Kategori.UpdateKategoriViewModel
import com.example.perpustakaan.ui.ViewModel.Penerbit.HomePenerbitViewModel
import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitViewModel
import com.example.perpustakaan.ui.ViewModel.Penerbit.UpdatePenerbitViewModel
import com.example.perpustakaan.ui.ViewModel.Penulis.HomePenulisViewModel
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisViewModel
import com.example.perpustakaan.ui.ViewModel.Penulis.UpdatePenulisViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        //Buku
        initializer { HomeViewModel(AplikasiPerpustakaan().container.bukuRepository) }
        initializer {
            InsertBukuViewModel(
                AplikasiPerpustakaan().container.bukuRepository,
                AplikasiPerpustakaan().container.kategoriRepository,
                AplikasiPerpustakaan().container.penerbitRepository,
                AplikasiPerpustakaan().container.penulisRepository
            )
        }

        initializer { DetailBukuViewModel(createSavedStateHandle(),AplikasiPerpustakaan().container.bukuRepository) }
        initializer {
            UpdateBukuViewModel(
                savedStateHandle = createSavedStateHandle(),  // Parameter pertama harus SavedStateHandle
                bukuRepository = AplikasiPerpustakaan().container.bukuRepository,  // Repository Buku
                kategoriRepository = AplikasiPerpustakaan().container.kategoriRepository,  // Repository Kategori
                penulisRepository = AplikasiPerpustakaan().container.penulisRepository,  // Repository Penulis
                penerbitRepository = AplikasiPerpustakaan().container.penerbitRepository
            )
        }

        //Kategori
        initializer { HomeKategoriViewModel(AplikasiPerpustakaan().container.kategoriRepository) }
        initializer { InsertKategoriViewModel(AplikasiPerpustakaan().container.kategoriRepository) }
        initializer { UpdateKategoriViewModel(createSavedStateHandle(),AplikasiPerpustakaan().container.kategoriRepository) }

        //Penerbit
        initializer { HomePenerbitViewModel(AplikasiPerpustakaan().container.penerbitRepository) }
        initializer { InsertPenerbitViewModel(AplikasiPerpustakaan().container.penerbitRepository) }
        initializer { UpdatePenerbitViewModel(createSavedStateHandle(),AplikasiPerpustakaan().container.penerbitRepository) }

        //Penulis
        initializer { HomePenulisViewModel(AplikasiPerpustakaan().container.penulisRepository) }
        initializer { InsertPenulisViewModel(AplikasiPerpustakaan().container.penulisRepository) }
        initializer { UpdatePenulisViewModel(createSavedStateHandle(),AplikasiPerpustakaan().container.penulisRepository) }
    }

    fun CreationExtras.AplikasiPerpustakaan(): PerpustakaanApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PerpustakaanApplication)
}