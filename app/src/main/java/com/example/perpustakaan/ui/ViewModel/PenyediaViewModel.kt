package com.example.perpustakaan.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perpustakaan.PerpustakaanApplications
import com.example.perpustakaan.ui.ViewModel.Home.HomeViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(AplikasiPerpustakaan().container.bukuRepository) }

    }

    fun CreationExtras.AplikasiPerpustakaan(): PerpustakaanApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PerpustakaanApplications)
}