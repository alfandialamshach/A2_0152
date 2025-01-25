package com.example.perpustakaan.ui.ViewModel.Penerbit

import androidx.compose.runtime.getValue
import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.model.Penerbit
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePenerbitUiState {
    data class Success(val penerbit: List<Penerbit>) : HomePenerbitUiState()
    object  Error : HomePenerbitUiState()
    object Loading : HomePenerbitUiState()
}

class HomePenerbitViewModel (private val penerbit: PenerbitRepository) : ViewModel(){
    var penerbitUiState: HomePenerbitUiState by mutableStateOf(HomePenerbitUiState.Loading)
        private set
    init {
        getPenerbit()
    }

    fun getPenerbit(){
        viewModelScope.launch {
            penerbitUiState = HomePenerbitUiState.Loading
            penerbitUiState = try {
                HomePenerbitUiState.Success(penerbit.getAllPenerbit().data)
            }catch (e: IOException){
                HomePenerbitUiState.Error
            }catch (e: HttpException){
                HomePenerbitUiState.Error
            }
        }
    }

    fun deletePenerbit(id_penerbit : Int){
        viewModelScope.launch {
            try {
                penerbit.deletePenerbit(id_penerbit)
            }catch (e: IOException){
                HomePenerbitUiState.Error
            }catch (e: HttpException){
                HomePenerbitUiState.Error
            }
        }
    }
}

fun Penerbit.toHomeTerbitUiEvent(): InsertPenerbitUiEvent {
    return InsertPenerbitUiEvent(
        id_penerbit =id_penerbit,
        nama_penerbit = nama_penerbit,
        alamat_penerbit = alamat_penerbit,
        telepon_penerbit = telepon_penerbit,

    )
}