package com.example.perpustakaan.ui.ViewModel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Penulis
import kotlinx.coroutines.launch


class InsertPenulisViewModel(private val penulis: PenulisRepository) : ViewModel() {
    var penulisuiState by mutableStateOf(InsertPenulisUiState())

    fun updateInsertPenulisState(insertPenulisUiEvent: InsertPenulisUiEvent) {
        penulisuiState = InsertPenulisUiState(insertPenulisUiEvent = insertPenulisUiEvent)
    }

    suspend fun insertPenulis() {
        viewModelScope.launch {
            try {
                penulis.insertPenulis(penulisuiState.insertPenulisUiEvent.toPenulis())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenulisUiState(
    val insertPenulisUiEvent: InsertPenulisUiEvent = InsertPenulisUiEvent(id_penulis = 0)
)

data class InsertPenulisUiEvent(
    val id_penulis:Int,
    val nama_penulis:String = "",
    val biografi:String = "",
    val kontak:String = "",

)
fun InsertPenulisUiEvent.toPenulis(): Penulis = Penulis(
  id_penulis = id_penulis,
    nama_penulis = nama_penulis,
    biografi = biografi,
    kontak =kontak
)
