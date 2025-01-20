package com.example.perpustakaan.ui.ViewModel.Buku

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.BukuRepository
import com.example.perpustakaan.model.Buku
import kotlinx.coroutines.launch
import com.example.perpustakaan.ui.View.Buku.DestinasiDetail
import java.util.Date

class DetailBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val buk: BukuRepository
) : ViewModel() {

    // Retrieve the id_buku from SavedStateHandle
    val id_buku: Int = checkNotNull(savedStateHandle[DestinasiDetail.ID_BUKU])

    var bukuUiState = mutableStateOf(InsertBukuUiEvent())
        private set

    init {
        ambilDetailBuku()
    }

    // Fetch the book data using id_buku
    fun ambilDetailBuku() {
        viewModelScope.launch {
            try {
                val buku = buk.getBukuID(id_buku = id_buku)

                buku?.let {
                    bukuUiState.value = it.toDetailUiEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
//
//// Data class for UI State
//data class InsertBukuUiEvent(
//    val id_buku: Int = 0,
//    val nama_buku: String = "",
//    val deskripsi_buku: String = "",
//    val tanggal_terbit: Date = Date(),
//    val status_buku: String = "",
//    val id_kategori: Int = 0,
//    val id_penulis: Int = 0,
//    val id_penerbit: Int = 0,
//)

// Extension function to convert Buku to InsertBukuUiEvent
fun Buku.toDetailUiEvent(): InsertBukuUiEvent {
    return InsertBukuUiEvent(
        id_buku = id_buku,
        nama_buku = nama_buku,
        deskripsi_buku = deskripsi_buku,
        tanggal_terbit = tanggal_terbit,
        status_buku = status_buku,
        id_kategori = id_kategori,
        id_penulis = id_penulis,
        id_penerbit = id_penerbit
    )
}
