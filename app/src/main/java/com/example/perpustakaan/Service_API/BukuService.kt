package com.example.perpustakaan.Service_API

import com.example.perpustakaan.model.AllBukuResponse
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.BukuDetailResponse
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
    @GET(".")
    suspend fun getAllBuku(): AllBukuResponse

    @GET("{id_buku}")
    suspend fun getBukuID(@Path("id_buku")id_buku:String):BukuDetailResponse

    @POST("store")
    suspend fun insertBuku(@Body buku: Buku)

    @PUT("{id_buku}")
    suspend fun updateBuku(@Path("id_buku")id_buku: String, @Body buku: Buku)

    @DELETE("{id_buku}")
    suspend fun deleteBuku(@Path("id_buku")id_buku: String): retrofit2.Response<Void>
}

