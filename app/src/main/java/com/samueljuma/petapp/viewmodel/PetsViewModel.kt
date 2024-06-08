package com.samueljuma.petapp.viewmodel

import androidx.lifecycle.ViewModel
import com.samueljuma.petapp.data.PetsRepository
import com.samueljuma.petapp.data.PetsRepositoryImpl

class PetsViewModel : ViewModel() {

    private val petsRepository: PetsRepository = PetsRepositoryImpl()

    fun getPets() = petsRepository.getPets()

}