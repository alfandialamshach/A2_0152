package com.example.perpustakaan.ui.ViewModel.Buku

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.BukuRepository
import com.example.perpustakaan.Repository.KategoriRepository
import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.model.Penerbit
import kotlinx.coroutines.launch
import java.util.Date

class InsertBukuViewModel(
    private val buku: BukuRepository,
    private val kategori: KategoriRepository,
    private val penerbit: PenerbitRepository,
    private val penulis: PenulisRepository,
) : ViewModel() {

    // State untuk UI
    var bukuUiState by mutableStateOf(InsertBukuUiState())
    var kategoriList by mutableStateOf<List<Kategori>>(emptyList())
        private set
    var penulisList by mutableStateOf<List<Penulis>>(emptyList())
        private set
    var penerbitList by mutableStateOf<List<Penerbit>>(emptyList())
        private set
    var isLoading by mutableStateOf(false) // Tambahan untuk loading state
        private set
    var errorMessage by mutableStateOf("")

    init {
        // Memuat data kategori, penulis, dan penerbit
        loadInitialData()
    }

    private fun loadInitialData() {
        isLoading = true
        viewModelScope.launch {
            try {
                val kategoriResponse = kategori.getAllKategori()
                kategoriList = kategoriResponse.data // Mapping dari response
                val penulisResponse = penulis.getAllPenulis()
                penulisList = penulisResponse.data // Mapping dari response
                val penerbitResponse = penerbit.getAllPenerbit()
                penerbitList = penerbitResponse.data // Mapping dari response
            } catch (e: Exception) {
                errorMessage = "Gagal memuat data: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateInsertBukuState(insertBukuUiEvent: InsertBukuUiEvent) {
        bukuUiState = InsertBukuUiState(insertBukuUiEvent = insertBukuUiEvent)
    }

    fun insertBuku() {
        isLoading = true
        val uiEvent = bukuUiState.insertBukuUiEvent
        errorMessage = ""
        // Validation
        if (uiEvent.nama_buku.isEmpty()) {
            errorMessage = "Nama Buku tidak boleh kosong"
            return
        }
        if (uiEvent.deskripsi_buku.isEmpty()) {
            errorMessage = "Deskripsi Buku tidak boleh kosong"
            return
        }
        if (uiEvent.status_buku.isEmpty()) {
            errorMessage = "Status Buku tidak boleh kosong"
            return
        }
        if (uiEvent.id_kategori == 0 || !isValidKategori(uiEvent.id_kategori)) {
            errorMessage = "ID Kategori tidak valid"
            return
        }
        if (uiEvent.id_penulis == 0 || !isValidPenulis(uiEvent.id_penulis)) {
            errorMessage = "ID Penulis tidak valid"
            return
        }
        if (uiEvent.id_penerbit == 0 || !isValidPenerbit(uiEvent.id_penerbit)) {
            errorMessage = "ID Penerbit tidak valid"
            return
        }


        viewModelScope.launch {
            try {
                Log.d("InsertBukuViewModel", "Inserting Buku: $bukuUiState")
                buku.insertBuku(bukuUiState.insertBukuUiEvent.toBuku())
                Log.d("InsertBukuViewModel", "Insert successful")
            } catch (e: Exception) {
                errorMessage = "Gagal menyimpan buku: ${e.message}"
                Log.e("InsertBukuViewModel", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
    // Function to check if id_kategori exists in the available categories
    fun isValidKategori(id_kategori: Int): Boolean {
        // Check if the id_kategori exists in kategoriList
        return kategoriList.any { it.id_kategori == id_kategori }
    }
    fun isValidPenulis(id_penulis: Int): Boolean {
        // Check if the id_kategori exists in kategoriList
        return penulisList.any { it.id_penulis == id_penulis }
    }
    fun isValidPenerbit(id_penerbit: Int): Boolean {
        // Check if the id_kategori exists in kategoriList
        return penerbitList.any { it.id_penerbit == id_penerbit }
    }

}


data class InsertBukuUiState(
    val insertBukuUiEvent: InsertBukuUiEvent = InsertBukuUiEvent(id_buku = 0)
)

data class InsertBukuUiEvent(
    val id_buku: Int = 0,
    val nama_buku: String = "",
    val deskripsi_buku: String = "",
    val tanggal_terbit: Date = Date(),
    val status_buku: String = "",
    val id_kategori: Int = 0,
    val id_penulis: Int = 0,
    val id_penerbit: Int = 0,
)

fun InsertBukuUiEvent.toBuku(): Buku = Buku(
    id_buku = id_buku,
    nama_buku = nama_buku,
    deskripsi_buku = deskripsi_buku,
    tanggal_terbit = java.sql.Date(tanggal_terbit.time), // Konversi ke java.sql.Date
    status_buku = status_buku,
    id_kategori = id_kategori,
    id_penulis = id_penulis,
    id_penerbit = id_penerbit
)

