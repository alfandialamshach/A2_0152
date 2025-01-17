package com.example.perpustakaan.Service_API

import com.example.perpustakaan.model.AllBukuResponse
import com.example.perpustakaan.model.AllKategoriResponse
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.BukuDetailResponse
import com.example.perpustakaan.model.Kategori
import com.example.perpustakaan.model.KategoriDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KategoriService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getAllKategori(): AllKategoriResponse

    @GET("{id_kategori}")
    suspend fun getKategoriID(@Path("id_buku")id_kategori:String): KategoriDetailResponse

    @POST("store")
    suspend fun insertKategori(@Body kategori: Kategori)

    @PUT("{id_kategori}")
    suspend fun updateKategori(@Path("id_kategori")id_kategori: String, @Body kategori: Kategori)

    @DELETE("{id_kategori}")
    suspend fun deleteKategori(@Path("id_kategori")id_kategori: String): retrofit2.Response<Void>
}