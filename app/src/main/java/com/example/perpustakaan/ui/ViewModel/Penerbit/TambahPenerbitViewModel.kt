package com.example.perpustakaan.ui.ViewModel.Penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.model.Penulis
import kotlinx.coroutines.launch


class InsertPenerbitViewModel(private val penerbit: PenerbitRepository) : ViewModel() {
    var penerbituiState by mutableStateOf(InsertPenerbitUiState())
    var errorMessage by mutableStateOf("")
    fun updateInsertPenerbitState(insertPenerbitUiEvent: InsertPenerbitUiEvent) {
        penerbituiState = InsertPenerbitUiState(insertPenerbitUiEvent = insertPenerbitUiEvent)
    }

    suspend fun insertPenerbit() {

        val uiEvent = penerbituiState.insertPenerbitUiEvent
        errorMessage = ""
        // Validation
        if (uiEvent.nama_penerbit.isEmpty()) {
            errorMessage = "Nama penerbit tidak boleh kosong"
            return
        }
        if (uiEvent.alamat_penerbit.isEmpty()) {
            errorMessage = "Alamat tidak boleh kosong"
            return
        }

        if (uiEvent.telepon_penerbit.isEmpty() || !isValidPhoneNumber(uiEvent.telepon_penerbit)) {
            errorMessage = "Nomor telepon harus berupa angka dan format yang valid"
            return
        }
        viewModelScope.launch {
            try {
                penerbit.insertPenerbit(penerbituiState.insertPenerbitUiEvent.toPenerbit())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    private fun isValidPhoneNumber(phone: String): Boolean {
        // Regex untuk validasi nomor telepon (hanya angka, 10-15 digit)
        val regex = Regex("^\\d{10,15}$")
        return regex.matches(phone)
    }
}

data class InsertPenerbitUiState(
    val insertPenerbitUiEvent: InsertPenerbitUiEvent = InsertPenerbitUiEvent(id_penerbit = 0)
)

data class InsertPenerbitUiEvent(
    val id_penerbit:Int,
    val nama_penerbit:String = "",
    val alamat_penerbit:String = "",
    val telepon_penerbit:String = "",

    )
fun InsertPenerbitUiEvent.toPenerbit(): Penerbit = Penerbit(
    id_penerbit = id_penerbit,
    nama_penerbit = nama_penerbit,
    alamat_penerbit = alamat_penerbit,
    telepon_penerbit = telepon_penerbit
)

fun Penerbit.toInsertUiEvent():InsertPenerbitUiEvent = InsertPenerbitUiEvent(
    id_penerbit =id_penerbit,
    nama_penerbit = nama_penerbit,
    alamat_penerbit = alamat_penerbit,
    telepon_penerbit = telepon_penerbit
)