package com.example.perpustakaan.model

import kotlinx.serialization.Serializable


@Serializable
data class Kategori(
    val id_kategori: Int,
    val nama_kategori: String,
    val deskripsi_kategori: String,




    )

@Serializable
data class AllKategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kategori>
)

@Serializable
data class KategoriDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kategori
)