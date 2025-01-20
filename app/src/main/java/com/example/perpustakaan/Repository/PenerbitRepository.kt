package com.example.perpustakaan.Repository


import com.example.perpustakaan.Service_API.PenerbitService
import com.example.perpustakaan.model.AllPenerbitResponse
import com.example.perpustakaan.model.Penerbit
import java.io.IOException


interface PenerbitRepository{
    suspend fun getAllPenerbit(): AllPenerbitResponse
    suspend fun insertPenerbit(penerbit: Penerbit)
    suspend fun updatePenerbit(id_penerbit:Int, penerbit: Penerbit)
    suspend fun deletePenerbit(id_penerbit: Int)
    suspend fun getPenerbitID(id_penerbit: Int) : Penerbit
}


class NetworkPenerbitRepository(
    private val penerbitApiService: PenerbitService
) : PenerbitRepository{

    override suspend fun getAllPenerbit(): AllPenerbitResponse =
        penerbitApiService.getAllPenerbit()


    override suspend fun insertPenerbit(penerbit: Penerbit) {
        penerbitApiService.insertPenerbit(penerbit)
    }
    override suspend fun updatePenerbit(id_penerbit: Int, penerbit: Penerbit) {
        penerbitApiService.updatePenerbit(id_penerbit, penerbit)
    }

    override suspend fun deletePenerbit(id_penerbit: Int) {
        try{
            val response = penerbitApiService.deletePenerbit(id_penerbit)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Penertbit. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }


    override suspend fun getPenerbitID(id_penerbit: Int): Penerbit {
        return penerbitApiService.getPenerbitID(id_penerbit).data
    }

}