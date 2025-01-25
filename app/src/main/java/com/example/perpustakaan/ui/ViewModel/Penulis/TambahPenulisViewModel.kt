package com.example.perpustakaan.ui.ViewModel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Penulis
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class InsertPenulisViewModel(private val penulis: PenulisRepository) : ViewModel() {
    var penulisuiState by mutableStateOf(InsertPenulisUiState())
    var errorMessage by mutableStateOf("")

    fun updateInsertPenulisState(insertPenulisUiEvent: InsertPenulisUiEvent) {
        penulisuiState = InsertPenulisUiState(insertPenulisUiEvent = insertPenulisUiEvent)
    }

    suspend fun insertPenulis() {
        val uiEvent = penulisuiState.insertPenulisUiEvent

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

        // Proceed with insertion if validation passes
        viewModelScope.launch {
            try {
                penulis.insertPenulis(uiEvent.toPenulis())
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Unexpected error occurred"
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }
}

data class InsertPenulisUiState(
    val insertPenulisUiEvent: InsertPenulisUiEvent = InsertPenulisUiEvent(id_penulis = 0)
)

data class InsertPenulisUiEvent(
    val id_penulis: Int,
    val nama_penulis: String = "",
    val biografi: String = "",
    val kontak: String = ""
)

fun InsertPenulisUiEvent.toPenulis(): Penulis = Penulis(
    id_penulis = id_penulis,
    nama_penulis = nama_penulis,
    biografi = biografi,
    kontak = kontak
)
