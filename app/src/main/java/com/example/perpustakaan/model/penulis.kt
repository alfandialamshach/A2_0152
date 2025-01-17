package com.example.perpustakaan.model

import kotlinx.serialization.Serializable


@Serializable
data class Penulis(
    val id_penulis: Int,
    val nama_buku: String,
    val biografi: String,
    val kontak: String,




    )

@Serializable
data class AllPenulisResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penulis>
)

@Serializable
data class PenulisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penulis
)