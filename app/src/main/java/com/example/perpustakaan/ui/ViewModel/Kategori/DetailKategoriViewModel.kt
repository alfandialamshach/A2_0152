package com.example.perpustakaan.ui.ViewModel.Kategori

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.KategoriRepository
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.ui.View.Kategori.DestinasiDetailKategori
import kotlinx.coroutines.launch


class DetailKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val Ktg: KategoriRepository
) : ViewModel() {

    // Retrieve the id_buku from SavedStateHandle
    val id_kategori: Int = checkNotNull(savedStateHandle[DestinasiDetailKategori.ID_Kategori])

    var kategoriUiState = mutableStateOf(InsertKategoriUiEvent(id_kategori))
        private set

    init {
        ambilDetailKategori()
    }

    // Fetch the book data using id_buku
    fun ambilDetailKategori() {
        viewModelScope.launch {
            try {
                val kategori = Ktg.getKategoriID(id_kategori = id_kategori)

                kategori?.let {
                    kategoriUiState.value = it.toDetailKategoriUiEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


// Extension function to convert Buku to InsertBukuUiEvent
fun Kategori.toDetailKategoriUiEvent(): InsertKategoriUiEvent {
    return InsertKategoriUiEvent(
        id_kategori =id_kategori,
        nama_kategori = nama_kategori,
        deskripsi_kategori = deskripsi_kategori,
    )
}
