package com.samueljuma.petapp.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class PetsRepositoryImpl (
    private val catsAPI: CatsAPI,
    private val dispatcher: CoroutineDispatcher
): PetsRepository {
    override suspend fun getPets(): NetworkResult<List<Cat>> {

        //withContext(dispatcher) helps to switch the context of the coroutine
        return withContext(dispatcher){
            try {
                val response = catsAPI.fetchCats("cute")
                if (response.isSuccessful){
                    NetworkResult.Success(response.body()!!)
                }else{
                    NetworkResult.Error(response.errorBody().toString())
                }
            }catch (e: Exception){
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }


}