package com.samueljuma.petapp.data


interface PetsRepository {
    fun getPets(): List<Pet>
}