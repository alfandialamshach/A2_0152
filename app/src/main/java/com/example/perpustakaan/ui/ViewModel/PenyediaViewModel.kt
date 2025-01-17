package com.example.perpustakaan.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perpustakaan.PerpustakaanApplication
import com.example.perpustakaan.ui.ViewModel.Home.HomeViewModel
import com.example.perpustakaan.ui.ViewModel.Kategori.HomeKategoriViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(AplikasiPerpustakaan().container.bukuRepository) }
        initializer { HomeKategoriViewModel(AplikasiPerpustakaan().container.kategoriRepository) }

    }

    fun CreationExtras.AplikasiPerpustakaan(): PerpustakaanApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PerpustakaanApplication)
}