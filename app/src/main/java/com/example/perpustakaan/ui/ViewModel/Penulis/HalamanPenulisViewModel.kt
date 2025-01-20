package com.example.perpustakaan.ui.ViewModel.Penulis


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Penulis
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePenulisUiState {
    data class Success(val penulis: List<Penulis>) : HomePenulisUiState()
    object  Error : HomePenulisUiState()
    object Loading : HomePenulisUiState()
}

class HomePenulisViewModel (private val penulis: PenulisRepository) : ViewModel() {
    var penulisUiState: HomePenulisUiState by mutableStateOf(HomePenulisUiState.Loading)
        private set

    init {
        getPenulis()
    }

    fun getPenulis() {
        viewModelScope.launch {
            penulisUiState = HomePenulisUiState.Loading
            penulisUiState = try {
                HomePenulisUiState.Success(penulis.getAllPenulis().data)
            } catch (e: IOException) {
                HomePenulisUiState.Error
            } catch (e: HttpException) {
                HomePenulisUiState.Error
            }
        }
    }

    fun deletePenulis(id_penulis: Int) {
        viewModelScope.launch {
            Log.d("DeletePenulis", "Attempting to delete penulis with id: $id_penulis")
            try {
                penulis.deletePenulis(id_penulis)
                getPenulis()
            } catch (e: IOException) {
                HomePenulisUiState.Error
            } catch (e: HttpException) {
                HomePenulisUiState.Error
            }
        }
    }

}



fun Penulis.toHomePenulisUiEvent(): InsertPenulisUiEvent{
    return InsertPenulisUiEvent(
        id_penulis =id_penulis,
        nama_penulis = nama_penulis,
        biografi = biografi,
        kontak = kontak
    )
}