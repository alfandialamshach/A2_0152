package com.example.perpustakaan.Repository

import com.example.perpustakaan.Service_API.BukuService
import com.example.perpustakaan.Service_API.KategoriService
import com.example.perpustakaan.model.AllBukuResponse
import com.example.perpustakaan.model.AllKategoriResponse
import com.example.perpustakaan.model.Buku
import com.example.perpustakaan.model.Kategori
import java.io.IOException


interface KategoriRepository{
    suspend fun getAllKategori(): AllKategoriResponse
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(id_kategori:Int, kategori: Kategori)
    suspend fun deleteKategori(id_kategori: Int)
    suspend fun getKategoriID(id_kategori: Int) : Kategori
}


class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository{

    override suspend fun getAllKategori(): AllKategoriResponse =
        kategoriApiService.getAllKategori()


    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }
    override suspend fun updateKategori(id_kategori: Int, kategori: Kategori) {
        kategoriApiService.updateKategori(id_kategori, kategori)
    }

    override suspend fun deleteKategori(id_kategori: Int) {
        try{
            val response = kategoriApiService.deleteKategori(id_kategori)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Kategori. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }


    override suspend fun getKategoriID(id_kategori: Int): Kategori {
        return kategoriApiService.getKategoriID(id_kategori).data
    }

}