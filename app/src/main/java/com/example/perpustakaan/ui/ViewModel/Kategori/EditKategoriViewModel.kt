package com.example.perpustakaan.ui.ViewModel.Kategori

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.KategoriRepository
import com.example.perpustakaan.model.Kategori
import kotlinx.coroutines.launch
import com.example.perpustakaan.ui.View.Kategori.DestinasiUpdateKategori




class UpdateKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val KTG: KategoriRepository
) : ViewModel() {

    // Retrieve the id penulis from SavedStateHandle
    val id_kategori: Int = checkNotNull(savedStateHandle[DestinasiUpdateKategori.ID_Kategori])


    var kategoriuiState = mutableStateOf(InsertKategoriUiState())
        private set

    init {
        ambilKategori()
    }

    // Fetch the student data using NIM
    fun ambilKategori() {
        viewModelScope.launch {
            try {
                val kategori = KTG.getKategoriID(id_kategori = id_kategori)

                kategori?.let {
                    kategoriuiState.value = it.toInsertKategoriUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the penerbit information
    fun updateKategori(id_kategori: Int, kategori: Kategori) {
        viewModelScope.launch {
            try {
                KTG.updateKategori(id_kategori = id_kategori, kategori)

                ambilKategori()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updateKategoriState(insertKategoriUiEvent: InsertKategoriUiEvent) {
        kategoriuiState.value = kategoriuiState.value.copy(insertKategoriUiEvent = insertKategoriUiEvent)
    }
}

fun Kategori.toInsertKategoriUIEvent(): InsertKategoriUiState = InsertKategoriUiState(
    insertKategoriUiEvent = this.toHomeKategoriUiEvent()
)

