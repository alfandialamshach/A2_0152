package com.example.perpustakaan.ui.ViewModel.Penulis

import com.example.perpustakaan.ui.ViewModel.Penerbit.InsertPenerbitUiEvent
import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.model.Penerbit
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.ui.View.Kategori.DestinasiDetailKategori
import com.example.perpustakaan.ui.View.Penulis.DestinasiDetailPenulis
import kotlinx.coroutines.launch


class DetailPenulisViewModel(
    savedStateHandle: SavedStateHandle,
    private val tulis: PenulisRepository
) : ViewModel() {

    // Retrieve the id_buku from SavedStateHandle
    val id_penulis: Int = checkNotNull(savedStateHandle[DestinasiDetailPenulis.ID_Penulis])

    var penulisUiState = mutableStateOf(InsertPenulisUiEvent(id_penulis))
        private set

    init {
        ambilDetailPenulis()
    }

    // Fetch the book data using id_buku
    fun ambilDetailPenulis() {
        viewModelScope.launch {
            try {
                val penulis = tulis.getPenulisID(id_penulis = id_penulis)

                penulis?.let {
                    penulisUiState.value = it.toDetailPenulisUiEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


// Extension function to convert Buku to InsertBukuUiEvent
fun Penulis.toDetailPenulisUiEvent(): InsertPenulisUiEvent {
    return InsertPenulisUiEvent(
        id_penulis =id_penulis,
        nama_penulis = nama_penulis,
        biografi = biografi,
        kontak = kontak
    )
}
