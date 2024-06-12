package com.samueljuma.petapp.data


interface PetsRepository {
    suspend fun getPets(): NetworkResult<List<Cat>>
}