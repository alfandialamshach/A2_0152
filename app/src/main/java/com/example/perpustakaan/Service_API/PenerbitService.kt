package com.example.perpustakaan.Service_API


import com.example.perpustakaan.model.AllPenerbitResponse
import com.example.perpustakaan.model.Penerbit
import com.example.perpustakaan.model.PenerbitDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path



interface PenerbitService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("penerbit/")
    suspend fun getAllPenerbit(): AllPenerbitResponse

    @GET("penerbit/{id_penerbit}")
    suspend fun getPenerbitID(@Path("id_penerbit")id_penerbit:Int): PenerbitDetailResponse

    @POST("penerbit/store")
    suspend fun insertPenerbit(@Body penerbit: Penerbit)

    @PUT("penerbit/{id_penerbit}")
    suspend fun updatePenerbit(@Path("id_penerbit")id_penerbit : Int, @Body penerbit: Penerbit)

    @DELETE("penerbit/{id_penerbit}")
    suspend fun deletePenerbit(@Path("id_penerbit")id_penerbit: Int): retrofit2.Response<Void>
}