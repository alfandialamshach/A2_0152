package com.example.perpustakaan.Container

import com.example.perpustakaan.Repository.BukuRepository
import com.example.perpustakaan.Repository.KategoriRepository
import com.example.perpustakaan.Repository.NetworkBukuRepository
import com.example.perpustakaan.Repository.NetworkKategoriRepository
import com.example.perpustakaan.Repository.NetworkPenerbitRepository
import com.example.perpustakaan.Repository.NetworkPenulisRepository
import com.example.perpustakaan.Repository.PenerbitRepository
import com.example.perpustakaan.Repository.PenulisRepository
import com.example.perpustakaan.Service_API.BukuService
import com.example.perpustakaan.Service_API.KategoriService
import com.example.perpustakaan.Service_API.PenerbitService
import com.example.perpustakaan.Service_API.PenulisService
import com.example.perpustakaan.serializer.DateSerializer // Import serializer Date
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bukuRepository: BukuRepository
    val kategoriRepository: KategoriRepository
    val penerbitRepository : PenerbitRepository
    val penulisRepository : PenulisRepository
}

class PerpustakaanAppContainer : AppContainer {

    // Base URL untuk API
    private val baseUrl = "http://10.0.2.2:3030/api/" // Emulator Android
    // Gunakan ini untuk perangkat nyata
    // private val baseUrl = "http://<IP_LOCAL>:3030/api/"

    // Konfigurasi JSON
    private val json = Json {
        ignoreUnknownKeys = true // Mengabaikan field yang tidak dikenali
        isLenient = true // Mendukung format JSON yang kurang ketat
        encodeDefaults = false // Tidak menambahkan nilai default saat serialisasi
        serializersModule = SerializersModule {
            contextual(DateSerializer) // Registrasi serializer untuk tipe Date
        }
    }

    // Instance Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    // Service untuk Buku API
    private val bukuService: BukuService by lazy {
        retrofit.create(BukuService::class.java)
    }

    // Service untuk Kategori API
    private val kategoriService: KategoriService by lazy {
        retrofit.create(KategoriService::class.java)
    }
    // Service untuk Penerbit Api
    private val penerbitService: PenerbitService by lazy {
        retrofit.create(PenerbitService::class.java)
    }

    // Service untuk Penulis API
    private val penulisService: PenulisService by lazy {
        retrofit.create(PenulisService::class.java)
    }
    // Repository untuk Buku
    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }

    // Repository untuk Kategori
    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }

    // Repository untuk penerbit
    override val penerbitRepository: PenerbitRepository by lazy {
        NetworkPenerbitRepository(penerbitService)
    }

    // Repository untuk Penulis
    override val penulisRepository: PenulisRepository by lazy {
        NetworkPenulisRepository(penulisService)
    }
}
