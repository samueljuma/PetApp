package com.samueljuma.petapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samueljuma.petapp.views.PetDetailsScreen
import com.samueljuma.petapp.views.PetsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation() {

    val navController = rememberNavController() // Create a NavController for navigation
    NavHost(
        navController = navController, // Set the NavController for the NavHost
        startDestination = Screens.PetsScreen.route // Define the initial screen
    ) {
        composable(route = Screens.PetsScreen.route) { // Define the PetsScreen route
            PetsScreen(onPetClicked = { cat ->
                // Navigate to PetDetailsScreen when a pet is clicked
                navController.navigate(
                    route = "${Screens.PetDetailsScreen.route}/${Json.encodeToString(cat)}" // Pass the Cat object as a JSON string
                )
            })
        }
        composable(
            route = "${Screens.PetDetailsScreen.route}/{cat}", // Define the PetDetailsScreen route with a dynamic "cat" argument
            arguments = listOf(

                navArgument("cat"){ // Define the "cat" argument as a String type
                    type = NavType.StringType
                }
            )

        ) {
            PetDetailsScreen(
                onBackPressed = {
                    navController.popBackStack() // Pop the back stack when back is pressed
                },
                cat = Json.decodeFromString(it.arguments?.getString("cat") ?: "") // Decode the Cat object from the "cat" argument
            )
        }

    }
}