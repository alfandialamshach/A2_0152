package com.example.perpustakaan.Service_API


import com.example.perpustakaan.model.AllPenerbitResponse
import com.example.perpustakaan.model.AllPenulisResponse
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.model.PenerbitDetailResponse
import com.example.perpustakaan.model.Penulis
import com.example.perpustakaan.model.PenulisDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path



interface PenulisService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("penulis/")
    suspend fun getAllPenulis(): AllPenulisResponse

    @GET("penulis/{id_penulis}")
    suspend fun getPenulisID(@Path("id_penulis")id_penulis: Int): PenulisDetailResponse


    @POST("penulis/tambah")
    suspend fun insertPenulis(@Body penulis: Penulis)


    @PUT("penulis/{id_penulis}")
    suspend fun updatePenulis(@Path("id_penulis")id_penulis: Int, @Body penulis: Penulis)

    @DELETE("penulis/{id_penulis}")
    suspend fun deletePenulis(@Path("id_penulis") id_penulis: Int): retrofit2.Response<Void>



}