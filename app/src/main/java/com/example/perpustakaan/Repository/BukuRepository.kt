package com.example.perpustakaan.Repository

import com.example.perpustakaan.Service_API.BukuService
import com.example.perpustakaan.model.AllBukuResponse
import com.example.perpustakaan.model.Buku
import java.io.IOException


interface BukuRepository{
    suspend fun getAllBuku(): AllBukuResponse
    suspend fun insertBuku(buku: Buku)
    suspend fun updateBuku(id_buku: String, buku: Buku)
    suspend fun deleteBuku(id_buku: String)
    suspend fun getBukuID(id_buku: String) : Buku
}


class NetworkBukuRepository(
    private val bukuApiService: BukuService
) : BukuRepository{

    override suspend fun getAllBuku(): AllBukuResponse =
        bukuApiService.getAllBuku()

    override suspend fun insertBuku(buku: Buku) {
        bukuApiService.insertBuku(buku)
    }
    override suspend fun updateBuku(id_buku: String, buku: Buku) {
        bukuApiService.updateBuku(id_buku, buku)
    }

    override suspend fun deleteBuku(id_buku: String) {
        try{
            val response = bukuApiService.deleteBuku(id_buku)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Buku. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }


    override suspend fun getBukuID(id_buku: String): Buku {
        return bukuApiService.getBukuID(id_buku).data
    }

}