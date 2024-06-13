package com.samueljuma.petapp.navigation

sealed class Screens(val route: String) {
    object PetsScreen: Screens("pets")
    object PetDetailsScreen: Screens("petDetails")
}