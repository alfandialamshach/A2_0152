package com.example.perpustakaan.ui.ViewModel.Kategori


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.KategoriRepository
import com.example.perpustakaan.model.Kategori
import kotlinx.coroutines.launch


class InsertKategoriViewModel(private val kategori: KategoriRepository) : ViewModel() {
    var kategoriuiState by mutableStateOf(InsertKategoriUiState())
    var errorMessage by mutableStateOf("")
    fun updateInsertKategoriState(insertKategoriUiEvent: InsertKategoriUiEvent) {
        kategoriuiState = InsertKategoriUiState(insertKategoriUiEvent =insertKategoriUiEvent)
    }

    suspend fun insertKategori() {
        errorMessage = ""
        val uiEvent = kategoriuiState.insertKategoriUiEvent
        // Validation
        if (uiEvent.nama_kategori.isEmpty()) {
            errorMessage = "Nama Kategori tidak boleh kosong"
            return
        }
        if (uiEvent.deskripsi_kategori.isEmpty()) {
            errorMessage = "Alamat tidak boleh kosong"
            return
        }



        viewModelScope.launch {
            try {
                kategori.insertKategori(kategoriuiState.insertKategoriUiEvent.toKategori())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertKategoriUiState(
    val insertKategoriUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent(id_kategori = 0)
)

data class InsertKategoriUiEvent(
    val id_kategori:Int,
    val nama_kategori:String = "",
    val deskripsi_kategori:String = "",


    )
fun InsertKategoriUiEvent.toKategori(): Kategori = Kategori(
    id_kategori = id_kategori,
    nama_kategori = nama_kategori,
    deskripsi_kategori = deskripsi_kategori,

)

fun Kategori.toInsertKategoriUiEvent():InsertKategoriUiEvent = InsertKategoriUiEvent(
    id_kategori =id_kategori,
    nama_kategori = nama_kategori,
    deskripsi_kategori = deskripsi_kategori,

)