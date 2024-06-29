package com.samueljuma.petapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samueljuma.petapp.views.FavoritePetsScreen
import com.samueljuma.petapp.views.PetDetailsScreen
import com.samueljuma.petapp.views.PetsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        // Set the NavController for the NavHost
        navController = navController,
        // Define the initial screen
        startDestination = Screens.PetsScreen.route,
    ) {
        composable(route = Screens.PetsScreen.route) { // Define the PetsScreen route
            PetsScreen(
                onPetClicked = { cat ->
                    // Navigate to PetDetailsScreen when a pet is clicked
                    navController.navigate(
                        // Pass the Cat object as a JSON string
                        route = "${Screens.PetDetailsScreen.route}/${Json.encodeToString(cat)}",
                    )
                },
                contentType = contentType,
            )
        }
        composable(
            // Define the PetDetailsScreen route with a dynamic "cat" argument
            route = "${Screens.PetDetailsScreen.route}/{cat}",
            arguments =
                listOf(
                    // Define the "cat" argument as a String type
                    navArgument("cat") {
                        type = NavType.StringType
                    },
                ),
        ) {
            PetDetailsScreen(
                onBackPressed = {
                    // Pop the back stack when back is pressed
                    navController.popBackStack()
                },
                // Decode the Cat object from the "cat" argument
                cat =
                    Json.decodeFromString(
                        it.arguments?.getString("cat") ?: "",
                    ),
            )
        }

        composable(Screens.FavouritesScreen.route) {
            FavoritePetsScreen(
                onPetClicked = { cat ->
                    navController.navigate(
                        "${Screens.PetDetailsScreen.route}/${Json.encodeToString(cat)}",
                    )
                },
            )
        }
    }
}