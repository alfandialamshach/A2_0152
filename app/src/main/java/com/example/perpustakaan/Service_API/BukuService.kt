package com.example.perpustakaan.Service_API

import com.example.perpustakaan.model.AllBukuResponse
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.BukuDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BukuService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("buku/")
    suspend fun getAllBuku(): AllBukuResponse

    @GET("buku/{id_buku}")
    suspend fun getBukuID(@Path("id_buku")id_buku:Int):BukuDetailResponse

    @POST("buku/tambahbuku")
    suspend fun insertBuku(@Body buku: Buku)

    @PUT("buku/{id_buku}")
    suspend fun updateBuku(@Path("id_buku")id_buku: Int, @Body buku: Buku)

    @DELETE("buku/{id_buku}")
    suspend fun deleteBuku(@Path("id_buku")id_buku: Int): retrofit2.Response<Void>
}

