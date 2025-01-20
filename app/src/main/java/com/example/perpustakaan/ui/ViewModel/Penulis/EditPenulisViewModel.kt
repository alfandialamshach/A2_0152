package com.example.perpustakaan.ui.ViewModel.Penulis



import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.ui.View.Penulis.DestinasiUpdate


import kotlinx.coroutines.launch

class UpdatePenulisViewModel(
    savedStateHandle: SavedStateHandle,
    private val tulis: PenulisRepository
) : ViewModel() {

    // Retrieve the id penulis from SavedStateHandle
    val id_penulis: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID_Penulis])


    var penulisuiState = mutableStateOf(InsertPenulisUiState())
        private set

    init {
        ambilPenulis()
    }

    // Fetch the student data using NIM
    fun ambilPenulis() {
        viewModelScope.launch {
            try {
                val penulis = tulis.getPenulisID(id_penulis = id_penulis)

                penulis?.let {
                    penulisuiState.value = it.toInsertUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the penulis information
    fun updatePenulis(id_penulis: Int, penulis: Penulis) {
        viewModelScope.launch {
            try {
                tulis.updatePenulis(id_penulis = id_penulis, penulis)

                ambilPenulis()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updatePenulisState(insertPenulisUiEvent: InsertPenulisUiEvent) {
        penulisuiState.value = penulisuiState.value.copy(insertPenulisUiEvent = insertPenulisUiEvent)
    }
}

fun Penulis.toInsertUIEvent(): InsertPenulisUiState = InsertPenulisUiState(
    insertPenulisUiEvent = this.toHomePenulisUiEvent()
)
