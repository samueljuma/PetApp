package com.samueljuma.petapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.data.NetworkResult
import com.samueljuma.petapp.data.PetsRepository
import com.samueljuma.petapp.data.asResult
import com.samueljuma.petapp.views.PetsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetsViewModel(
    private val petsRepository: PetsRepository,
) : ViewModel() {
    val petsUIState = MutableStateFlow(PetsUIState())

    private val _favoritePets = MutableStateFlow<List<Cat>>(emptyList())
    val favoritePets: StateFlow<List<Cat>> get() = _favoritePets

    // Call the getPets function when the ViewModel is initialized
    init {
        getPets()
    }

    private fun getPets() {
        petsUIState.value = PetsUIState(isLoading = true)
        viewModelScope.launch {
            petsRepository.getPets().asResult().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        petsUIState.update {
                            it.copy(
                                isLoading = false,
                                pets = result.data,
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        petsUIState.update {
                            it.copy(
                                isLoading = false,
                                error = result.error,
                            )
                        }
                    }
                }
            }
        }
    }

    fun updatePet(cat: Cat) {
        viewModelScope.launch {
            petsRepository.updatePet(cat)
        }
    }

    fun getFavoritePets() {
        viewModelScope.launch {
            petsRepository.getFavouritePets().collect {
                _favoritePets.value = it
            }
        }
    }
}