package com.example.perpustakaan.ui.ViewModel.Penulis



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.ui.View.Penulis.DestinasiUpdate


import kotlinx.coroutines.launch
import java.util.regex.Pattern

class UpdatePenulisViewModel(
    savedStateHandle: SavedStateHandle,
    private val tulis: PenulisRepository
) : ViewModel() {

    // Retrieve the id penulis from SavedStateHandle
    val id_penulis: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID_Penulis])
    var errorMessage by mutableStateOf("")

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
        val uiEvent = penulisuiState.value.insertPenulisUiEvent

        // Reset the error message before validation
        errorMessage = ""
        // Validation
        if (uiEvent.nama_penulis.isEmpty()) {
            errorMessage = "Nama penulis tidak boleh kosong"
            return
        }
        if (uiEvent.biografi.isEmpty()) {
            errorMessage = "Biografi tidak boleh kosong"
            return
        }
        if (uiEvent.kontak.isEmpty() || !isValidEmail(uiEvent.kontak)) {
            errorMessage = "Kontak harus berupa email yang valid"
            return
        }

        viewModelScope.launch {
            try {
                tulis.updatePenulis(id_penulis = id_penulis, penulis)

                ambilPenulis()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }
    // Update the UI state with a new InsertUiEvent
    fun updatePenulisState(insertPenulisUiEvent: InsertPenulisUiEvent) {
        penulisuiState.value = penulisuiState.value.copy(insertPenulisUiEvent = insertPenulisUiEvent)
    }
}

fun Penulis.toInsertUIEvent(): InsertPenulisUiState = InsertPenulisUiState(
    insertPenulisUiEvent = this.toHomePenulisUiEvent()
)
