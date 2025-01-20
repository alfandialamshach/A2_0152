package com.example.perpustakaan.ui.ViewModel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perpustakaan.Repository.BukuRepository
import com.example.perpustakaan.Repository.KategoriRepository
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.ui.ViewModel.Home.HomeUtamaUiState
import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitUiEvent
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisUiEvent
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeKategoriUiState {
    data class Success(val kategori: List<Kategori>) : HomeKategoriUiState()
    object  Error : HomeKategoriUiState()
    object Loading : HomeKategoriUiState()
}

class HomeKategoriViewModel (private val kategori: KategoriRepository) : ViewModel(){
    var kategoriUIState: HomeKategoriUiState by mutableStateOf(HomeKategoriUiState.Loading)
        private set
    init {
        getKategori()
    }

    fun getKategori(){
        viewModelScope.launch {
            kategoriUIState = HomeKategoriUiState.Loading
            kategoriUIState = try {
                HomeKategoriUiState.Success(kategori.getAllKategori().data)
            }catch (e: IOException){
                HomeKategoriUiState.Error
            }catch (e: HttpException){
                HomeKategoriUiState.Error
            }
        }
    }

    fun deleteBuku(id_kategori : Int){
        viewModelScope.launch {
            try {
                kategori.deleteKategori(id_kategori)
            }catch (e: IOException){
                HomeUtamaUiState.Error
            }catch (e: HttpException){
                HomeUtamaUiState.Error
            }
        }
    }
}

fun Kategori.toHomeKategoriUiEvent(): InsertKategoriUiEvent {
    return InsertKategoriUiEvent(
        id_kategori =id_kategori,
        nama_kategori = nama_kategori,
       deskripsi_kategori = deskripsi_kategori,
        )
}

