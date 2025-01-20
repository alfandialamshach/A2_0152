package com.example.perpustakaan.ui.ViewModel.Penerbit


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.ui.View.Penerbit.DestinasiUpdateTerbit
import com.example.perpustakaan.ui.View.Penulis.DestinasiUpdate
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisUiEvent
import com.example.perpustakaan.ui.ViewModel.Penulis.InsertPenulisUiState
import com.example.perpustakaan.ui.ViewModel.Penulis.toHomePenulisUiEvent


import kotlinx.coroutines.launch

class UpdatePenerbitViewModel(
    savedStateHandle: SavedStateHandle,
    private val terbit: PenerbitRepository
) : ViewModel() {

    // Retrieve the id penulis from SavedStateHandle
    val id_penerbit: Int = checkNotNull(savedStateHandle[DestinasiUpdateTerbit.ID_Penerbit])


    var penerbituiState = mutableStateOf(InsertPenerbitUiState())
        private set

    init {
        ambilPenerbit()
    }

    // Fetch the student data using NIM
    fun ambilPenerbit() {
        viewModelScope.launch {
            try {
                val penerbit = terbit.getPenerbitID(id_penerbit = id_penerbit)

                penerbit?.let {
                   penerbituiState.value = it.toInsertTerbitUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the penerbit information
    fun updatePenerbit(id_penerbit: Int, penerbit: Penerbit) {
        viewModelScope.launch {
            try {
                terbit.updatePenerbit(id_penerbit = id_penerbit, penerbit)

                ambilPenerbit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updatePenerbitState(insertPenerbitUiEvent: InsertPenerbitUiEvent) {
        penerbituiState.value = penerbituiState.value.copy(insertPenerbitUiEvent = insertPenerbitUiEvent)
    }
}

fun Penerbit.toInsertTerbitUIEvent(): InsertPenerbitUiState = InsertPenerbitUiState(
    insertPenerbitUiEvent = this.toHomeTerbitUiEvent()
)
