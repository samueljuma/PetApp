package com.samueljuma.petapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samueljuma.petapp.data.NetworkResult
import com.samueljuma.petapp.data.PetsRepository
import com.samueljuma.petapp.data.PetsRepositoryImpl
import com.samueljuma.petapp.views.PetsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetsViewModel(
    private val petsRepository: PetsRepository
) : ViewModel() {

    val petsUIState = MutableStateFlow(PetsUIState())

    // Call the getPets function when the ViewModel is initialized
    init {
        getPets()
    }

    private fun getPets() {
        viewModelScope.launch {
            petsUIState.value = PetsUIState(isLoading = true)
            when (val result = petsRepository.getPets()) {
                is NetworkResult.Success -> {
                    petsUIState.update {
                        it.copy(
                            isLoading = false,
                            pets = result.data
                        )
                    }
                }

                is NetworkResult.Error -> {
                    petsUIState.update {
                        it.copy(
                            isLoading = false,
                            error = result.error
                        )

                    }
                }
            }
        }

    }
}