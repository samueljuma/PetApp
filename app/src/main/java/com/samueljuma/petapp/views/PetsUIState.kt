package com.samueljuma.petapp.views

import com.samueljuma.petapp.data.Cat

data class PetsUIState(
    val isLoading: Boolean = false,
    val pets: List<Cat> = emptyList(),
    val error: String? = null,
)