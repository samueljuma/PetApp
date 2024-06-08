package com.samueljuma.petapp.viewmodel

import androidx.lifecycle.ViewModel
import com.samueljuma.petapp.data.PetsRepository
import com.samueljuma.petapp.data.PetsRepositoryImpl

class PetsViewModel (private val petsRepository: PetsRepository) : ViewModel() {

    fun getPets() = petsRepository.getPets()

}