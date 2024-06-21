package com.samueljuma.petapp.data

import kotlinx.coroutines.flow.Flow


interface PetsRepository {
    suspend fun getPets(): Flow<List<Cat>>
    suspend fun fetchRemotePets()
    suspend fun updatePet(cat: Cat)
    suspend fun getFavouritePets(): Flow<List<Cat>>
}