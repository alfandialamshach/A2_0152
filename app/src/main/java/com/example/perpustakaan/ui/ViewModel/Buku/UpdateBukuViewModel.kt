package com.example.perpustakaan.ui.ViewModel.Buku

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.Repository.BukuRepository
import com.example.perpustakaan.Repository.KategoriRepository
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.model.Penerbit
import kotlinx.coroutines.launch
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuUiEvent
import com.example.perpustakaan.ui.ViewModel.Buku.InsertBukuUiState

class UpdateBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val bukuRepository: BukuRepository,
    private val kategoriRepository: KategoriRepository,
    private val penulisRepository: PenulisRepository,
    private val penerbitRepository: PenerbitRepository
) : ViewModel() {

    val id_buku: Int = checkNotNull(savedStateHandle["id_buku"])

    var bukuUiState = mutableStateOf(InsertBukuUiState())
        private set
    var kategoriList = mutableStateOf<List<Kategori>>(emptyList())
        private set
    var penulisList = mutableStateOf<List<Penulis>>(emptyList())
        private set
    var penerbitList = mutableStateOf<List<Penerbit>>(emptyList())
        private set

    init {
        loadBukuData()
        loadAdditionalData()
    }

    // Fetch Buku Data by id_buku
    fun loadBukuData() {
        viewModelScope.launch {
            try {
                val buku = bukuRepository.getBukuID(id_buku = id_buku)
                buku?.let {
                    bukuUiState.value = it.toInsertBukuUIEvent() // Update UI state with fetched buku data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Load Data for Kategori, Penulis, and Penerbit
    fun loadAdditionalData() {
        viewModelScope.launch {
            try {
                val kategoriResponse = kategoriRepository.getAllKategori()
                kategoriList.value = kategoriResponse.data

                val penulisResponse = penulisRepository.getAllPenulis()
                penulisList.value = penulisResponse.data

                val penerbitResponse = penerbitRepository.getAllPenerbit()
                penerbitList.value = penerbitResponse.data
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }

    // Update Buku Data
    fun updateBuku(buku: Buku) {
        viewModelScope.launch {
            try {
                bukuRepository.updateBuku(id_buku = id_buku, buku)
                loadBukuData() // Reload buku data after update
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }

    // Update UI state with new InsertBukuUiEvent
    fun updateBukuState(insertBukuUiEvent: InsertBukuUiEvent) {
        bukuUiState.value = bukuUiState.value.copy(insertBukuUiEvent = insertBukuUiEvent)
    }
}

// Convert Buku to InsertBukuUiEvent
fun Buku.toInsertBukuUIEvent(): InsertBukuUiState = InsertBukuUiState(
    insertBukuUiEvent = this.toDetailUiEvent()
)
