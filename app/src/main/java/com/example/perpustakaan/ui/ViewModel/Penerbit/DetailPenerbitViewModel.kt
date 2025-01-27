package com.example.perpustakaan.ui.ViewModel.Penerbit

import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.model.Penerbit
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.ui.View.Kategori.DestinasiDetailKategori
import com.example.perpustakaan.ui.View.Penerbit.DestinasiDetailPenerbit
import kotlinx.coroutines.launch


class DetailPenerbitViewModel(
    savedStateHandle: SavedStateHandle,
    private val pnr: PenerbitRepository
) : ViewModel() {

    // Retrieve the id_buku from SavedStateHandle
    val id_penerbit: Int = checkNotNull(savedStateHandle[DestinasiDetailPenerbit.ID_Penerbit])

    var penerbitUiState = mutableStateOf(InsertPenerbitUiEvent(id_penerbit))
        private set

    init {
        ambilDetailPenerbit()
    }

    // Fetch the book data using id_buku
    fun ambilDetailPenerbit() {
        viewModelScope.launch {
            try {
                val penerbit = pnr.getPenerbitID(id_penerbit = id_penerbit)

                penerbit?.let {
                    penerbitUiState.value = it.toDetailPenerbitUiEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


// Extension function to convert Buku to InsertBukuUiEvent
fun Penerbit.toDetailPenerbitUiEvent(): InsertPenerbitUiEvent {
    return InsertPenerbitUiEvent(
        id_penerbit = id_penerbit,
        nama_penerbit = nama_penerbit,
        alamat_penerbit = alamat_penerbit,
        telepon_penerbit = telepon_penerbit
    )
}
