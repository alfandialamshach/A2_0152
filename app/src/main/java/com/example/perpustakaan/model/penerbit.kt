package com.example.perpustakaan.model

import kotlinx.serialization.Serializable


@Serializable
data class Penerbit(
    val id_penerbit: Int,
    val nama_penerbit: String,
    val alamat_penerbit: String,
    val telepon_penerbit : String


    )

@Serializable
data class AllPenerbitResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penerbit>
)

@Serializable
data class PenerbitDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penerbit
)