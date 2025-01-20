package com.example.perpustakaan.model

import com.example.perpustakaan.serializer.DateSerializer

import kotlinx.serialization.Serializable

import java.util.Date // Menggunakan java.util.Date

@Serializable
data class Buku(
    val id_buku: Int,
    val nama_buku: String,
    val deskripsi_buku: String,
    @Serializable(with = DateSerializer::class) // Menambahkan serializer untuk tanggal_terbit
    val tanggal_terbit: Date, // Ganti dengan java.util.Date
    val status_buku: String,
    val id_kategori: Int,
    val id_penulis: Int,
    val id_penerbit: Int,
)


@Serializable
data class AllBukuResponse(
    val status: Boolean,
    val message: String,
    val data: List<Buku>
)

@Serializable
data class BukuDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Buku
)