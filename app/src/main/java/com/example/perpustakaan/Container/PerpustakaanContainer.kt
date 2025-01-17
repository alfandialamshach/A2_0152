package com.example.perpustakaan.Container

import com.example.perpustakaan.Repository.BukuRepository
import com.example.perpustakaan.Repository.NetworkBukuRepository
import com.example.perpustakaan.Service_API.BukuService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val bukuRepository: BukuRepository
}


class BukuContainer : AppContainer{

    private val baseUrl = "http://10.0.2.2:3030/api/buku/" //localhost diganti ip kalo run dihp
    private val json = Json {ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bukuService: BukuService by lazy{
        retrofit.create(BukuService::class.java)
    }

    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }
}