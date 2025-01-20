package com.example.perpustakaan.Repository



import com.example.perpustakaan.Service_API.PenulisService

import com.example.perpustakaan.model.AllPenulisResponse

import com.example.perpustakaan.model.Penulis
import java.io.IOException


interface PenulisRepository{
    suspend fun getAllPenulis(): AllPenulisResponse
    suspend fun insertPenulis(penulis: Penulis)
    suspend fun updatePenulis(id_penulis:Int, penulis: Penulis)
    suspend fun deletePenulis(id_penulis: Int)
    suspend fun getPenulisID(id_penulis: Int) : Penulis
}


class NetworkPenulisRepository(
    private val penulisApiService: PenulisService
) : PenulisRepository{

    override suspend fun getAllPenulis(): AllPenulisResponse =
        penulisApiService.getAllPenulis()


    override suspend fun insertPenulis(penulis: Penulis) {
        penulisApiService.insertPenulis(penulis)
    }
    override suspend fun updatePenulis(id_penulis: Int, penulis: Penulis) {
        penulisApiService.updatePenulis(id_penulis, penulis)
    }

    override suspend fun deletePenulis(id_penulis: Int) {
        try{
            val response = penulisApiService.deletePenulis(id_penulis)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Penulis. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }


    override suspend fun getPenulisID(id_penulis: Int): Penulis {
        return penulisApiService.getPenulisID(id_penulis).data
    }

}

