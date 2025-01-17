package com.example.perpustakaan.ui.ViewModel.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perpustakaan.Repository.BukuRepository
import com.example.perpustakaan.model.Buku
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeUtamaUiState {
    data class Success(val buku: List<Buku>) : HomeUtamaUiState()
    object  Error : HomeUtamaUiState()
    object Loading : HomeUtamaUiState()
}

class HomeViewModel (private val buku: BukuRepository) : ViewModel(){
    var bukuUIState: HomeUtamaUiState by mutableStateOf(HomeUtamaUiState.Loading)
        private set
    init {
        getBuku()
    }

    fun getBuku(){
        viewModelScope.launch {
            bukuUIState = HomeUtamaUiState.Loading
            bukuUIState = try {
//                HomeUiState.Success(mhs.getAllMahasiswa())
                HomeUtamaUiState.Success(buku.getAllBuku().data)
            }catch (e: IOException){
                HomeUtamaUiState.Error
            }catch (e: HttpException){
                HomeUtamaUiState.Error
            }
        }
    }

    fun deleteBuku(id_buku : String){
        viewModelScope.launch {
            try {
                buku.deleteBuku(id_buku)
            }catch (e:IOException){
                HomeUtamaUiState.Error
            }catch (e: HttpException){
                HomeUtamaUiState.Error
            }
        }
    }
}